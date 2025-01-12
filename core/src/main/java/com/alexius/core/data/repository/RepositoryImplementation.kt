package com.alexius.core.data.repository

import android.util.Log
import com.alexius.core.data.remote.response.AssessmentScoreFirestore
import com.alexius.core.data.remote.response.CompletedStoriesFirestore
import com.alexius.core.data.remote.response.UserInfoFirestore
import com.alexius.core.data.remote.response.toDomainModel
import com.alexius.core.data.remote.speech_ai.SpeechAIApi
import com.alexius.core.data.remote.speech_ai.TextToSpeechRequest
import com.alexius.core.domain.model.AssessmentScore
import com.alexius.core.domain.model.CompletedStories
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

            val completedStories = CompletedStories(
                beginner = arrayListOf(false, false, false),
                intermediate = arrayListOf(false, false, false),
                advancedTales = arrayListOf(false, false, false),
                careerTales = arrayListOf(false, false, false)
            )

            db.collection("users").document(userId)
                .collection("completed_stories")
                .add(completedStories)
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
            val userInfo = userInfoFirestore.toDomainModel()

            // Get assessment score
            val assessmentScoreSnapshot = db.collection("users").document(userId)
                .collection("assessment_score").get().await()
            val assessmentScoreFirestore = assessmentScoreSnapshot.documents.firstOrNull()?.toObject(
                AssessmentScoreFirestore::class.java) ?: throw Exception("Assessment score not found")
            val assessmentScore = assessmentScoreFirestore.toDomainModel()

            emit(Result.success(Pair(userInfo, assessmentScore)))
        } catch (e: Exception) {
            Log.w(TAG, "Error fetching user info and assessment score", e)
            emit(Result.failure(e))
        }
    }

    override suspend fun getCompletedStories(): Flow<Result<CompletedStories>> = flow {
        try {
            val userId = auth.currentUser?.uid ?: throw Exception("User not authenticated")

            // Get all documents in the completed_stories collection
            val completedStoriesSnapshot = db.collection("users").document(userId)
                .collection("completed_stories").get().await()

            // Get the first document
            val firstDocument = completedStoriesSnapshot.documents.firstOrNull()
                ?: throw Exception("No completed stories document found")

            // Convert the first document to CompletedStoriesFirestore
            val completedStoriesFirestore = firstDocument.toObject(CompletedStoriesFirestore::class.java)
                ?: throw Exception("Failed to convert document to CompletedStoriesFirestore")
            val completedStories = completedStoriesFirestore.toDomainModel()

            emit(Result.success(completedStories))
        } catch (e: Exception) {
            Log.w(TAG, "Error fetching completed stories", e)
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

    override suspend fun updateCompletedStories(completedStories: CompletedStories): Flow<Result<Unit>> = flow {
        try {
            val userId = auth.currentUser?.uid ?: throw Exception("User not authenticated")

            // Get all documents in the completed_stories collection
            val completedStoriesSnapshot = db.collection("users").document(userId)
                .collection("completed_stories").get().await()

            // Get the first document
            val firstDocument = completedStoriesSnapshot.documents.firstOrNull()
                ?: throw Exception("No completed stories document found")

            // Update the first document
            val completedStoriesRef = db.collection("users").document(userId)
                .collection("completed_stories").document(firstDocument.id)

            completedStoriesRef.set(completedStories).await() // Use await to make it suspend function

            emit(Result.success(Unit))
        } catch (e: Exception) {
            Log.w(TAG, "Error updating completed stories", e)
            emit(Result.failure(e))
        }
    }

    override fun changeLocalUserInfoName(name: String) {
        _userInfo.full_name = name
    }

    override fun getLocalUserInfo(): UserInfo {
        Log.d(TAG, "getLocalUserInfo: ${_userInfo.full_name}")
        return userInfo
    }

    override fun getLocalAssessmentScore(): AssessmentScore {
        return assessmentScore
    }

    override fun generateGrammarPrompt(userSentence: String): String = """
        Analisis kalimat bahasa Inggris berikut untuk koreksi tata bahasa dan berikan respons terstruktur dengan bahasa indonesia:
        Kalimat: "$userSentence"

        Harap berikan:
        1. Daftar koreksi tata bahasa tertentu yang diperlukan (maksimum 3 poin)
        2. Kalimat yang sudah dikoreksi sepenuhnya

        Format respons sebagai JSON:
        {
            "incorrectSentence": "kalimat asli",
            "corrections": ["titik koreksi 1", "titik koreksi 2", "titik koreksi 3"],
            "correctedSentence": "kalimat yang sudah dikoreksi sepenuhnya"
        }
        
        selalu beri hasil response json dalam bahasa Indonesia
    """.trimIndent()

    override fun generateVocabularyPrompt(userSentence: String): String = """
        Analisis kalimat bahasa Inggris berikut dan berikan alternatif kosakata:
        Kalimat: "$userSentence"

        Harap berikan:
        1. Identifikasi kata sifat atau kata deskriptif utama
        2. Berikan 4 sinonim untuk setiap kata yang diidentifikasi
        
        Format respons sebagai JSON:
        {
            "originalSentence": "kalimat asli",
            "alternativeWords": {
                "word1": ["synonym1", "synonym2", "synonym3", "synonym4"],
                "word2": ["synonym1", "synonym2", "synonym3", "synonym4"]
            }
        }
    """.trimIndent()

    override fun generateGrammarReportCardPrompt(userSentence: String): String = """
        Analisis kalimat bahasa Inggris berikut untuk koreksi tata bahasa (grammar) dan berikan respons terstruktur dengan bahasa indonesia:
        Kalimat: "$userSentence"

        Harap berikan:
        1. Koreksi tata bahasa tertentu yang diperlukan secara grammar, perjelas dalam kalimat
        2. Kalimat yang sudah dikoreksi sepenuhnya

        Format respons sebagai JSON:
        {
            "originalSentence": "kalimat asli",
            "correctedSentence": "kalimat yang sudah dikoreksi sepenuhnya",
            "suggestion": "koreksi tata bahasa yang diperlukan secara grammar"
        }
        
        selalu beri hasil response json dalam bahasa Indonesia
    """.trimIndent()

    override fun generateVocabularyReportCardPrompt(userSentence: String): String = """
        Analisis kalimat bahasa Inggris berikut dan berikan alternatif kosakata yang bisa dipakai dalam kalimat tersebut:
        Kalimat: "$userSentence"

        Harap berikan:
        1. Identifikasi kata sifat atau kata deskriptif utama
        2. Berikan saran deskriptif terkait kata sinonim yang lebih baik untuk kata yang diidentifikasi untuk menambah variasi kosakata (vocabolaries)
        
        Format respons sebagai JSON:
        {
            "originalSentence": "kalimat asli",
            "correctedSentence": "kalimat yang sudah diganti dengan kosakata lain sepenuhnya",
            "suggestion": "saran deskriptif kosakata yang lebih baik untuk kata yang diidentifikasi"
        }
        
        selalu beri hasil response json dalam bahasa Indonesia
    """.trimIndent()
}