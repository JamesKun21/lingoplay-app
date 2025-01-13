package com.alexius.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ReportCardAnalysis(
    val originalSentence: String,
    val correctedSentence: String,
    val suggestion: String,
)
