package com.alexius.core.domain.model

data class GrammarResponse(
    val incorrectSentence: String,
    val corrections: List<String>,
    val correctedSentence: String
)


data class VocabularyResponse(
    val originalSentence: String,
    val alternativeWords: Map<String, List<String>>
)