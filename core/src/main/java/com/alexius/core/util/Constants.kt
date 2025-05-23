package com.alexius.core.util

import com.alexius.core.BuildConfig

object Constants {
    const val USER_SETTINGS = "user_settings"
    const val APP_ENTRY = "app_entry"
    const val ASSESSMENT_ENTRY = "assessment_entry"

    const val WEB_CLIENT_ID = BuildConfig.WEB_CLIENT_ID

    const val SPEECHAI_BASE_URL = "https://api.elevenlabs.io/v1/"

    const val SPEECHAI_API_KEY = BuildConfig.SPEECHAI_API_KEY

    const val GEMINI_API_KEY = BuildConfig.GEMINI_API_KEY
}