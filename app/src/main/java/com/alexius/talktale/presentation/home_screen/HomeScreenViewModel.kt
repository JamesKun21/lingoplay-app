package com.alexius.talktale.presentation.home_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexius.core.domain.usecases.app_entry.GetUserInfo
import com.alexius.core.domain.usecases.talktalenav.GetLocalAssessmentScore
import com.alexius.core.domain.usecases.talktalenav.GetLocalUserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getLocalUserInfo: GetLocalUserInfo,
    private val getUserInfo: GetUserInfo,
    private val getLocalAssessmentScore: GetLocalAssessmentScore
) : ViewModel() {

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName

    private val _category = MutableStateFlow("")
    val category: StateFlow<String> = _category

    init {
        viewModelScope.launch{
            getUserInfo().collect{ response ->
                response.onSuccess {
                    val userInfo = it.first
                    _userName.value = if (userInfo.full_name.isNotEmpty()) userInfo.full_name else "User"
                    Log.d("HomeScreenViewModel", "User Info Name: ${_userName.value}")
                    Log.d("HomeScreenViewModel", "User local Name: ${getLocalUserInfo().full_name}")

                    val assessmentScore = it.second
                    _category.value = assessmentScore.category
                }
            }
        }
    }
}