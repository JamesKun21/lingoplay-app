package com.alexius.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Story(
    val title: String,
    val subtitle: String,
    val imageRes: Int,
    val isLocked: Boolean = false,
    val bridgeHint: String,
    val closeStatement: String,
    val paragraphs: List<StoryParagraph>
): Parcelable

@Parcelize
data class StoryParagraph(
    val imageRes: Int,
    val content: String,
    val question: Question
): Parcelable

@Parcelize
data class Question(
    val multipleChoice: Boolean,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val text: String,
    var userAnswer: String = ""
): Parcelable
