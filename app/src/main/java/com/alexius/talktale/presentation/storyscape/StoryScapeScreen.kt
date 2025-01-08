package com.alexius.talktale.presentation.storyscape

import android.media.MediaPlayer
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alexius.core.domain.model.Story
import com.alexius.core.util.UIState
import com.alexius.talktale.Greeting
import com.alexius.talktale.presentation.navgraph_main.Route
import com.alexius.talktale.presentation.storyscape.choose_screen.StoryChooseScreen
import com.alexius.talktale.presentation.storyscape.story_quiz.StoryBridgeScreen
import com.alexius.talktale.presentation.storyscape.story_quiz.StoryQuizDisplay
import com.alexius.talktale.presentation.storyscape.wordwizard.WordWizardScreen
import java.io.File

@Composable
fun StoryScapeScreen(
    modifier: Modifier = Modifier,
    category: String,
    story: Story,
    viewModelStoryScape: StoryScapeViewModel,
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
            /*StoryChooseScreen(
                category = category,
                onClickPlay = {
                    navigateTo(navController, Route.StoryScapeBridgingScreen.route)
                }
            )*/

            Greeting(name = "StoryScape")
        }

        composable(
            route = Route.StoryScapeBridgingScreen.route
        ){
            StoryBridgeScreen(
                onBackClick = {navigateTo(navController, Route.StoryScapeChooseScreen.route)},
                bridgeHint = story.bridgeHint,
                imageDrawable = story.imageRes,
                title = story.title,
                subtitle = story.subtitle,
                onStartButton = {
                    navigateTo(navController, Route.StoryScapeQuizScreen.route)
                }
            )
        }

        composable(
            route = Route.StoryScapeQuizScreen.route
        )
        {
            var showExitDialog = rememberSaveable() { mutableStateOf(false) }

            var selectedAnswerIndex = rememberSaveable() { mutableIntStateOf(-1) }

            val uiStateAudio by viewModelStoryScape.uiStateAudio.collectAsState()

            var isLoading by remember { mutableStateOf(false) }

            val context = LocalContext.current

            val currentParagraphIndex by remember { mutableIntStateOf(viewModelStoryScape.currentPharagraphIndex.value) }

            var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }

            DisposableEffect(Unit) {
                onDispose {
                    mediaPlayer?.release()
                }
            }

            BackHandler {
                showExitDialog.value = true
            }

            StoryQuizDisplay(
                onExitClick = {
                    showExitDialog.value = true
                },
                onAnswerFieldChange = {viewModelStoryScape.answerQuestion(it)},
                answerInput = viewModelStoryScape.story.value.paragraphs[currentParagraphIndex].question.userAnswer,
                question = story.paragraphs[currentParagraphIndex].question.text,
                paragraph = story.paragraphs[currentParagraphIndex].content,
                imageDrawable = story.paragraphs[currentParagraphIndex].imageRes,
                onPlayAudioClick = {
                    viewModelStoryScape.generateSpeech(story.paragraphs[currentParagraphIndex].question.text)
                },
                isMultipleChoice = story.paragraphs[currentParagraphIndex].question.multipleChoice,
                options = story.paragraphs[currentParagraphIndex].question.options,
                onAnswerSelected = {},
                onNextClick = {
                    if (viewModelStoryScape.currentPharagraphIndex.value == viewModelStoryScape.story.value.paragraphs.size - 1) {
                        // Get all the answers that is not multiple choice and generate AI grammar and vocabulary
                      /*  viewModelStoryScape.analyzeText()*/
                        navigateTo(navController, Route.StoryScapeEndScreen.route)
                    } else {
                        viewModelStoryScape.moveToNextQuestion()
                        selectedAnswerIndex.intValue = -1
                    }
                },
                showExitDialog = showExitDialog,
                selectedAnswerIndex = selectedAnswerIndex,
            )

            when (val state = uiStateAudio) {
                is UIState.Loading -> {
                    // Show loading indicator
                }
                is UIState.Success -> {
                    // Play audio
                    LaunchedEffect(state) {
                        mediaPlayer?.release()
                        mediaPlayer = MediaPlayer().apply {
                            val file = File.createTempFile("audio", ".mp3", context.cacheDir)
                            file.writeBytes(state.data)
                            setDataSource(file.path)
                            prepare()
                            start()
                        }
                        isLoading = false
                    }
                }
                is UIState.Error -> {
                    // Show error message
                    if (state.errorMessage != null) {
                        Toast.makeText(context, state.errorMessage, Toast.LENGTH_SHORT).show()
                    }
                    isLoading = false
                }
            }

        }

        composable(
            route = Route.StoryScapeEndScreen.route
        )
        {
            StoryBridgeScreen(
                onBackClick = {navigateTo(navController, Route.StoryScapeChooseScreen.route)},
                bridgeHint = story.closeStatement,
                imageDrawable = story.imageRes,
                title = story.title,
                subtitle = story.subtitle,
                onStartButton = {
                    navigateTo(navController, Route.WordWizardScreen.route)
                }
            )
        }

        composable(
            route = Route.WordWizardScreen.route
        ){
            val grammarResponse by viewModelStoryScape.grammarState.collectAsState()
            val vocabularyResponse by viewModelStoryScape.vocabularyState.collectAsState()

            if (grammarResponse != null && vocabularyResponse != null){
                WordWizardScreen(
                    grammarResponse = grammarResponse!!,
                    vocabularyResponse = vocabularyResponse!!,
                    title = story.title,
                    subtitle = story.subtitle,
                    imageDrawable = story.imageRes,
                    onEndButton = onEndStory,
                )
            }

        }
    }

}

private fun navigateTo(navController: NavController, route: String) {
    navController.navigate(route) {
        launchSingleTop = true
    }
}