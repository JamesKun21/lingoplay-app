package com.alexius.core.domain.usecases.app_entry

import com.alexius.core.domain.model.UserInfo
import com.alexius.core.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateUserInfo @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(userInfo: UserInfo): Flow<Result<Unit>> {
        return repository.addUserInfo(userInfo)
    }
}