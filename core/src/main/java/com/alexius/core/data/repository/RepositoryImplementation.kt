package com.alexius.core.data.repository

import android.util.Log
import com.alexius.core.data.remote.response.AssessmentScoreFirestore
import com.alexius.core.data.remote.response.UserInfoFirestore
import com.alexius.core.data.remote.response.toDomainModel
import com.alexius.core.data.remote.speech_ai.SpeechAIApi
import com.alexius.core.data.remote.speech_ai.TextToSpeechRequest
import com.alexius.core.domain.model.AssessmentScore
import com.alexius.core.domain.model.UserInfo
import com.alexius.core.domain.repository.Repository
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import okhttp3.ResponseBody
import javax.inject.Inject

class RepositoryImplementation @Inject constructor(
    private val speechAi: SpeechAIApi
) : Repository  {

    private val db = Firebase.firestore
    private val auth = Firebase.auth
    private val TAG = "FirestoreExample"

    private var _userInfo: UserInfo = UserInfo(
        birth_date = "",
        full_name = "",
        phone_number = ""
    )

    val userInfo = _userInfo

    private var _assessmentScore: AssessmentScore = AssessmentScore(
        totalScore = 0,
        grade = "",
        category = ""
    )

    val assessmentScore = _assessmentScore

    override suspend fun generateSpeech(
        id: String,
        apiKey: String,
        request: TextToSpeechRequest
    ): ResponseBody {

        return speechAi.generateSpeech(id, apiKey, request)
    }

    override suspend fun addUserInfo(userInfo: UserInfo): Flow<Result<Unit>> = flow {
        try {
            val userId = auth.currentUser?.uid ?: throw Exception("User not authenticated")

            val user = hashMapOf(
                "birth_date" to userInfo.birth_date,
                "full_name" to userInfo.full_name,
                "phone_number" to userInfo.phone_number,
            )

            db.collection("users").document(userId)
                .set(user)
                .await() // Use await to make it suspend function

            // Add a document to the assessment_score subcollection
            val assessmentScore = AssessmentScore(
                totalScore = 0,
                grade = "",
                category = ""
            )

            db.collection("users").document(userId)
                .collection("assessment_score")
                .add(assessmentScore)
                .await() // Use await to make it suspend function

            emit(Result.success(Unit))
        } catch (e: Exception) {
            Log.w(TAG, "Error writing document", e)
            emit(Result.failure(e))
        }
    }

    override suspend fun getUserInfoAndAssessmentScore() = flow {
        try {
            val userId = auth.currentUser?.uid ?: throw Exception("User not authenticated")

            // Get user info
            val userInfoSnapshot = db.collection("users").document(userId).get().await()
            val userInfoFirestore = userInfoSnapshot.toObject(UserInfoFirestore::class.java) ?: throw Exception("User info not found")
            _userInfo = userInfoFirestore.toDomainModel()

            // Get assessment score
            val assessmentScoreSnapshot = db.collection("users").document(userId)
                .collection("assessment_score").get().await()
            val assessmentScoreFirestore = assessmentScoreSnapshot.documents.firstOrNull()?.toObject(
                AssessmentScoreFirestore::class.java) ?: throw Exception("Assessment score not found")

            _assessmentScore = assessmentScoreFirestore.toDomainModel()

            emit(Result.success(Unit))
        } catch (e: Exception) {
            Log.w(TAG, "Error fetching user info and assessment score", e)
            emit(Result.failure(e))
        }
    }

    override suspend fun updateAssessmentScore(newScore: AssessmentScore): Flow<Result<Unit>> = flow {
        try {
            val userId = auth.currentUser?.uid ?: throw Exception("User not authenticated")

            // Get all documents in the assessment_score collection
            val assessmentScoreSnapshot = db.collection("users").document(userId)
                .collection("assessment_score").get().await()

            // Get the first document
            val firstDocument = assessmentScoreSnapshot.documents.firstOrNull()
                ?: throw Exception("No assessment score document found")

            // Update the first document
            val assessmentScoreRef = db.collection("users").document(userId)
                .collection("assessment_score").document(firstDocument.id)

            assessmentScoreRef.set(newScore).await() // Use await to make it suspend function

            emit(Result.success(Unit))
        } catch (e: Exception) {
            Log.w(TAG, "Error updating assessment score", e)
            emit(Result.failure(e))
        }
    }

    override fun changeLocalUserInfoName(name: String) {
        _userInfo.full_name = name
    }

    override fun getLocalUserInfo(): UserInfo {
        return userInfo
    }

    override fun getLocalAssessmentScore(): AssessmentScore {
        return assessmentScore
    }

    override fun generateGrammarPrompt(userSentence: String): String = """
        Analyze the following English sentence for grammar corrections and provide a structured response:
        Sentence: "$userSentence"
        
        Please provide:
        1. A list of specific grammar corrections needed (maximum 3 points)
        2. The fully corrected sentence
        
        Format the response as JSON:
        {
            "incorrectSentence": "original sentence",
            "corrections": ["correction point 1", "correction point 2", "correction point 3"],
            "correctedSentence": "fully corrected sentence"
        }
    """.trimIndent()

    override fun generateVocabularyPrompt(userSentence: String): String = """
        Analyze the following English sentence and provide vocabulary alternatives:
        Sentence: "$userSentence"
        
        Please provide:
        1. Identify key adjectives or descriptive words
        2. Provide 4 synonyms for each identified word
        
        Format the response as JSON:
        {
            "originalSentence": "original sentence",
            "alternativeWords": {
                "word1": ["synonym1", "synonym2", "synonym3", "synonym4"],
                "word2": ["synonym1", "synonym2", "synonym3", "synonym4"]
            }
        }
    """.trimIndent()
}