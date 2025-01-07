package com.alexius.core.domain.usecases.app_entry

import com.alexius.core.domain.model.UserInfo
import com.alexius.core.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserInfo @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(): Flow<Result<Unit>> {
        return repository.getUserInfoAndAssessmentScore()
    }
}