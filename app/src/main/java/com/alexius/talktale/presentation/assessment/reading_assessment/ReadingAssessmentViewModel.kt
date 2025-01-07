package com.alexius.talktale.presentation.assessment.reading_assessment

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.alexius.core.domain.model.ReadingQuestionAssessment
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class ReadingAssessmentViewModel: ViewModel() {

    private val _currentQuestionIndex = mutableIntStateOf(-1)
    val currentQuestionIndex: State<Int> = _currentQuestionIndex

    private val _questions = mutableStateListOf<ReadingQuestionAssessment>()
    val questions: List<ReadingQuestionAssessment> = _questions

    init {
        // Initialize your questions
        _questions.addAll(listOf(
            ReadingQuestionAssessment(1, "Mike ... down the stairs in order to catch the last bus home.",
                listOf("hurried", "sped up", "pushed", "urged"), correctAnswerIndex = 0),

            ReadingQuestionAssessment(2, "Before he could start applying for jobs, he needed to ... his resume.",
                listOf("back up", "fill in", "inform", "update"), correctAnswerIndex = 3),

            ReadingQuestionAssessment(3, "She decided she wanted to ... a few extra pounds and started a diet.",
                listOf("throw away", "get rid of", "dispose of", "leave off"), correctAnswerIndex = 1),

            ReadingQuestionAssessment(4, "He was hungry and asked her if she wanted to ... a bite.",
                listOf("grab", "grasp", "grip", "seize"), correctAnswerIndex = 0),

            ReadingQuestionAssessment(5, "Who is ... the baby for you?",
                listOf("noticing", "minding", "observing", "regarding"), correctAnswerIndex = 1),

            ReadingQuestionAssessment(6, "It is certainly true that ... was not one of his aspirations, but after his novel became an international best seller he had to adjust to public attentiton.",
                listOf("rank", "position", "status", "fame"), correctAnswerIndex = 3),

            ReadingQuestionAssessment(7, "She never had many friends her own age because she could not figure out how to ... kids her own age.",
                listOf("drop in on", "go along", "fit in with", "pretend to be"), correctAnswerIndex = 2),

            ReadingQuestionAssessment(8, "Not surprisingly, her open and friendly demeanor made it easy for potential new clients to ... her and ask for assistance.",
                listOf("attempt", "draw near", "move toward", "approach"), correctAnswerIndex = 3),

            ReadingQuestionAssessment(9, "In many less-developed countries, visitors often observe much more ... on the side of roads than is typically seen in Europe or the US.",
                listOf("fragments", "disorder", "litter", "residues"), correctAnswerIndex = 2),

            ReadingQuestionAssessment(10, "In remote villages, people are more likely to rely on ... transportation than in larger cities.",
                listOf("public", "private", "motorized", "traditional"), correctAnswerIndex = 3),
        ))
    }

    fun selectAnswer(index: Int) {
        _questions[_currentQuestionIndex.intValue].selectedAnswer = index
    }

    fun moveToNextQuestion() {
        if (_currentQuestionIndex.intValue < _questions.size - 1) {
            _currentQuestionIndex.intValue++
        }
    }

    fun moveToPreviousQuestion() {
        if (_currentQuestionIndex.intValue > 0) {
            _currentQuestionIndex.intValue--
        }
    }

}