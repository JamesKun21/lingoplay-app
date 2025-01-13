package com.alexius.core.domain.usecases.app_entry

import com.alexius.core.domain.manager.LocalUserManager
import javax.inject.Inject

class DeleteAppEntry @Inject constructor(
    private val localUserManager: LocalUserManager
) {
    suspend operator fun invoke() {
        localUserManager.deleteAppEntry()
    }
}