package com.alexius.core.domain.repository

import com.alexius.core.data.remote.speech_ai.TextToSpeechRequest
import com.alexius.core.domain.model.AssessmentScore
import com.alexius.core.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody

interface Repository {

    suspend fun generateSpeech(id: String, apiKey: String, request: TextToSpeechRequest) : ResponseBody

    suspend fun addUserInfo(userInfo: UserInfo): Flow<Result<Unit>>

    suspend fun getUserInfoAndAssessmentScore(): Flow<Result<Unit>>
}