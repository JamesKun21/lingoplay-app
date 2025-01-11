package com.alexius.core.domain.model

data class ReportCardAnalysis(
    val originalSentence: String,
    val correctedSentence: String,
    val suggestion: String,
)
