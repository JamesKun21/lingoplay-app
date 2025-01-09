package com.alexius.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class GrammarResponse(
    val incorrectSentence: String,
    val corrections: List<String>,
    val correctedSentence: String
)

@Serializable
data class VocabularyResponse(
    val originalSentence: String,
    val alternativeWords: Map<String, List<String>>
)