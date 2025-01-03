package com.alexius.talktale.presentation.navgraph

import androidx.navigation.NamedNavArgument

sealed class Route(
    val route: String,
) {
    object OnBoardingScreen : Route(route = "onBoardingScreen")

    object HomeScreen : Route(route = "homeScreen")

    object SearchScreen : Route(route = "searchScreen")

    object BookmarkScreen : Route(route = "bookMarkScreen")

    object DetailsScreen : Route(route = "detailsScreen")

    object AppStartNavigation : Route(route = "appStartNavigation")

    object TalkTaleNavigation : Route(route = "talkTaleNavigation")

    object NewsNavigatorScreen : Route(route = "newsNavigator")
}