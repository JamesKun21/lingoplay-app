package com.alexius.core.domain.model

data class Story(
    val title: String,
    val subtitle: String,
    val imageRes: Int,
    val isLocked: Boolean = false,
    val bridgeHint: String,
    val closeStatement: String,
    val paragraphs: List<StoryParagraph>
)


data class StoryParagraph(
    val imageRes: Int,
    val content: String,
    val question: Question
)


data class Question(
    val multipleChoice: Boolean,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val text: String,
    var userAnswer: String = ""
)
