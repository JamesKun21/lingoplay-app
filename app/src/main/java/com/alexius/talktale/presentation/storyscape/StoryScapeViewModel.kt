package com.alexius.talktale.presentation.storyscape

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.alexius.core.domain.model.Story
import com.alexius.core.domain.usecases.talktalenav.GetStories
import com.alexius.core.util.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class StoryScapeViewModel @Inject constructor(
    private val getStories: GetStories
) : ViewModel() {

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
}