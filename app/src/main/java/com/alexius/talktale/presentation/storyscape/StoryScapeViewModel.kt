package com.alexius.talktale.presentation.storyscape

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexius.core.data.remote.speech_ai.TextToSpeechRequest
import com.alexius.core.domain.model.GrammarResponse
import com.alexius.core.domain.model.Story
import com.alexius.core.domain.model.VocabularyResponse
import com.alexius.core.domain.usecases.Assessment.GenerateSound
import com.alexius.core.domain.usecases.talktalenav.GenerateGrammarPrompt
import com.alexius.core.domain.usecases.talktalenav.GenerateVocabPrompt
import com.alexius.core.domain.usecases.talktalenav.GetStories
import com.alexius.core.util.Constants.SPEECHAI_API_KEY
import com.alexius.core.util.UIState
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject


class StoryScapeViewModel @Inject constructor(
    private val getStories: GetStories,
    private val generateSound: GenerateSound,
    private val generateVocabPrompt: GenerateVocabPrompt,
    private val generateGrammarPrompt: GenerateGrammarPrompt,
    private val geminiModel: GenerativeModel
) : ViewModel() {

    private val _grammarState = MutableStateFlow<GrammarResponse?>(null)
    val grammarState: StateFlow<GrammarResponse?> = _grammarState

    private val _vocabularyState = MutableStateFlow<VocabularyResponse?>(null)
    val vocabularyState: StateFlow<VocabularyResponse?> = _vocabularyState


    private val _story = mutableStateOf(Story(
        title = "",
        subtitle = "",
        imageRes = 0,
        isLocked = false,
        bridgeHint = "",
        closeStatement = "",
        paragraphs = emptyList()
    ))
    val story = _story

    private val _currentPharagraphIndex = mutableIntStateOf(0)
    val currentPharagraphIndex: State<Int> = _currentPharagraphIndex

    private val _uiStateAudio = MutableStateFlow<UIState<ByteArray>>(UIState.Loading)
    val uiStateAudio: StateFlow<UIState<ByteArray>> = _uiStateAudio

    init {
        _story.value = getStories()
    }

    fun analyzeText() {
        viewModelScope.launch {
            try {
                val answerList = getAllUserAnswersNotMultipleChoice()

                val answer1 = answerList[0]

                val answer2 = answerList[1]

                // Get grammar analysis
                val grammarPrompt = generateGrammarPrompt(answer1)
                val grammarResponse = geminiModel.generateContent(grammarPrompt)
                    .text?.let { Json.decodeFromString<GrammarResponse>(it) }
                _grammarState.value = grammarResponse

                // Get vocabulary analysis
                val vocabPrompt = generateVocabPrompt(answer2)
                val vocabResponse = geminiModel.generateContent(vocabPrompt)
                    .text?.let { Json.decodeFromString<VocabularyResponse>(it) }
                _vocabularyState.value = vocabResponse
            } catch (e: Exception) {
                // Handle errors
                Log.d("StoryScapeViewModel", "Error: ${e.message}")
            }
        }
    }

    fun moveToNextQuestion() {
        if (_currentPharagraphIndex.intValue < _story.value.paragraphs.size - 1) {
            _currentPharagraphIndex.intValue++
        }
    }

    fun answerQuestion(answer: String) {
        val currentParagraphQuestion = _story.value.paragraphs[_currentPharagraphIndex.intValue].question
        currentParagraphQuestion.userAnswer = answer
    }

    private fun getAllUserAnswersNotMultipleChoice(): List<String> {
        val userAnswers = mutableListOf<String>()
        _story.value.paragraphs.forEach { paragraph ->
            if (!paragraph.question.multipleChoice) {
                userAnswers.add(paragraph.question.userAnswer)
            }
        }
        return userAnswers
    }

    fun generateSpeech(text: String) {
        viewModelScope.launch {
            _uiStateAudio.value = UIState.Loading
            try {
                val request = TextToSpeechRequest(
                    text = text,
                    model_id = "eleven_multilingual_v2"
                )

                val response = generateSound(
                    id = "JBFqnCBsd6RMkjVDRZzb",
                    apiKey = SPEECHAI_API_KEY,
                    request = request,
                )

                val audioData = response.bytes()
                _uiStateAudio.value = UIState.Success(audioData)
            } catch (e: Exception) {
                _uiStateAudio.value = UIState.Error(e.message ?: "Unknown error")
                delay(2000)
                _uiStateAudio.value = UIState.Loading
            }
        }
    }
}