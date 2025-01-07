package com.alexius.core.domain.usecases.app_entry

import com.alexius.core.domain.manager.LocalUserManager
import javax.inject.Inject

class SaveAssessment @Inject constructor(
    private val localUserManger: LocalUserManager
) {

    suspend operator fun invoke(value: Boolean) {
        return localUserManger.saveUserTakeAssessment(value)
    }

}