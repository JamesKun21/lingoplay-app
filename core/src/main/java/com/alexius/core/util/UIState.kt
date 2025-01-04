package com.alexius.core.util

sealed class UIState<out T : Any?> {

    object Loading : UIState<Nothing>()

    data class Success<out T : Any>(val data: T) : UIState<T>()

    data class Error(val errorMessage: String) : UIState<Nothing>()
}