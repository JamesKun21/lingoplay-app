package com.alexius.talktale.presentation.storyscape

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alexius.core.domain.model.Story
import com.alexius.talktale.presentation.navgraph_main.Route
import com.alexius.talktale.presentation.storyscape.choose_screen.StoryChooseScreen
import com.alexius.talktale.ui.theme.White

@Composable
fun StoryScapeScreen(
    modifier: Modifier = Modifier,
    category: String,
    story: Story,
    onEndStory: () -> Unit
) {

    val navController= rememberNavController()

    NavHost(
        startDestination = Route.StoryScapeChooseScreen,
        navController = navController
    ){
        composable(
            route = Route.StoryScapeChooseScreen.route
        ){
            StoryChooseScreen(
                category = category,
                onClickPlay = {

                }
            )
        }


    }

}