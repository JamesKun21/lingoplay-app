package com.alexius.core.data.manager

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.alexius.core.domain.manager.LocalUserManager
import com.alexius.core.util.Constants
import com.alexius.core.util.Constants.USER_SETTINGS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalUserManagerImplementation @Inject constructor(
    private val application: Application
) : LocalUserManager {

    override suspend fun saveAppEntry() {
        application.dataStore.edit { settings ->
            settings[PreferenceKeys.APP_ENTRY] = true
        }
    }

    override fun readAppEntry(): Flow<Boolean> {
        return application.dataStore.data.map { preferences ->
            preferences[PreferenceKeys.APP_ENTRY] ?: false
        }
    }

    override suspend fun deleteAppEntry() {
        application.dataStore.edit { settings ->
            settings[PreferenceKeys.APP_ENTRY] = false
        }
    }

    override suspend fun saveUserTakeAssessment(value: Boolean) {
        application.dataStore.edit { settings ->
            settings[PreferenceKeys.ASSESSMENT_ENTRY] = value
        }
    }

    override fun readUserTakeAssessment(): Flow<Boolean> {
        return application.dataStore.data.map { preferences ->
            preferences[PreferenceKeys.ASSESSMENT_ENTRY] ?: false
        }
    }

}

private val readOnlyProperty = preferencesDataStore(name = USER_SETTINGS)

val Context.dataStore: DataStore<Preferences> by readOnlyProperty

private object PreferenceKeys {
    val APP_ENTRY = booleanPreferencesKey(Constants.APP_ENTRY)
    val ASSESSMENT_ENTRY = booleanPreferencesKey(Constants.ASSESSMENT_ENTRY)
}