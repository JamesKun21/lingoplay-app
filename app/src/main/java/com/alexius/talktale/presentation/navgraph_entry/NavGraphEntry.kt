package com.alexius.talktale.presentation.navgraph_entry

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.alexius.talktale.presentation.navgraph.Route
import com.alexius.talktale.presentation.onboarding.OnboardingScreen
import com.alexius.talktale.ui.theme.TalkTaleTheme

@Composable
fun NavGraphEntry(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    NavHost(
        startDestination = Route.OnBoardingDisplay.route,
        navController = navController,
    ){
        navigation(
            route = Route.OnBoardingDisplay.route,
            startDestination = Route.OnBoardingScreen.route
        ){
            composable(
                route = Route.OnBoardingScreen.route
            ){
                OnboardingScreen(
                    onSignInButtonClick = {
                        navController.navigate(Route.SignInScreen.route) {
                            launchSingleTop = true
                        }
                    },
                    onSignUpButtonClick = {
                        navController.navigate(Route.SignDisplay.route) {
                            launchSingleTop = true
                        }
                    }
                )
            }
        }

      /*  navigation(
            route = Route.SignDisplay.route,
            startDestination = Route.SignUpScreen.route
        ){
            composable(
                route = Route.SignUpScreen.route
            ){
                SignUpScreen(
                    onSignInButtonClick = {
                        navController.navigate(Route.SignInScreen.route)
                    }
                )
            }

            composable(
                route = Route.SignInScreen.route
            ){
                SignInScreen()
            }
        }*/

    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun NavGraphEntryPrev() {
    TalkTaleTheme {
        NavGraphEntry()
    }
}