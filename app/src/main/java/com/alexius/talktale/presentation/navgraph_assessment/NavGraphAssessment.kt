package com.alexius.talktale.presentation.navgraph_assessment

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.alexius.talktale.presentation.navgraph_main.Route

@Composable
fun NavGraphAssessment(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    NavHost(
        startDestination = Route.AssessmentReading.route,
        navController = navController,
    ){

        navigation(
            route = Route.AssessmentReading.route,
            startDestination = Route.AssessmentReading.route
        ){
            composable(
                route = Route.AssessmentReading.route
            ){

            }
        }

        composable(
            route = Route.AssessmentListening.route
        ){

        }

    }

}