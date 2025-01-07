package com.alexius.core.domain.usecases.app_entry

import com.alexius.core.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadAssessmentTaken @Inject constructor(
    private val localUserManger: LocalUserManager
) {

    operator fun invoke(): Flow<Boolean> {
        return localUserManger.readUserTakeAssessment()
    }

}