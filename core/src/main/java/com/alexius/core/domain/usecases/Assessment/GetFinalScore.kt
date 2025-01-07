package com.alexius.core.domain.usecases.Assessment

import com.alexius.core.domain.model.AssessmentScore
import kotlinx.coroutines.flow.Flow

class GetFinalScore {

    operator fun invoke(totalCorrect: Int): AssessmentScore {

        when(totalCorrect) {
            in 0..60 -> {
                return AssessmentScore(
                    totalScore = totalCorrect,
                    grade = "A1/A2",
                    category = "Beginner"
                )
            }
            in 61..85 -> {
                return AssessmentScore(
                    totalScore = totalCorrect,
                    grade = "B1/B2",
                    category = "Intermediate"
                )
            }
            in 86..100 -> {
                return AssessmentScore(
                    totalScore = totalCorrect,
                    grade = "C1/C2",
                    category = "Advanced"
                )
            }
            else -> {
                return AssessmentScore(
                    totalScore = totalCorrect,
                    grade = "Excellent",
                    category = "Expert"
                )
            }
        }

    }
}