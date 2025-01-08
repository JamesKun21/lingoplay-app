package com.alexius.core.domain.usecases.Assessment

import com.alexius.core.domain.model.AssessmentScore
import com.alexius.core.domain.model.UserInfo
import com.alexius.core.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateAssessmentScore @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(assessmentScore: AssessmentScore): Flow<Result<Unit>> {
        return repository.updateAssessmentScore(assessmentScore)
    }
}