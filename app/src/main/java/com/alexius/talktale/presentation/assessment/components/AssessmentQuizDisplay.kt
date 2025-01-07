package com.alexius.talktale.presentation.assessment.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexius.talktale.presentation.common.ExitDialog
import com.alexius.talktale.presentation.navgraph_entry.NavGraphEntry
import com.alexius.talktale.ui.theme.Green
import com.alexius.talktale.ui.theme.TalkTaleTheme

@Composable
fun AssessmentQuizDisplay(
    modifier: Modifier = Modifier,
    currentQuestionIndex: Int,
    totalQuestions: Int,
    onExitClick: () -> Unit,
    contentQuestion: @Composable () -> Unit,
    answers: List<String>,
    onAnswerSelected: (Int) -> Unit,
    onNextClick: () -> Unit,
    showExitDialog: MutableState<Boolean>,
    selectedAnswerIndex: MutableState<Int>
) {
    Scaffold(
        topBar = {
            TopBarProgressIndicator(
                currentQuestion = currentQuestionIndex + 1,
                totalQuestions = totalQuestions,
                onExitClick = {
                    //InitiateExit Dialog
                    showExitDialog.value = true
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(horizontal = 25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            contentQuestion()

            AssessmentOptionAnswer(
                answers = answers,
                onAnswerSelected = onAnswerSelected,
                selectedAnswerIndex = selectedAnswerIndex
            )

            Spacer(modifier = modifier.fillMaxWidth().height(60.dp))

            Button(
                onClick = onNextClick,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
                    .height(44.dp),
                shape = MaterialTheme.shapes.extraLarge,
                enabled = selectedAnswerIndex.value != -1,
                colors = ButtonDefaults.buttonColors(
                    disabledContainerColor = Green.copy(alpha = 0.5f)
                )
            ){
                Text(
                    text = "Lanjut",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold, color = Color.White),
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    ExitDialog(
        onExitClick = onExitClick,
        showExitDialog = showExitDialog ,
        titleNotif = "Kamu yakin ingin keluar?",
        notifDesc = "Jawaban kamu akan hilang dan kamu harus memulai tes dari awal.",
        confirmButtonText = "Yakin",
        cancelButtonText = "Tidak"
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun AssessmentQuizPrev() {
    TalkTaleTheme {

        var showExitDialog = rememberSaveable() { mutableStateOf(false) }

        var selectedAnswerIndex = rememberSaveable() { mutableIntStateOf(-1) }

        AssessmentQuizDisplay(
            currentQuestionIndex = 0,
            totalQuestions = 10,
            onExitClick = {},
            contentQuestion = {
                Text(
                    text = "Pertanyaan",
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp)
                )
            },
            answers = listOf("A", "B", "C", "D"),
            onAnswerSelected = {},
            onNextClick = {},
            showExitDialog = showExitDialog,
            selectedAnswerIndex = selectedAnswerIndex
        )
    }
}