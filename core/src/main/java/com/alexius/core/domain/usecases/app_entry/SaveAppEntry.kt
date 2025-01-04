package com.alexius.core.domain.usecases.app_entry

import com.alexius.core.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveAppEntry @Inject constructor(
    private val localUserManger: LocalUserManager
) {

    suspend operator fun invoke() {
        return localUserManger.saveAppEntry()
    }

}