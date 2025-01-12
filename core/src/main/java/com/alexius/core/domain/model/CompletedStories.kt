package com.alexius.core.domain.model

data class CompletedStories(
    val beginner: ArrayList<Boolean>,
    val intermediate: ArrayList<Boolean>,
    val advancedTales: ArrayList<Boolean>,
    val careerTales: ArrayList<Boolean>
)
