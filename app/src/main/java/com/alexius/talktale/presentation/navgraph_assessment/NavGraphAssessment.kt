package com.alexius.talktale.presentation.navgraph_assessment

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.alexius.talktale.presentation.assessment.listening_assessment.ListeningAssessment
import com.alexius.talktale.presentation.assessment.listening_assessment.ListeningAssessmentViewModel
import com.alexius.talktale.presentation.assessment.reading_assessment.ReadingAssessmentScreen
import com.alexius.talktale.presentation.assessment.reading_assessment.ReadingAssessmentViewModel
import com.alexius.talktale.presentation.navgraph_main.Route

@Composable
fun NavGraphAssessment(
    modifier: Modifier = Modifier,
    onEndAssessment: () -> Unit
) {

    val navController = rememberNavController()

    val readingViewModel: ReadingAssessmentViewModel = hiltViewModel()
    val listeningViewModel: ListeningAssessmentViewModel = hiltViewModel()

    NavHost(
        startDestination = Route.AssessmentReading.route,
        navController = navController,
    ){


        composable(
            route = Route.AssessmentReading.route
        ){
            ReadingAssessmentScreen(
                onEndAssessment = {
                    navController.navigate(Route.AssessmentListening.route) {
                        popUpTo(Route.AssessmentReading.route) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                viewModelReading = readingViewModel
            )
        }


        composable(
            route = Route.AssessmentListening.route
        ){
            ListeningAssessment(
                onEndScoreAssessment = onEndAssessment,
                viewModelListening = listeningViewModel,
                readViewModel = readingViewModel,
            )
        }

    }

}