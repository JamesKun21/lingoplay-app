package com.alexius.talktale.presentation.navgraph_main

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexius.core.domain.usecases.app_entry.DeleteAppEntry
import com.alexius.core.domain.usecases.app_entry.ReadAssessmentTaken
import com.alexius.core.domain.usecases.app_entry.SaveAssessment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavGraphViewModel @Inject constructor (
    private val saveAssessment: SaveAssessment,
    private val readAssessmentTaken: ReadAssessmentTaken,
    private val deleteAppEntry: DeleteAppEntry
): ViewModel() {

    private val _takenAssessment = mutableStateOf(Route.AssessmentNavigation.route)
    val takenAssessment: State<String> = _takenAssessment

    init {
        readAssessmentTaken().onEach { shouldStartFromAssessmentScreen ->
            if(shouldStartFromAssessmentScreen){
                _takenAssessment.value = Route.TalkTaleNavigator.route
                Log.d("NavGraphViewModel", "Assessment taken")
            }else{
                _takenAssessment.value =  Route.AssessmentNavigation.route
                Log.d("NavGraphViewModel", "Assessment not taken yet")
            }
        }.launchIn(viewModelScope)
    }

    fun saveAssessmentTaken() {
        viewModelScope.launch {
            saveAssessment(true)
        }
    }

    fun deleteEntry() {
        viewModelScope.launch {
            deleteAppEntry()
        }
    }
}