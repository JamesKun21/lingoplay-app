package com.alexius.core.data.remote.response

import com.alexius.core.domain.model.AssessmentScore
import com.alexius.core.domain.model.UserInfo

data class AssessmentScoreFirestore(
    var totalScore: Int? = null,
    var grade: String? = null,
    var category: String? = null,
)

fun AssessmentScoreFirestore.toDomainModel(): AssessmentScore {
    return AssessmentScore(
        totalScore = this.totalScore?:0,
        grade = this.grade?: "",
        category = this.category?: ""
    )
}
