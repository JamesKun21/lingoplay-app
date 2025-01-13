package com.alexius.talktale.presentation.report_card

import android.media.MediaPlayer
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alexius.core.util.UIState
import com.alexius.talktale.presentation.common.LoadingScreen
import com.alexius.talktale.presentation.navgraph_main.Route
import com.alexius.talktale.presentation.report_card.components.EndReportCardScreen
import com.alexius.talktale.presentation.report_card.components.ReportCardQuiz
import com.alexius.talktale.presentation.report_card.components.ReportCardWordWizard
import java.io.File

@Composable
fun ReportCardScreen(
    modifier: Modifier = Modifier,
    onEndReportCard: () -> Unit,
    reportCardViewModel: ReportCardViewModel,
    answersToAnalyze: List<String>,
    completedStoriesCount: Int
) {
    val navController= rememberNavController()

    NavHost(
        startDestination = Route.ReportCardQuizScreen.route,
        navController = navController
    ){
        composable(
            route = Route.ReportCardQuizScreen.route
        ){

            var selectedAnswerIndex = rememberSaveable() { mutableIntStateOf(-1) }



            ReportCardQuiz(
                onBackClick = {
                    reportCardViewModel.moveToPreviousQuestion {
                        onEndReportCard()
                    }
                },
                imageRes = reportCardViewModel.questions[reportCardViewModel.currentQuestionIndex.value].imageRes,
                question = reportCardViewModel.questions[reportCardViewModel.currentQuestionIndex.value].questionText,
                answers = reportCardViewModel.questions[reportCardViewModel.currentQuestionIndex.value].options,
                onAnswerSelected = {reportCardViewModel.selectAnswer(it)},
                selectedAnswerIndex = selectedAnswerIndex,
                onNextClick = {
                    if (reportCardViewModel.currentQuestionIndex.value == reportCardViewModel.questions.size - 1) {
                        reportCardViewModel.analyzeAnswers(answersToAnalyze)
                        Log.d("ReportCardScreen", "Answers to analyze: $answersToAnalyze")
                        navigateTo(navController, Route.ReportCardWordWizardScreen.route)
                    } else {
                        reportCardViewModel.moveToNextQuestion()
                        selectedAnswerIndex.intValue = -1
                    }
                }
            )
        }

        composable(
            route = Route.ReportCardWordWizardScreen.route
        ){
            val vocabList by reportCardViewModel.listVocabResponse.collectAsStateWithLifecycle()
            val grammarList by reportCardViewModel.listGrammarResponse.collectAsStateWithLifecycle()
            var isGrammar by remember { mutableStateOf(true) }
            var isLoading by remember { mutableStateOf(false) }
            val context = LocalContext.current
            val geminiState by reportCardViewModel.uiStateGemini.collectAsStateWithLifecycle()

            ReportCardWordWizard(
                isGrammar = isGrammar,
                analysisList = if (isGrammar) grammarList else vocabList,
                onBackClick = {
                    if (!isGrammar) {
                        isGrammar = true
                    } else {
                        onEndReportCard()
                    }
                },
                onNextClick = {
                    if (isGrammar) {
                        isGrammar = false
                    } else {
                        navigateTo(navController, Route.ReportCardEndScreen.route)
                    }
                }
            )

            when (val state = geminiState) {
                is UIState.Loading -> {
                    // Show loading indicator
                    isLoading = true
                }
                is UIState.Success -> {
                    isLoading = false
                }
                is UIState.Error -> {
                    // Show error message
                    if (state.errorMessage != null) {
                        Toast.makeText(context, state.errorMessage, Toast.LENGTH_SHORT).show()
                    }
                    isLoading = false
                }
            }

            LoadingScreen(enableLoading = isLoading)
        }

        composable(
            route = Route.ReportCardEndScreen.route
        ){
            EndReportCardScreen(
                storiesCompleted = completedStoriesCount,
                onEndButtonClick = {
                    onEndReportCard()
                }
            )
        }
    }
}

private fun navigateTo(navController: NavController, route: String) {
    navController.navigate(route) {
        launchSingleTop = true
    }
}