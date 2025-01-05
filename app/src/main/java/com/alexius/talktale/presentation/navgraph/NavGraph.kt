package com.alexius.talktale.presentation.navgraph

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.alexius.talktale.Greeting
import com.alexius.talktale.presentation.navgraph_entry.NavGraphEntry

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    startDestination: String
) {
    val navController = rememberNavController()

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
            startDestination = Route.MainNavigatorScreen.route
        ){
            composable(route = Route.MainNavigatorScreen.route){
                Greeting("BERHASIL CUY")
            }
        }

    }
}