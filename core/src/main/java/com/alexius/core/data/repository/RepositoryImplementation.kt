package com.alexius.core.data.repository

import android.util.Log
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
                .set(userInfo)
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
            _userInfo = userInfoSnapshot.toObject(UserInfo::class.java) ?: throw Exception("User info not found")

            // Get assessment score
            val assessmentScoreSnapshot = db.collection("users").document(userId)
                .collection("assessment_score").get().await()
            _assessmentScore = assessmentScoreSnapshot.documents.firstOrNull()?.toObject(AssessmentScore::class.java)
                ?: throw Exception("Assessment score not found")

            emit(Result.success(Unit))
        } catch (e: Exception) {
            Log.w(TAG, "Error fetching user info and assessment score", e)
            emit(Result.failure(e))
        }
    }

}