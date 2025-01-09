package com.alexius.talktale.presentation.storyscape.story_quiz

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexius.talktale.R
import com.alexius.talktale.presentation.common.ExitDialog
import com.alexius.talktale.ui.theme.Black
import com.alexius.talktale.ui.theme.Grey
import com.alexius.talktale.ui.theme.RedError

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoryQuizDisplay(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    onExitClick: () -> Unit,
    onAnswerFieldChange: (String) -> Unit,
    answerInput: String,
    question: String,
    paragraph: String,
    imageDrawable: Int,
    onPlayAudioClick: () -> Unit,
    isMultipleChoice: Boolean,
    options: List<String>,
    onAnswerSelected: (Int) -> Unit,
    onNextClick: () -> Unit,
    showExitDialog: MutableState<Boolean>,
    selectedAnswerIndex: MutableState<Int>
) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            // Add window insets padding
            .systemBarsPadding(),
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(end = 50.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "StoryScape",
                            style = MaterialTheme.typography.displaySmall,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        showExitDialog.value = true
                    }) {
                        Icon(imageVector = Icons.Outlined.ArrowBackIosNew, contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    )
    { innerPadding ->
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(horizontal = 37.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                painter = painterResource(id = imageDrawable),
                contentDescription = null,
                modifier = modifier.fillMaxWidth().height(180.dp)
                    .clip(MaterialTheme.shapes.large),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = modifier.height(26.dp))

            Text(
                text = paragraph,
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Justify
            )

            Spacer(modifier = modifier.height(26.dp))

            Text(
                text = question,
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = modifier.height(15.dp))

            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = modifier.height(77.dp))
                IconButton(
                    onClick = onPlayAudioClick,
                    enabled = !isLoading
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.play_audio_orange),
                        contentDescription = "Play Audio",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }

                Spacer(modifier = modifier.height(10.dp))

                Text(
                    text = "Putar ulang audio",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            Spacer(modifier = modifier.height(26.dp))

            if (isMultipleChoice) {
                StoryQuizOptionAnswer(
                    answers = options,
                    onAnswerSelected = onAnswerSelected,
                    selectedAnswerIndex = selectedAnswerIndex
                )
            } else {
                OutlinedTextField(
                    value = answerInput,
                    onValueChange = { input ->
                        onAnswerFieldChange(input)
                    },
                    placeholder = {
                        Text(
                            text = "Jawab pertanyaan dengan mengetik.",
                            style = MaterialTheme.typography.labelSmall.copy(color = Grey)
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ),
                    modifier = Modifier.fillMaxWidth().height(100.dp),
                    shape = MaterialTheme.shapes.medium,
                    textStyle = MaterialTheme.typography.labelSmall,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Black,
                        unfocusedBorderColor = Grey,
                        errorBorderColor = RedError
                    ),
                )
            }

            Spacer(modifier = modifier.height(28.dp))


            Button(
                onClick = onNextClick,
                modifier = modifier
                    .fillMaxWidth()
                    .height(44.dp),
                shape = MaterialTheme.shapes.extraLarge
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
        notifDesc = "Jawaban kamu akan hilang dan kamu harus memulai cerita dari awal.",
        confirmButtonText = "Yakin",
        cancelButtonText = "Tidak"
    )
}