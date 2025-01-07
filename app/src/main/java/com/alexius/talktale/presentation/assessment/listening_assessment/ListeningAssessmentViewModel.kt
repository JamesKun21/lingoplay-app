package com.alexius.talktale.presentation.assessment.listening_assessment

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexius.core.data.remote.speech_ai.TextToSpeechRequest
import com.alexius.core.domain.model.AssessmentScore
import com.alexius.core.domain.model.QuestionAssessment
import com.alexius.core.domain.usecases.Assessment.GenerateSound
import com.alexius.core.domain.usecases.Assessment.GetFinalScore
import com.alexius.core.util.Constants.SPEECHAI_API_KEY
import com.alexius.core.util.Constants.SPEECHAI_BASE_URL
import com.alexius.core.util.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListeningAssessmentViewModel @Inject constructor(
    private val generateSound: GenerateSound,
    private val getFinalScore: GetFinalScore
) : ViewModel() {

    private val _currentQuestionIndex = mutableIntStateOf(0)
    val currentQuestionIndex: State<Int> = _currentQuestionIndex

    private val _questions = mutableStateListOf<QuestionAssessment>()
    val questions: List<QuestionAssessment> = _questions

    private val _finalScore = mutableIntStateOf(0)

    private val _finalScoreData = mutableStateOf(AssessmentScore(
        totalScore = 0,
        grade = "A1/A2",
        category = "Beginner"
    ))

    val finalScoreData: State<AssessmentScore> = _finalScoreData

    private val _uiStateAudio = MutableStateFlow<UIState<ByteArray>>(UIState.Loading)
    val uiStateAudio: StateFlow<UIState<ByteArray>> = _uiStateAudio

    init {
        // Initialize your questions
        _questions.addAll(listOf(
            QuestionAssessment(1, "Excuse me, do you know where the nearest park is?",
                listOf("Yes, it’s just down this street.", "I’m not sure.", "Why do you want to go there?", "Parks are boring."), correctAnswerIndex = 0),

            QuestionAssessment(2, "Could you pass me the salt, please?",
                listOf("Here you go.", "I don’t like salt.", "Why do you need it?", "Maybe later."), correctAnswerIndex = 0),

            QuestionAssessment(3, "Do you like pizza?",
                listOf("Yes, it’s my favorite!", "I don’t have time.", "What kind of question is that?", "Sometimes I watch TV."), correctAnswerIndex = 0),

            QuestionAssessment(4, "What time will the movie start?",
                listOf("I don’t watch movies.", "Why are you asking me?", "It starts at 7 PM.", "Maybe tomorrow."), correctAnswerIndex = 2),

            QuestionAssessment(5, "Did you hear about the new restaurant that opened downtown?",
                listOf("I don’t eat out often.", "Yes, I heard it’s amazing.", "Downtown is far away.", "No, but I don’t like restaurants."), correctAnswerIndex = 1),

            QuestionAssessment(6, "Can you remind me how to get to the library?",
                listOf("Why would I go there?", "I’ve never been there.", "Libraries are quiet.", "Turn left at the next corner."), correctAnswerIndex = 3),

            QuestionAssessment(7, "I was thinking of taking the scenic route to the beach. What do you think?",
                listOf("That sounds like a great idea!", "Why would you do that?", "I think the beach is overrated.", "It depends on how much time you have."), correctAnswerIndex = 0),

            QuestionAssessment(8, "The meeting has been rescheduled to 3 PM tomorrow. Does that work for you?",
                listOf("I don’t like meetings.", "Why did they change it?", "Yes, I’ll adjust my schedule.", "That’s too late for me."), correctAnswerIndex = 2),

            QuestionAssessment(9, "What’s the best way to save on travel expenses?",
                listOf("Why do you care about expenses?", "I don’t travel much.", "Just spend less.", "Booking flights early often helps."), correctAnswerIndex = 3),

            QuestionAssessment(10, "Have you had a chance to look over the proposal I sent you?",
                listOf("No, I don’t have time for that.", "Yes, I reviewed it this morning.", "Why did you send it to me?", "I’ll get to it when I can."), correctAnswerIndex = 1),
        ))
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

    fun selectAnswer(index: Int) {
        _questions[_currentQuestionIndex.intValue].selectedAnswer = index
    }

    fun moveToNextQuestion() {
        checkAnswer()
        if (_currentQuestionIndex.intValue < _questions.size - 1) {
            _currentQuestionIndex.intValue++
        }
    }

    private fun checkAnswer() {
        val currentQuestion = _questions[_currentQuestionIndex.intValue]
        if (currentQuestion.selectedAnswer == currentQuestion.correctAnswerIndex) {
            _finalScore.intValue += 10
        }
    }

    fun getFinalScore() {
        _finalScoreData.value = getFinalScore(_finalScore.intValue)
    }
}