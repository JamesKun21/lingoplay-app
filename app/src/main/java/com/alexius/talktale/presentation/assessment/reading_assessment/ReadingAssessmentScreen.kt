package com.alexius.talktale.presentation.assessment.reading_assessment

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alexius.talktale.R
import com.alexius.talktale.presentation.assessment.components.AssessmentPanelDisplay
import com.alexius.talktale.presentation.assessment.components.AssessmentQuizDisplay
import com.alexius.talktale.presentation.common.OnboardingPanelDisplay
import com.alexius.talktale.presentation.navgraph_main.Route

@Composable
fun ReadingAssessmentScreen(
    modifier: Modifier = Modifier,
   /* viewModel: ReadingAssessmentViewModel = hiltViewModel()*/
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.AssessmentReadingOnboardingScreen.route
    )
    {
        composable(
            route = Route.AssessmentReadingOnboardingScreen.route
        ){
            OnboardingPanelDisplay(
                imageDrawable = R.drawable.boy_touching,
                title = "Temukan Level Bahasa Inggrismu",
                description = "Ikuti tes untuk mengetahui level Bahasa Inggrismu dan mulai perjalanan belajar yang sempurna!",
                onClickMainButton ={
                    navigateTo(navController, Route.AssessmentReadingBridgingScreen.route)
                },
                mainButtonText = "Lanjut",
            )
        }

        composable(
            route = Route.AssessmentReadingBridgingScreen.route
        ){
            AssessmentPanelDisplay(
                title = "Reading",
                underTitle = "Kamu akan memulai bagian membaca.",
                drawableImage = R.drawable.robot_reading,
                onClickMainButton = {},
                mainButtonText = "Lanjut",
                hints = listOf(
                    "Pertanyaan di tes ini dapat semakin sulit atau mudah untuk menyesuaikan levelmu.",
                    "Kamu tidak akan kehilangan poin jika salah menjawab.",
                    "Setelah mengumpulkan jawaban, kamu tidak bisa kembali ke pertanyaan sebelumnya."
                )
            )
        }

        composable(
            route = Route.AssessmentReadingQuestionScreen.route
        ){

            var showExitDialog = rememberSaveable() { mutableStateOf(false) }

            var selectedAnswerIndex = rememberSaveable() { mutableIntStateOf(-1) }

            BackHandler {
                showExitDialog.value = true
            }

            AssessmentQuizDisplay(
                currentQuestionIndex = TODO(),
                totalQuestions = TODO(),
                onExitClick = TODO(),
                contentQuestion = TODO(),
                answers = TODO(),
                onAnswerSelected = TODO(),
                onNextClick = TODO(),
                showExitDialog = TODO(),
                selectedAnswerIndex = TODO()
            )

        }
    }
}

private fun navigateTo(navController: NavController, route: String) {
    navController.navigate(route) {
        launchSingleTop = true
    }
}