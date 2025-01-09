package com.alexius.talktale.presentation.report_card

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alexius.talktale.presentation.navgraph_main.Route

@Composable
fun ReportCardScreen(
    modifier: Modifier = Modifier,
    onEndReportCard: () -> Unit
) {
    val navController= rememberNavController()

    NavHost(
        startDestination = Route.ReportCardBridgeScreen.route,
        navController = navController
    ){
        composable(
            route = Route.ReportCardBridgeScreen.route
        ){

        }
    }
}