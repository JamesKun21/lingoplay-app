package com.alexius.core.domain.manager

import kotlinx.coroutines.flow.Flow

interface LocalUserManager {

    suspend fun saveAppEntry()

    suspend fun deleteAppEntry()

    fun readAppEntry(): Flow<Boolean>

    suspend fun saveUserTakeAssessment(value: Boolean)

    fun readUserTakeAssessment(): Flow<Boolean>
}