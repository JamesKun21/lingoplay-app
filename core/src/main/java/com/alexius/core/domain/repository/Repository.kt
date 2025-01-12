package com.alexius.core.domain.repository

import com.alexius.core.data.remote.speech_ai.TextToSpeechRequest
import com.alexius.core.domain.model.AssessmentScore
import com.alexius.core.domain.model.CompletedStories
import com.alexius.core.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody

interface Repository {

    suspend fun generateSpeech(id: String, apiKey: String, request: TextToSpeechRequest) : ResponseBody

    suspend fun addUserInfo(userInfo: UserInfo): Flow<Result<Unit>>

    suspend fun getUserInfoAndAssessmentScore(): Flow<Result<Pair<UserInfo, AssessmentScore>>>

    suspend fun getCompletedStories(): Flow<Result<CompletedStories>>

    suspend fun updateAssessmentScore(assessmentScore: AssessmentScore): Flow<Result<Unit>>

    suspend fun updateCompletedStories(completedStories: CompletedStories): Flow<Result<Unit>>

    fun changeLocalUserInfoName(name: String)

    fun getLocalUserInfo(): UserInfo

    fun getLocalAssessmentScore(): AssessmentScore

    fun generateGrammarPrompt(userSentence: String): String

    fun generateVocabularyPrompt(userSentence: String): String

    fun generateGrammarReportCardPrompt(userSentence: String): String

    fun generateVocabularyReportCardPrompt(userSentence: String): String
}