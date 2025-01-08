package com.alexius.talktale.presentation.home_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexius.core.domain.usecases.app_entry.GetUserInfo
import com.alexius.core.domain.usecases.talktalenav.GetLocalAssessmentScore
import com.alexius.core.domain.usecases.talktalenav.GetLocalUserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getLocalUserInfo: GetLocalUserInfo,
    private val getUserInfo: GetUserInfo,
    private val getLocalAssessmentScore: GetLocalAssessmentScore
) : ViewModel() {

    val userInfo = mutableStateOf(getLocalUserInfo())

    val assessmentScore = mutableStateOf(getLocalAssessmentScore())

    init {
        viewModelScope.launch{
            getUserInfo().collect{ response ->
                response.onSuccess {
                    userInfo.value = getLocalUserInfo()
                    assessmentScore.value = getLocalAssessmentScore()
                }
            }
        }
    }
}