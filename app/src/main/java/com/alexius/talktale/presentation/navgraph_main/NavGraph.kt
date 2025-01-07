package com.alexius.talktale.presentation.navgraph_main

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.alexius.talktale.Greeting
import com.alexius.talktale.presentation.navgraph_assessment.NavGraphAssessment
import com.alexius.talktale.presentation.navgraph_entry.NavGraphEntry

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    startDestination: String
) {
    val navController = rememberNavController()

    val viewModel: NavGraphViewModel = hiltViewModel()

    NavHost(
        startDestination = startDestination,
        navController = navController,
    ){
        navigation(
            route = Route.AppStartNavigation.route,
            startDestination = Route.NavigationEntry.route
        ){
            composable(
                route = Route.NavigationEntry.route
            ){
                NavGraphEntry()
            }
        }

        navigation(
            route = Route.MainNavigation.route,
            startDestination = viewModel.takenAssessment.value
        ){
            composable(route = Route.AssessmentNavigation.route){
                NavGraphAssessment(
                    onEndAssessment = {
                        viewModel.saveAssessmentTaken()
                        navController.navigate(Route.StoryScopeNavigation.route){
                            popUpTo(Route.AssessmentNavigation.route){
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable(route = Route.StoryScopeNavigation.route){

                Greeting(name = "Android")
            }
        }

    }
}