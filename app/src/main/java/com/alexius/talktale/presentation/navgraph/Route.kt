package com.alexius.talktale.presentation.navgraph

import androidx.navigation.NamedNavArgument

sealed class Route(
    val route: String,
) {
    object OnBoardingDisplay : Route(route = "onBoardingDisplay")

    object OnBoardingScreen : Route(route = "onBoardingScreen")

    object OnBoarding1Screen : Route(route = "onBoarding1Screen")

    object OnBoarding2Screen : Route(route = "onBoarding2Screen")

    object OnBoarding3Screen : Route(route = "onBoarding3Screen")

    object BridgeToSignInScreen : Route(route = "bridgeToSignInScreen")

    object SignDisplay : Route(route = "signDisplay")

    object SignUpScreen : Route(route = "signUpScreen")

    object SignInScreen : Route(route = "signInScreen")

    object HomeScreen : Route(route = "homeScreen")

    object SearchScreen : Route(route = "searchScreen")

    object BookmarkScreen : Route(route = "bookMarkScreen")

    object DetailsScreen : Route(route = "detailsScreen")

    object AppStartNavigation : Route(route = "appStartNavigation")

    object NavigationEntry : Route(route = "navigationEntry")

    object MainNavigation : Route(route = "mainNavigation")

    object MainNavigatorScreen : Route(route = "mainNavigator")
}