package com.alexius.talktale.presentation.assessment.listening_assessment

import android.media.MediaPlayer
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alexius.core.util.UIState
import com.alexius.talktale.R
import com.alexius.talktale.presentation.assessment.components.AssessmentPanelDisplay
import com.alexius.talktale.presentation.assessment.components.AssessmentQuizDisplay
import com.alexius.talktale.presentation.assessment.reading_assessment.ReadingAssessmentViewModel
import com.alexius.talktale.presentation.assessment.score.ScoreAssessmentScreen
import com.alexius.talktale.presentation.common.LoadingScreen
import com.alexius.talktale.presentation.navgraph_main.Route
import com.alexius.talktale.ui.theme.LightGreen
import com.alexius.talktale.ui.theme.RedError
import com.alexius.talktale.ui.theme.WhitePale
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun ListeningAssessment(
    modifier: Modifier = Modifier,
    viewModelListening: ListeningAssessmentViewModel,
    readViewModel: ReadingAssessmentViewModel,
    onEndScoreAssessment: () -> Unit
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.AssessmentListeningBridgeScreen.route,
    )
    {
        composable(
            route = Route.AssessmentListeningBridgeScreen.route
        ){
            AssessmentPanelDisplay(
                title = "Listening",
                underTitle = "Hebat! Kamu baru saja menyelesaikan bagian membaca. Selanjutnya kamu akan memulai bagian mendengar.",
                drawableImage = R.drawable.guy_listen,
                onClickMainButton = {navigateTo(navController, Route.AssessmentListeningQuestionScreen.route)},
                mainButtonText = "Lanjut",
                hints = listOf(
                    "Pertanyaan di tes ini dapat semakin sulit atau mudah untuk menyesuaikan levelmu.",
                    "Kamu tidak akan kehilangan poin jika salah menjawab.",
                    "Setelah mengumpulkan jawaban, kamu tidak bisa kembali ke pertanyaan sebelumnya."
                )
            )
        }

        composable(
            route = Route.AssessmentListeningQuestionScreen.route
        ){

            var showExitDialog = rememberSaveable() { mutableStateOf(false) }

            var selectedAnswerIndex = rememberSaveable() { mutableIntStateOf(-1) }

            val uiStateAudio by viewModelListening.uiStateAudio.collectAsState()

            var isLoading by remember { mutableStateOf(false) }

            val context = LocalContext.current

            var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }

            DisposableEffect(Unit) {
                onDispose {
                    mediaPlayer?.release()
                }
            }

            BackHandler {
                showExitDialog.value = true
            }

            AssessmentQuizDisplay(
                currentQuestionIndex = viewModelListening.currentQuestionIndex.value,
                totalQuestions = 10,
                onExitClick = {
                    showExitDialog.value = true
                },
                contentQuestion = {
                    Spacer(modifier = modifier.fillMaxWidth().height(44.dp))

                    IconButton(
                        onClick = {
                            isLoading = true
                            viewModelListening.generateSpeech(viewModelListening.questions[viewModelListening.currentQuestionIndex.value].questionText)
                        },
                        modifier = modifier
                            .background(
                                color = LightGreen,
                                shape = CircleShape
                            )
                            .size(95.dp),
                        enabled = !isLoading
                    ) {
                        Icon(
                            imageVector = Icons.Filled.PlayArrow,
                            contentDescription = "Exit",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = modifier.size(65.dp)
                        )
                    }

                    Spacer(modifier = modifier.fillMaxWidth().height(44.dp))

                    Text(
                        text = "Apa tanggapan terbaik untuk Pembicara ${viewModelListening.currentQuestionIndex.value + 1}?",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                    )

                    Spacer(modifier = modifier.fillMaxWidth().height(44.dp))
                },
                answers = viewModelListening.questions[viewModelListening.currentQuestionIndex.value].options,
                onAnswerSelected = {
                    viewModelListening.selectAnswer(it)
                    viewModelListening.checkAnswer()
                },
                onNextClick = {
                    if (viewModelListening.currentQuestionIndex.value == viewModelListening.questions.size - 1) {
                        navigateTo(navController, Route.AssessmentCalculatingScreen.route)
                    } else {
                        viewModelListening.moveToNextQuestion()
                        selectedAnswerIndex.intValue = -1
                    }
                },
                showExitDialog = showExitDialog,
                selectedAnswerIndex = selectedAnswerIndex
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
            route = Route.AssessmentCalculatingScreen.route
        ){
            val coroutineScope = rememberCoroutineScope()
            var activateLoading by rememberSaveable { mutableStateOf(false) }

            val readingViewModel: ReadingAssessmentViewModel = hiltViewModel()

            AssessmentPanelDisplay(
                title = "Hebat!",
                underTitle = "Kamu baru saja menyelesaikan bagian mendengar.",
                drawableImage = R.drawable.girl_waiting,
                onClickMainButton = {
                    coroutineScope.launch {
                        activateLoading = true
                        delay(4000)
                        activateLoading = false
                        navigateTo(navController, Route.AssessmentScoreScreen.route)
                    }
                },
                mainButtonText = "Lanjut",
                hints = listOf(
                    "Tunggu sebentar. Kami sedang menganalisis jawabanmu.",
                    "Skormu akan ditampilkan beserta penjelasan dan level yang kamu capai."
                )
            )
            LoadingScreen(enableLoading = activateLoading)
        }

        composable(
            route = Route.AssessmentScoreScreen.route
        ){

            val readFinalScoreData = readViewModel.finalScoreData.value
            val listenFinalScoreData = viewModelListening.finalScoreData.value
            val averageScore = (readFinalScoreData.totalScore + listenFinalScoreData.totalScore) / 2

            ScoreAssessmentScreen(
                userReadingScore = readFinalScoreData.totalScore,
                userReadingGrade = readFinalScoreData.grade,
                userListeningScore = listenFinalScoreData.totalScore,
                userListeningGrade = listenFinalScoreData.grade,
                averageScore = averageScore,
                onMainButtonClick = onEndScoreAssessment
            )
        }
    }
}

private fun navigateTo(navController: NavController, route: String) {
    navController.navigate(route) {
        launchSingleTop = true
    }
}