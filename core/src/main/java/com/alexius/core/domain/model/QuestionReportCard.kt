package com.alexius.core.domain.model

data class QuestionReportCard(
    val questionText: String,
    val options: List<String>,
    val imageRes: Int,
    val correctAnswerIndex: Int,
    var selectedAnswer: Int? = null
)
