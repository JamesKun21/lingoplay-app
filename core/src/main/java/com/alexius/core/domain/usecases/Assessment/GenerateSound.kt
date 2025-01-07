package com.alexius.core.domain.usecases.Assessment

import com.alexius.core.data.remote.speech_ai.SpeechAIApi
import com.alexius.core.data.remote.speech_ai.TextToSpeechRequest
import com.alexius.core.domain.repository.Repository
import okhttp3.ResponseBody
import javax.inject.Inject

class GenerateSound @Inject constructor(
    private val speechAIApi: Repository
) {

    suspend operator fun invoke(id: String, apiKey: String, request: TextToSpeechRequest): ResponseBody {
        return speechAIApi.generateSpeech(id, apiKey, request)
    }
}