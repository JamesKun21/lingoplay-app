package com.alexius.talktale.presentation.storyscape

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexius.core.R
import com.alexius.core.data.remote.speech_ai.TextToSpeechRequest
import com.alexius.core.domain.model.GrammarResponse
import com.alexius.core.domain.model.Question
import com.alexius.core.domain.model.Story
import com.alexius.core.domain.model.StoryParagraph
import com.alexius.core.domain.model.VocabularyResponse
import com.alexius.core.domain.usecases.Assessment.GenerateSound
import com.alexius.core.domain.usecases.talktalenav.GenerateGrammarPrompt
import com.alexius.core.domain.usecases.talktalenav.GenerateVocabPrompt
import com.alexius.core.domain.usecases.talktalenav.GetStories
import com.alexius.core.util.Constants.SPEECHAI_API_KEY
import com.alexius.core.util.UIState
import com.google.ai.client.generativeai.GenerativeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class StoryScapeViewModel @Inject constructor(
    private val generateSound: GenerateSound,
    private val generateVocabPrompt: GenerateVocabPrompt,
    private val generateGrammarPrompt: GenerateGrammarPrompt,
    private val geminiModel: GenerativeModel
) : ViewModel() {

   /* private val _grammarState = MutableStateFlow<GrammarResponse?>(null)
    val grammarState: StateFlow<GrammarResponse?> = _grammarState

    private val _vocabularyState = MutableStateFlow<VocabularyResponse?>(null)
    val vocabularyState: StateFlow<VocabularyResponse?> = _vocabularyState
*/

    private val _story = mutableStateOf(Story(
        title = "",
        subtitle = "",
        imageRes = 0,
        isLocked = false,
        bridgeHint = "",
        closeStatement = "",
        paragraphs = emptyList()
    ))
    val story = _story

    private val _currentPharagraphIndex = mutableIntStateOf(0)
    val currentPharagraphIndex: State<Int> = _currentPharagraphIndex

    private val _uiStateAudio = MutableStateFlow<UIState<ByteArray>>(UIState.Loading)
    val uiStateAudio: StateFlow<UIState<ByteArray>> = _uiStateAudio

    /*init {
        _story.value =   Story(
            title = "Timun Mas",
            subtitle = "The Golden Cucumber Adventure",
            imageRes = R.drawable.timun_mas,
            isLocked = false,
            bridgeHint = "Kamu akan membaca cerita Timun Mas sambil mendengarkan audionya. Jawab pertanyaan dan ikuti instruksi yang diberikan.",
            closeStatement = "Kamu telah menyelesaikan cerita Timun Mas. Selanjutnya kamu akan melihat analisis grammar dan vocabularymu.",
            paragraphs = listOf(

                StoryParagraph(
                    imageRes = R.drawable.timun_mas_1,
                    content = "Once upon a time, in a village, there lived a widow named Mbok Srini. She was alone because her husband had passed away, and she had no children. Mbok Srini always wished for a child so that she wouldn't feel lonely. One day, she had a dream about a giant in the forest. The giant told her to go to the forest and find a package behind a tree. Mbok Srini woke up and thought this dream was a sign. She went to the forest to find the package.",
                    question = Question(
                        multipleChoice = true,
                        options = listOf(
                            "Yes",
                            "No",
                        ),
                        correctAnswerIndex = 1,
                        text = "Did Mbok Srini have children before the dream?",
                        userAnswer = "",
                    )
                ),

                StoryParagraph(
                    imageRes = R.drawable.timun_mas_2,
                    content = "In the forest, Mbok Srini met a green giant. The giant said he would grant her wish to have a child, but she had to give something in return. The giant wanted Mbok Srini to raise the child, and when the child was 6 years old, the giant would take the child and eat her. Mbok Srini agreed to the giant’s terms. The giant gave her some cucumber seeds and told her to plant them and take care of them.",
                    question = Question(
                        multipleChoice = false,
                        options = listOf(
                            ""
                        ),
                        correctAnswerIndex = 0,
                        text = "What did the giant want from Mbok Srini?",
                        userAnswer = "",
                    )
                ),

                StoryParagraph(
                    imageRes = R.drawable.timun_mas_3,
                    content = "Mbok Srini planted the cucumber seeds. She took good care of them, and soon a big cucumber grew. When she opened the cucumber, a beautiful baby girl appeared. Mbok Srini named her Timun Mas, which means \"Golden Cucumber.\" Timun Mas grew up to be a pretty girl, and Mbok Srini took care of her very well. However, Mbok Srini was always afraid that the giant would come and take Timun Mas.",
                    question = Question(
                        multipleChoice = false,
                        options = listOf(
                            ""
                        ),
                        correctAnswerIndex = 0,
                        text = "Can you describe how Timun Mas came to be and what happened after Mbok Srini opened the cucumber?",
                        userAnswer = "",
                    )
                ),

                StoryParagraph(
                    imageRes = R.drawable.timun_mas_4,
                    content = "One day, the giant came to Mbok Srini’s house and asked for Timun Mas. Mbok Srini was scared and told Timun Mas to hide in the kitchen. When the giant asked for the girl, Mbok Srini lied and said that Timun Mas was too skinny and not ready to eat. She asked the giant to wait for two more years. The giant agreed and left.",
                    question = Question(
                        multipleChoice = false,
                        options = listOf(
                            ""
                        ),
                        correctAnswerIndex = 0,
                        text = "What did Mbok Srini say to the giant?",
                        userAnswer = "",
                    )
                ),

                StoryParagraph(
                    imageRes = R.drawable.timun_mas_5,
                    content = "Mbok Srini prayed to God for help. She dreamed about a wise man on a mountain. The wise man told her to send Timun Mas to find him. Timun Mas traveled for many days until she finally found the wise man. The wise man gave her four packages and told her to run and throw the packages at the giant to stop him.",
                    question = Question(
                        multipleChoice = true,
                        options = listOf(
                            "A wise man",
                            "A magician",
                        ),
                        correctAnswerIndex = 1,
                        text = "Who helped Mbok Srini and Timun Mas?",
                        userAnswer = "",
                    )
                ),

                StoryParagraph(
                    imageRes = R.drawable.timun_mas_6,
                    content = "After two years, the giant returned and chased Timun Mas. She threw the first package with cucumber seeds, creating a field that slowed the giant. Then she threw needles, turning them into trees that hurt the giant. Next, she threw salt, creating a sea that drowned him, but he survived. Finally, she threw shrimp paste, making hot mud that sank the giant forever. Timun Mas returned home, and they lived happily ever after.",
                    question = Question(
                        multipleChoice = false,
                        options = listOf(
                            "",
                        ),
                        correctAnswerIndex = 0,
                        text = "How did Timun Mas stop the giant?",
                        userAnswer = "",
                    )
                )
            )
        )
    }*/

    /*fun analyzeText() {
        viewModelScope.launch {
            try {
                val answerList = getAllUserAnswersNotMultipleChoice()

                val answer1 = answerList[0]

                val answer2 = answerList[1]

                // Get grammar analysis
                val grammarPrompt = generateGrammarPrompt(answer1)
                val grammarResponse = geminiModel.generateContent(grammarPrompt)
                    .text?.let { Json.decodeFromString<GrammarResponse>(it) }
                _grammarState.value = grammarResponse

                // Get vocabulary analysis
                val vocabPrompt = generateVocabPrompt(answer2)
                val vocabResponse = geminiModel.generateContent(vocabPrompt)
                    .text?.let { Json.decodeFromString<VocabularyResponse>(it) }
                _vocabularyState.value = vocabResponse
            } catch (e: Exception) {
                // Handle errors
                Log.d("StoryScapeViewModel", "Error: ${e.message}")
            }
        }
    }*/

    fun moveToNextQuestion() {
        if (_currentPharagraphIndex.intValue < _story.value.paragraphs.size - 1) {
            _currentPharagraphIndex.intValue++
        }
    }

    fun answerQuestion(answer: String) {
        val currentParagraphQuestion = _story.value.paragraphs[_currentPharagraphIndex.intValue].question
        currentParagraphQuestion.userAnswer = answer
    }

    private fun getAllUserAnswersNotMultipleChoice(): List<String> {
        val userAnswers = mutableListOf<String>()
        _story.value.paragraphs.forEach { paragraph ->
            if (!paragraph.question.multipleChoice) {
                userAnswers.add(paragraph.question.userAnswer)
            }
        }
        return userAnswers
    }

    fun generateSpeech(text: String) {
        viewModelScope.launch {
            _uiStateAudio.value = UIState.Loading
            try {
                val request = TextToSpeechRequest(
                    text = text,
                    model_id = "eleven_multilingual_v2"
                )

                val response = generateSound(
                    id = "JBFqnCBsd6RMkjVDRZzb",
                    apiKey = SPEECHAI_API_KEY,
                    request = request,
                )

                val audioData = response.bytes()
                _uiStateAudio.value = UIState.Success(audioData)
            } catch (e: Exception) {
                _uiStateAudio.value = UIState.Error(e.message ?: "Unknown error")
                delay(2000)
                _uiStateAudio.value = UIState.Loading
            }
        }
    }
}