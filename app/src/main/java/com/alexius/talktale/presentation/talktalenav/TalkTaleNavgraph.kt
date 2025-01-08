package com.alexius.talktale.presentation.talktalenav

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Article
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.alexius.talktale.presentation.navgraph_main.Route
import com.alexius.talktale.presentation.talktalenav.components.TalkTaleBottomNavigation

@Composable
fun TalkTaleNavgraph(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current
) {
    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(Icons.Default.Home, "Beranda"),
            BottomNavigationItem(Icons.Outlined.Book, "StoryScape"),
            BottomNavigationItem(Icons.AutoMirrored.Outlined.Article, "Report Card"),
            BottomNavigationItem(Icons.Outlined.Person, "Profil")
        )
    }

    // Line 34 - 45 is configuration for the bottom navigation bar pages to be displayed
    val navController = rememberNavController()
    val backstackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableIntStateOf(0) // Default value is 0 (home screen)
    }

    selectedItem = remember(backstackState) {
        when (backstackState?.destination?.route) {
            Route.HomeScreen.route -> 0
            Route.StoryScapeScreen.route -> 1
            Route.ReportCardScreen.route -> 2
            Route.ProfileScreen.route -> 3
            else -> 0
        }
    }

    // This will hide the bottom navigation bar when the user navigates to the detail screen
    val isBottomBarVisible = remember(key1 = backstackState) {
        backstackState?.destination?.route != Route.StoryScopeNavigation.route &&
                backstackState?.destination?.route != Route.ReportCardNavigation.route
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            if (isBottomBarVisible) {
                TalkTaleBottomNavigation(
                    items = bottomNavigationItems,
                    selected = selectedItem,
                    onItemClick = { index ->
                        when (index) {
                            0 -> navigateToTap(navController, Route.HomeScreen.route)
                            1 -> navigateToTap(navController, Route.StoryScapeScreen.route)
                            2 -> navigateToTap(navController, Route.ReportCardScreen.route)
                            3 -> navigateToTap(navController, Route.ProfileScreen.route)
                        }
                    })
            }
        }
    ) {
        val bottomPadding = it.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = modifier.padding(bottom = bottomPadding)
        ){
            composable(route = Route.HomeScreen.route){

            }
        }
    }
}

data class BottomNavigationItem(
    val icon: ImageVector,
    val text: String
)

private fun navigateToTap(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { homeScreen ->
            popUpTo(homeScreen) {
                saveState = true
            }
            restoreState = true
            // Reuse the existing instance of the composable (case: if user taps on the same tab,
            // it will not create a new instance)
            launchSingleTop = true
        }
    }
}