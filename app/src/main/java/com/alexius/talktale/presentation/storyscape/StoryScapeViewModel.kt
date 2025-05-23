package com.alexius.talktale.presentation.storyscape

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexius.core.R
import com.alexius.core.data.remote.speech_ai.TextToSpeechRequest
import com.alexius.core.domain.model.CompletedStories
import com.alexius.core.domain.model.GrammarResponse
import com.alexius.core.domain.model.Question
import com.alexius.core.domain.model.Story
import com.alexius.core.domain.model.StoryParagraph
import com.alexius.core.domain.model.VocabularyResponse
import com.alexius.core.domain.usecases.Assessment.GenerateSound
import com.alexius.core.domain.usecases.talktalenav.GenerateGrammarPrompt
import com.alexius.core.domain.usecases.talktalenav.GenerateVocabPrompt
import com.alexius.core.domain.usecases.talktalenav.GetCompletedStories
import com.alexius.core.domain.usecases.talktalenav.GetStories
import com.alexius.core.domain.usecases.talktalenav.UpdateCompletedStories
import com.alexius.core.util.Constants.SPEECHAI_API_KEY
import com.alexius.core.util.UIState
import com.google.ai.client.generativeai.GenerativeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject


@HiltViewModel
class StoryScapeViewModel @Inject constructor(
    private val generateSound: GenerateSound,
    private val generateVocabPrompt: GenerateVocabPrompt,
    private val generateGrammarPrompt: GenerateGrammarPrompt,
    private val geminiModel: GenerativeModel,
    private val getCompletedStories: GetCompletedStories,
    private val updateCompletedStories: UpdateCompletedStories
) : ViewModel() {

    private val _beginnerCompletedStories = mutableListOf<Boolean>()
    private val _intermediateCompletedStories = mutableListOf<Boolean>()
    private val _advancedCompletedStories = mutableListOf<Boolean>()
    private val _advancedCareerCompletedStories = mutableListOf<Boolean>()

    val beginnerCompletedStories: List<Boolean> = _beginnerCompletedStories
    val intermediateCompletedStories: List<Boolean> = _intermediateCompletedStories
    val advancedCompletedStories: List<Boolean> = _advancedCompletedStories
    val advancedCareerCompletedStories: List<Boolean> = _advancedCareerCompletedStories

    var _totalCompletedStories = mutableIntStateOf(0)
    val totalCompletedStories: State<Int> = _totalCompletedStories

    private val _grammarState = MutableStateFlow<GrammarResponse?>(null)
    val grammarState: StateFlow<GrammarResponse?> = _grammarState

    private val _vocabularyState = MutableStateFlow<VocabularyResponse?>(null)
    val vocabularyState: StateFlow<VocabularyResponse?> = _vocabularyState

    private val _story = mutableStateOf(Story(
        title = "",
        subtitle = "",
        imageRes = 0,
        isComplete = false,
        bridgeHint = "",
        closeStatement = "",
        paragraphs = emptyList()
    ))
    val story: State<Story> = _story

    private val _beginnerStories = MutableStateFlow<List<Story>>(emptyList())
    val beginnerStories: StateFlow<List<Story>> = _beginnerStories

    private val _intermediateStories = MutableStateFlow<List<Story>>(emptyList())
    val intermediateStories: StateFlow<List<Story>> = _intermediateStories

    private val _advancedStories = MutableStateFlow<List<Story>>(emptyList())
    val advancedStories: StateFlow<List<Story>> = _advancedStories

    private val _advancedCareerStories = MutableStateFlow<List<Story>>(emptyList())
    val advancedCareerStories: StateFlow<List<Story>> = _advancedCareerStories

    private val _listOfAnswers = mutableListOf<String>()
    val listOfAnswers: List<String> = _listOfAnswers

    private val _currentPharagraphIndex = mutableIntStateOf(0)
    val currentPharagraphIndex: State<Int> = _currentPharagraphIndex

    private val _uiStateAudio = MutableStateFlow<UIState<ByteArray>>(UIState.Loading)
    val uiStateAudio: StateFlow<UIState<ByteArray>> = _uiStateAudio

    private val _uiStateGemini = MutableStateFlow<UIState<String>>(UIState.Loading)
    val uiStateGemini: StateFlow<UIState<String>> = _uiStateGemini

    private val _currentStoryIndex = mutableIntStateOf(0)
    val currentStoryIndex: State<Int> = _currentStoryIndex

    init {

        getListCompletedStories()

        val listBeginnerStories = listOf<Story>(
            //region Timun Mas
            Story(
                title = "Timun Mas",
                subtitle = "The Golden Cucumber Adventure",
                imageRes = R.drawable.timun_mas,
                isComplete = false,
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
            ),
            //endregion

            //region The Lion and The Mouse
            Story(
                title = "The Lion and the Mouse",
                subtitle = "The Unexpected Friendship Adventure",
                imageRes = R.drawable.lion,
                isComplete = false,
                bridgeHint = "Kamu akan membaca cerita The Lion and the Mouse sambil mendengarkan audionya. Jawab pertanyaan dan ikuti instruksi yang diberikan.",
                closeStatement = "Kamu telah menyelesaikan cerita The Lion and the Mouse. Selanjutnya kamu akan melihat analisis grammar dan vocabularymu.",
                paragraphs = listOf(

                    StoryParagraph(
                        imageRes = R.drawable.lion_1,
                        content = "One day, in a large forest, there lived a strong and brave lion who was the king of the jungle. The lion ruled over all the animals and was feared by many. After finishing his lunch one sunny afternoon, the lion felt tired and decided to take a nap. He lay down under a big tree and soon fell into a deep, peaceful sleep. The sun was shining, and the forest was quiet, making it the perfect time for the lion to rest.",
                        question = Question(
                            multipleChoice = true,
                            options = listOf(
                                "He went for a walk.",
                                "He fell asleep.",
                            ),
                            correctAnswerIndex = 1,
                            text = "What did the lion do after finishing his lunch?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.lion_2,
                        content = "As the lion was sleeping, a little mouse was walking through the forest, happily singing a song. The mouse was so caught up in its music that it didn't pay attention to where it was going. While walking, the mouse stepped on a slippery banana peel and suddenly slipped and fell. It tumbled all the way down and landed right on the lion’s face! The mouse was startled, but the lion was even more surprised. The mouse quickly jumped off and tried to run away, but it was too late.",
                        question = Question(
                            multipleChoice = false,
                            options = listOf(
                                ""
                            ),
                            correctAnswerIndex = 0,
                            text = "What made the mouse fall?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.lion_3,
                        content = "As the mouse fell, its tail accidentally went into the lion’s nose. The lion woke up with a loud roar, very angry and startled by what had just happened. He quickly grabbed the mouse with his big paw. The mouse was terrified and began to beg the lion, \"Please don’t eat me! I’m too small, and I promise to help you someday if you let me go!\" The lion was surprised to hear the mouse speak and thought about what the mouse had said. After a while, he decided to let the mouse go free.",
                        question = Question(
                            multipleChoice = false,
                            options = listOf(
                                ""
                            ),
                            correctAnswerIndex = 0,
                            text = "What did the lion do when he woke up?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.lion_4,
                        content = "The mouse, still shaking with fear, looked up at the lion and said, \"I know I am small, but I can help you if you ever need it. Please, let me go this time, and I promise I will repay you.\" The lion thought for a moment. He was the king of the jungle, and what could a tiny mouse do for such a mighty creature? But the lion was feeling generous, so he decided to give the mouse a chance. \"Alright,\" said the lion, \"I will let you go, but remember your promise.\" The mouse thanked the lion and quickly ran away into the forest.",
                        question = Question(
                            multipleChoice = false,
                            options = listOf(
                                ""
                            ),
                            correctAnswerIndex = 0,
                            text = "What did the mouse promise to do for the lion?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.lion_5,
                        content = "A few days later, the lion was walking through the forest, enjoying the beautiful weather. As he walked, he didn’t notice a large trap hidden in the grass. The lion stepped right into the trap, and before he knew it, his foot was caught in a large net. He tried to pull his foot free, but the net was too strong. The lion roared loudly in fear and anger, but no one was around to help. The lion was stuck, and he couldn’t get out of the trap. He began to feel helpless, something he had never felt before.",
                        question = Question(
                            multipleChoice = true,
                            options = listOf(
                                "He found food.",
                                "He got caught in a trap.",
                            ),
                            correctAnswerIndex = 1,
                            text = "What happened to the lion in the forest?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.lion_6,
                        content = "The mouse heard the lion's roar and quickly ran to find him. Seeing the lion trapped in a net, the mouse used its sharp teeth to chew through the ropes. Soon, the lion was free. Grateful, the lion said, \"Thank you, little mouse. You saved my life.\" From then on, they became best friends.",
                        question = Question(
                            multipleChoice = false,
                            options = listOf(
                                "",
                            ),
                            correctAnswerIndex = 0,
                            text = "How did the mouse help the lion?",
                            userAnswer = "",
                        )
                    )
                )
            ),
            //endregion

            //region Red Riding Hood
            Story(
                title = "The Girl in the Red Hood",
                subtitle = "The Little Red Riding Hood Adventure",
                imageRes = R.drawable.red_hood,
                isComplete = false,
                bridgeHint = "Kamu akan membaca cerita The Little Red Riding Hood Adventure sambil mendengarkan audionya. Jawab pertanyaan dan ikuti instruksi yang diberikan.",
                closeStatement = "Kamu telah menyelesaikan cerita The Little Red Riding Hood Adventure. Selanjutnya kamu akan melihat analisis grammar dan vocabularymu.",
                paragraphs = listOf(

                    StoryParagraph(
                        imageRes = R.drawable.redhood_1,
                        content = "Once upon a time, there was a little girl who always wore a red cloak when she went outside. She lived with her mother in a village near a forest. One day, her mother asked her to visit her grandmother, who was sick. The little girl was excited to help her grandmother, so she set off on her journey, carrying a cake made by her mother to give to her grandmother. It was a beautiful day, and the sun was shining as she walked through the forest.",
                        question = Question(
                            multipleChoice = true,
                            options = listOf(
                                "In a big city.",
                                "Near a forest.",
                            ),
                            correctAnswerIndex = 1,
                            text = "Where did the little girl live?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.redhood_2,
                        content = "Before leaving, her mother warned her to be careful and not talk to strangers. The little girl nodded and promised to be safe. She walked happily through the forest, enjoying the birds singing and the flowers blooming. She wasn’t afraid of the forest at all. However, as she walked deeper into the woods, she didn’t realize that a big wolf was watching her from behind the trees. The wolf had been waiting for the right moment to approach her.",
                        question = Question(
                            multipleChoice = false,
                            options = listOf(
                                ""
                            ),
                            correctAnswerIndex = 0,
                            text = "What did the little girl promise her mother?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.redhood_3,
                        content = "Suddenly, the big wolf appeared from behind a tree and approached the little girl. He asked her what she was doing alone in the forest. The little girl, forgetting her mother’s warning, told the wolf that she was going to visit her grandmother, who was sick. The wolf smiled slyly and let her go, but he was thinking of a bad plan. He decided to get to the grandmother’s house before the little girl and do something bad.",
                        question = Question(
                            multipleChoice = false,
                            options = listOf(
                                ""
                            ),
                            correctAnswerIndex = 0,
                            text = "What did the wolf ask the little girl?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.redhood_4,
                        content = "The wolf quickly ran to the grandmother’s house. When he arrived, he knocked on the door. The grandmother, thinking it was her granddaughter, opened the door. But as soon as the door opened, the wolf jumped in and attacked her. The grandmother didn’t have time to protect herself, and she fell unconscious. The wolf then hid her in the closet and disguised himself as the grandmother. He wore her clothes and jewelry and even changed his voice to sound like her.",
                        question = Question(
                            multipleChoice = false,
                            options = listOf(
                                ""
                            ),
                            correctAnswerIndex = 0,
                            text = "What did the wolf do to the grandmother?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.redhood_5,
                        content = "The little girl arrived at her grandmother's house and knocked on the door. The wolf, pretending to sleep, stayed silent. When she entered, the little girl noticed something strange about the \"grandmother.\" She looked much bigger, with large ears, eyes, hands, and mouth. The little girl quickly realized it was the wolf in disguise.",
                        question = Question(
                            multipleChoice = true,
                            options = listOf(
                                "She looked smaller.",
                                "She looked much bigger and different.",
                            ),
                            correctAnswerIndex = 1,
                            text = "What did the little girl notice about her grandmother?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.redhood_6,
                        content = "The little girl ran out of the house, shouting for help. A hunter heard her cries and rushed to help. After she explained everything, the hunter chased the wolf, who ran away in fear. The little girl then found her grandmother hidden in the closet, and they were happily reunited.",
                        question = Question(
                            multipleChoice = false,
                            options = listOf(
                                "",
                            ),
                            correctAnswerIndex = 0,
                            text = "What did the hunter do when he heard the little girl’s cries?",
                            userAnswer = "",
                        )
                    )
                )
            ),
            //endregion
        )
        _beginnerStories.value = listBeginnerStories

        val listIntermediateStories = listOf<Story>(
            //region Timun Mas
            Story(
                title = "Timun Mas",
                subtitle = "The Golden Cucumber Adventure",
                imageRes = R.drawable.timun_mas,
                isComplete = false,
                bridgeHint = "Kamu akan membaca cerita Timun Mas sambil mendengarkan audionya. Jawab pertanyaan dan ikuti instruksi yang diberikan.",
                closeStatement = "Kamu telah menyelesaikan cerita Timun Mas. Selanjutnya kamu akan melihat analisis grammar dan vocabularymu.",
                paragraphs = listOf(

                    StoryParagraph(
                        imageRes = R.drawable.timun_mas_1,
                        content = "Once upon a time, in a small village, there lived a widow named Mbok Srini. She lived alone because her husband had passed away, and she had no children. Mbok Srini often felt lonely and wished for a child. One day, she had a dream about a giant in the forest. The giant told her to go into the forest and look for a package hidden behind a tree. When Mbok Srini woke up, she thought the dream was a sign and decided to follow it. She went to the forest to search for the package.",
                        question = Question(
                            multipleChoice = true,
                            options = listOf(
                                "Yes",
                                "No",
                            ),
                            correctAnswerIndex = 1,
                            text = "Did Mbok Srini have any children before she had the dream?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.timun_mas_2,
                        content = "In the forest, Mbok Srini met a huge green giant. The giant told her he would grant her wish for a child, but she would need to make a sacrifice. He asked her to raise the child, and when the child turned six, the giant would take the child away and eat her. Mbok Srini reluctantly agreed to the giant's condition. The giant gave her some cucumber seeds and told her to plant them and take care of them carefully.",
                        question = Question(
                            multipleChoice = false,
                            options = listOf(
                                ""
                            ),
                            correctAnswerIndex = 0,
                            text = "What did the giant demand from Mbok Srini?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.timun_mas_3,
                        content = "Timun Mas appeared when Mbok Srini planted the cucumber seeds given by the giant. She looked after the plants carefully, and one day, a big cucumber grew. When she opened it, a beautiful baby girl was inside. Mbok Srini named her Timun Mas, which means \"Golden Cucumber.\" As Timun Mas grew up, she became a kind and lovely girl, and Mbok Srini took care of her with great love. However, Mbok Srini was always worried the giant would come back to take Timun Mas, as he had promised.",
                        question = Question(
                            multipleChoice = false,
                            options = listOf(
                                ""
                            ),
                            correctAnswerIndex = 0,
                            text = "How did Timun Mas come to be, and what happened after Mbok Srini opened the cucumber?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.timun_mas_4,
                        content = "One day, the giant came to Mbok Srini’s house to take Timun Mas. Mbok Srini felt scared and quickly told Timun Mas to hide in the kitchen. When the giant asked for Timun Mas, Mbok Srini lied and said that Timun Mas was too skinny and not ready to be eaten. She asked the giant to wait for two more years, and the giant agreed and left.",
                        question = Question(
                            multipleChoice = false,
                            options = listOf(
                                ""
                            ),
                            correctAnswerIndex = 0,
                            text = "What did Mbok Srini tell the giant?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.timun_mas_5,
                        content = "Mbok Srini prayed to God for help and had a dream about a wise man who lived on a mountain. In the dream, the wise man told her to send Timun Mas to find him. Timun Mas walked for many days until she reached the wise man. He gave her four packages and told her to use them by throwing them at the giant to stop him.",
                        question = Question(
                            multipleChoice = true,
                            options = listOf(
                                "A wise man",
                                "A magician",
                            ),
                            correctAnswerIndex = 1,
                            text = "Who gave help to Mbok Srini and Timun Mas?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.timun_mas_6,
                        content = "Two years later, the giant came back and started chasing Timun Mas. She threw the first package, which had cucumber seeds. The seeds grew into a big field that slowed the giant down. Then, she threw a package with needles, and they turned into sharp trees that hurt the giant. After that, she threw salt, which created a sea that nearly drowned the giant, but he managed to survive. Finally, she threw shrimp paste, which changed into hot mud and made the giant sink forever. Timun Mas went back home, and she and Mbok Srini lived happily ever after.",
                        question = Question(
                            multipleChoice = false,
                            options = listOf(
                                "",
                            ),
                            correctAnswerIndex = 0,
                            text = "How did Timun Mas defeat the giant?",
                            userAnswer = "",
                        )
                    )
                )
            ),
            //endregion

            //region The Lion and The Mouse
            Story(
                title = "The Lion and the Mouse",
                subtitle = "The Unexpected Friendship Adventure",
                imageRes = R.drawable.lion,
                isComplete = false,
                bridgeHint = "Kamu akan membaca cerita The Lion and the Mouse sambil mendengarkan audionya. Jawab pertanyaan dan ikuti instruksi yang diberikan.",
                closeStatement = "Kamu telah menyelesaikan cerita The Lion and the Mouse. Selanjutnya kamu akan melihat analisis grammar dan vocabularymu.",
                paragraphs = listOf(

                    StoryParagraph(
                        imageRes = R.drawable.lion_1,
                        content = "One day, in a big forest, there was a strong and brave lion who was the king of the jungle. All the animals respected the lion, and many were afraid of him. After eating his lunch on a warm afternoon, the lion felt sleepy and decided to take a rest. He lay under a big tree and soon fell into a deep sleep. The sun was bright, and the forest was quiet, making it a perfect time for the lion to relax.",
                        question = Question(
                            multipleChoice = true,
                            options = listOf(
                                "He went for a walk.",
                                "He fell asleep.",
                            ),
                            correctAnswerIndex = 1,
                            text = "What did the lion do after he finished his lunch?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.lion_2,
                        content = "While the lion was sleeping, a little mouse was walking through the forest, happily singing a song. The mouse was so focused on its singing that it didn’t notice where it was going. As it walked, the mouse stepped on a slippery banana peel and lost its balance. It fell down and landed right on the lion’s face! The mouse was very surprised, but the lion was even more shocked. The mouse quickly jumped off and tried to run away, but it was too late.",
                        question = Question(
                            multipleChoice = false,
                            options = listOf(
                                ""
                            ),
                            correctAnswerIndex = 0,
                            text = "What caused the mouse to fall?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.lion_3,
                        content = "As the mouse fell, its tail accidentally touched the lion’s nose. The lion woke up with a loud roar, feeling very angry and shocked by the sudden noise. He grabbed the mouse with his big paw. The mouse was scared and quickly begged, \"Please don’t eat me! I’m too small, and I promise to help you one day if you let me go!\" The lion was surprised to hear the mouse talk and stopped to think about what it had said. After a moment, he decided to let the mouse go.",
                        question = Question(
                            multipleChoice = false,
                            options = listOf(
                                ""
                            ),
                            correctAnswerIndex = 0,
                            text = "What did the lion do when he woke up?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.lion_4,
                        content = "The mouse, still trembling with fear, looked up at the lion and said, \"I may be small, but I can help you if you ever need it. Please let me go this time, and I promise to help you in the future.\" The lion thought for a moment. He was the king of the jungle, and what could such a small mouse do for a powerful creature like him? But the lion felt kind and decided to give the mouse a chance. \"Okay,\" said the lion, \"I will let you go, but remember your promise.\" The mouse thanked the lion and quickly ran off into the forest.",
                        question = Question(
                            multipleChoice = false,
                            options = listOf(
                                ""
                            ),
                            correctAnswerIndex = 0,
                            text = "What did the mouse promise to do for the lion?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.lion_5,
                        content = "A few days later, the lion was walking through the forest, enjoying the nice weather. While walking, he didn’t see a big trap hidden in the grass. The lion stepped into it, and before he realized it, his foot was caught in a strong net. He tried to pull his foot out, but the net was too tight. The lion roared loudly in fear and anger, but no one was around to help. The lion was stuck and couldn’t get free. He started to feel helpless, something he had never felt before.",
                        question = Question(
                            multipleChoice = true,
                            options = listOf(
                                "He found food.",
                                "He got caught in a trap.",
                            ),
                            correctAnswerIndex = 1,
                            text = "What happened to the lion in the forest?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.lion_6,
                        content = "The mouse heard the lion’s roar and quickly ran to find him. When the mouse saw the lion trapped in the net, it used its sharp teeth to bite through the ropes. Soon, the lion was free. Thankful, the lion said, \"Thank you, little mouse. You saved my life.\" After that, they became best friends.",
                        question = Question(
                            multipleChoice = false,
                            options = listOf(
                                "",
                            ),
                            correctAnswerIndex = 0,
                            text = "How did the mouse help the lion?",
                            userAnswer = "",
                        )
                    )
                )
            ),
            //endregion

            //region Red Riding Hood
            Story(
                title = "The Girl in the Red Hood",
                subtitle = "The Little Red Riding Hood Adventure",
                imageRes = R.drawable.red_hood,
                isComplete = false,
                bridgeHint = "Kamu akan membaca cerita The Little Red Riding Hood Adventure sambil mendengarkan audionya. Jawab pertanyaan dan ikuti instruksi yang diberikan.",
                closeStatement = "Kamu telah menyelesaikan cerita The Little Red Riding Hood Adventure. Selanjutnya kamu akan melihat analisis grammar dan vocabularymu.",
                paragraphs = listOf(

                    StoryParagraph(
                        imageRes = R.drawable.redhood_1,
                        content = "Once upon a time, there was a young girl who always wore a red cloak whenever she went outside. She lived with her mother in a small village near a forest. One day, her mother asked her to visit her grandmother, who was feeling unwell. The girl was excited to help her grandmother, so she set off on her journey, carrying a cake made by her mother to give to her grandmother. It was a lovely day, and the sun was shining as she walked through the forest.",
                        question = Question(
                            multipleChoice = true,
                            options = listOf(
                                "In a big city.",
                                "Near a forest.",
                            ),
                            correctAnswerIndex = 1,
                            text = "Where did the little girl live?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.redhood_2,
                        content = "Before she left, her mother warned her to be cautious and not talk to strangers. The little girl nodded and promised to be careful. She walked cheerfully through the forest, enjoying the sounds of birds singing and the sight of blooming flowers. She wasn’t afraid of the forest at all. However, as she walked deeper into the woods, she didn’t notice that a large wolf was watching her from behind the trees. The wolf had been waiting for the right moment to approach her.",
                        question = Question(
                            multipleChoice = false,
                            options = listOf(
                                ""
                            ),
                            correctAnswerIndex = 0,
                            text = "What did the little girl promise her mother?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.redhood_3,
                        content = "Suddenly, the wolf appeared from behind a tree and walked toward the little girl. He asked her what she was doing alone in the forest. The little girl, forgetting her mother’s warning, told the wolf that she was going to visit her sick grandmother. The wolf smiled slyly and let her go, but he was already planning something bad. He decided to reach the grandmother’s house before the little girl and do something terrible.",
                        question = Question(
                            multipleChoice = false,
                            options = listOf(
                                ""
                            ),
                            correctAnswerIndex = 0,
                            text = "What did the wolf ask the little girl?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.redhood_4,
                        content = "The wolf quickly ran to the grandmother’s house. When he arrived, he knocked on the door. The grandmother, thinking it was her granddaughter, opened the door. But as soon as the door opened, the wolf jumped inside and attacked her. The grandmother didn’t have time to defend herself and fell unconscious. The wolf then hid her in the closet and disguised himself as the grandmother. He wore her clothes and jewelry and even changed his voice to sound like hers.",
                        question = Question(
                            multipleChoice = false,
                            options = listOf(
                                ""
                            ),
                            correctAnswerIndex = 0,
                            text = "What did the wolf do to the grandmother?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.redhood_5,
                        content = "The little girl arrived at her grandmother's house and knocked on the door. The wolf, pretending to sleep, remained quiet. When she entered, the little girl noticed something odd about the \"grandmother.\" She seemed much bigger, with large ears, eyes, hands, and mouth. The little girl quickly realized it was the wolf in disguise.",
                        question = Question(
                            multipleChoice = true,
                            options = listOf(
                                "She looked smaller.",
                                "She looked strange.",
                            ),
                            correctAnswerIndex = 1,
                            text = "What did the little girl notice about her grandmother?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.redhood_6,
                        content = "The little girl ran out of the house, screaming for help. A hunter heard her cries and rushed to assist. After she explained everything, the hunter chased the wolf, who ran away in fear. The little girl then found her grandmother hidden in the closet, and they were joyfully reunited.",
                        question = Question(
                            multipleChoice = false,
                            options = listOf(
                                "",
                            ),
                            correctAnswerIndex = 0,
                            text = "What did the hunter do when he heard the little girl’s cries?",
                            userAnswer = "",
                        )
                    )
                )
            ),
            //endregion
        )
        _intermediateStories.value = listIntermediateStories

        val listAdvancedStories = listOf<Story>(
            //region Timun Mas
            Story(
                title = "Timun Mas",
                subtitle = "The Golden Cucumber Adventure",
                imageRes = R.drawable.timun_mas,
                isComplete = false,
                bridgeHint = "Kamu akan membaca cerita Timun Mas sambil mendengarkan audionya. Jawab pertanyaan dan ikuti instruksi yang diberikan.",
                closeStatement = "Kamu telah menyelesaikan cerita Timun Mas. Selanjutnya kamu akan melihat analisis grammar dan vocabularymu.",
                paragraphs = listOf(

                    StoryParagraph(
                        imageRes = R.drawable.timun_mas_1,
                        content = "In a quiet village, there lived a widow named Mbok Srini who felt lonely after the death of her husband and having no children. One night, she dreamed of a giant in a forest who told her to find a hidden package behind a tree. Believing it was a sign, she decided to follow the dream and went into the forest to search for the package.",
                        question = Question(
                            multipleChoice = true,
                            options = listOf(
                                "Yes",
                                "No",
                            ),
                            correctAnswerIndex = 1,
                            text = "Did Mbok Srini have any children before she experienced the dream?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.timun_mas_2,
                        content = "In the forest, Mbok Srini met a giant who promised to grant her wish for a child if she agreed to a heavy sacrifice. The giant asked her to raise the child, but when the child turned six, the giant would take her away and eat her. Despite the harsh condition, Mbok Srini agreed, and the giant gave her cucumber seeds to plant and care for.",
                        question = Question(
                            multipleChoice = false,
                            options = listOf(
                                ""
                            ),
                            correctAnswerIndex = 0,
                            text = "What condition did the giant impose on Mbok Srini in exchange for granting her wish?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.timun_mas_3,
                        content = "As Mbok Srini carefully nurtured the cucumber plants, one grew large and, upon opening it, revealed a beautiful baby girl. Overjoyed, Mbok Srini named her Timun Mas, or \"Golden Cucumber.\" Timun Mas grew into a kind and loving girl, but Mbok Srini lived in constant fear that the giant would return to claim her as promised.",
                        question = Question(
                            multipleChoice = false,
                            options = listOf(
                                ""
                            ),
                            correctAnswerIndex = 0,
                            text = "How did Timun Mas come into existence, and what transpired after Mbok Srini opened the cucumber?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.timun_mas_4,
                        content = "One fateful day, the giant arrived at Mbok Srini’s home, demanding Timun Mas. In a panic, Mbok Srini quickly instructed Timun Mas to hide in the kitchen. When the giant inquired about the child’s whereabouts, Mbok Srini deceived him, claiming that Timun Mas was far too thin and underdeveloped to be eaten. She implored the giant to grant her two more years, and after some consideration, the giant reluctantly agreed and left.",
                        question = Question(
                            multipleChoice = false,
                            options = listOf(
                                ""
                            ),
                            correctAnswerIndex = 0,
                            text = "What explanation did Mbok Srini offer to the giant when he arrived at her house?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.timun_mas_5,
                        content = "Desperate for help, Mbok Srini prayed fervently to God and received a dream in which a wise old man, living on a distant mountain, appeared. The man instructed her to send Timun Mas to seek him out. Timun Mas embarked on a long and arduous journey, traveling for many days before finally arriving at the wise man's dwelling. The wise man provided her with four mysterious packages, each with instructions on how to use them against the giant. He cautioned her to throw the packages at the giant to thwart his pursuit.",
                        question = Question(
                            multipleChoice = true,
                            options = listOf(
                                "A wise man",
                                "A magician",
                            ),
                            correctAnswerIndex = 1,
                            text = "Who provided assistance to Mbok Srini and Timun Mas during their time of need?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.timun_mas_6,
                        content = "Two years later, the giant returned to capture Timun Mas. As he chased her, she threw four packages to stop him: cucumber seeds that created a field, needles that formed sharp trees, salt that turned into a sea, and shrimp paste that became boiling mud. The giant was defeated and sank into the earth. Timun Mas returned home, and she and Mbok Srini lived happily ever after.",
                        question = Question(
                            multipleChoice = false,
                            options = listOf(
                                "",
                            ),
                            correctAnswerIndex = 0,
                            text = "How did Timun Mas manage to defeat the giant and protect herself from his capture?",
                            userAnswer = "",
                        )
                    )
                )
            ),
            //endregion

            //region The Lion and The Mouse
            Story(
                title = "The Lion and the Mouse",
                subtitle = "The Unexpected Friendship Adventure",
                imageRes = R.drawable.lion,
                isComplete = false,
                bridgeHint = "Kamu akan membaca cerita The Lion and the Mouse sambil mendengarkan audionya. Jawab pertanyaan dan ikuti instruksi yang diberikan.",
                closeStatement = "Kamu telah menyelesaikan cerita The Lion and the Mouse. Selanjutnya kamu akan melihat analisis grammar dan vocabularymu.",
                paragraphs = listOf(

                    StoryParagraph(
                        imageRes = R.drawable.lion_1,
                        content = "In a vast forest, there lived a mighty lion, revered as the king of the jungle. All the animals feared and respected him. One afternoon, after enjoying his meal, the lion grew drowsy and sought refuge beneath a large tree, soon falling into a deep slumber. The forest was peaceful, and the sunlight was gentle, creating the perfect atmosphere for rest.",
                        question = Question(
                            multipleChoice = true,
                            options = listOf(
                                "He went for a walk.",
                                "He fell asleep.",
                            ),
                            correctAnswerIndex = 1,
                            text = "What action did the lion take after completing his meal?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.lion_2,
                        content = "While the lion slept, a mouse, absorbed in its song, failed to notice a banana peel. It slipped, tumbling onto the lion's face. Startled, the mouse quickly leapt off and attempted to flee, but it was too late.",
                        question = Question(
                            multipleChoice = false,
                            options = listOf(
                                ""
                            ),
                            correctAnswerIndex = 0,
                            text = "What event led to the mouse losing its balance and falling?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.lion_3,
                        content = "As the mouse fell, its tail brushed the lion's nose, waking him with an angry roar. Grabbing the mouse, he was startled when it begged for mercy, promising to repay him one day. Surprised by the mouse's plea, the lion paused and ultimately chose to spare it.",
                        question = Question(
                            multipleChoice = false,
                            options = listOf(
                                ""
                            ),
                            correctAnswerIndex = 0,
                            text = "How did the lion react upon awakening from his slumber?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.lion_4,
                        content = "Trembling with fear, the mouse gazed up at the lion and offered to help him in the future if spared. Despite his power, the lion, moved by kindness, agreed to release the mouse, reminding him to keep his promise. Grateful, the mouse hurried into the forest.",
                        question = Question(
                            multipleChoice = false,
                            options = listOf(
                                ""
                            ),
                            correctAnswerIndex = 0,
                            text = "What vow did the mouse make to the lion in exchange for its freedom?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.lion_5,
                        content = "A few days later, the lion unknowingly stepped into a hidden trap, his foot caught in a tight net. Despite his struggle, he was trapped. Hearing his roar, the mouse swiftly arrived and, with its sharp teeth, chewed through the ropes, freeing the lion. Grateful, the lion thanked the mouse, and they became steadfast companions from that day onward.",
                        question = Question(
                            multipleChoice = true,
                            options = listOf(
                                "He found food.",
                                "He got caught in a trap.",
                            ),
                            correctAnswerIndex = 1,
                            text = "What happened to the lion in the forest?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.lion_6,
                        content = "Upon hearing the lion's anguished roar, the mouse swiftly made its way to him. Upon discovering the lion ensnared in the net, it employed its sharp teeth to sever the ropes. In no time, the lion was liberated. Grateful, the lion expressed, \"I owe you my life, little mouse.\" From that moment onward, they forged an unbreakable bond of friendship.",
                        question = Question(
                            multipleChoice = false,
                            options = listOf(
                                "",
                            ),
                            correctAnswerIndex = 0,
                            text = "How did the mouse assist the lion?",
                            userAnswer = "",
                        )
                    )
                )
            ),
            //endregion

            //region Red Riding Hood
            Story(
                title = "The Girl in the Red Hood",
                subtitle = "The Little Red Riding Hood Adventure",
                imageRes = R.drawable.red_hood,
                isComplete = false,
                bridgeHint = "Kamu akan membaca cerita The Little Red Riding Hood Adventure sambil mendengarkan audionya. Jawab pertanyaan dan ikuti instruksi yang diberikan.",
                closeStatement = "Kamu telah menyelesaikan cerita The Little Red Riding Hood Adventure. Selanjutnya kamu akan melihat analisis grammar dan vocabularymu.",
                paragraphs = listOf(

                    StoryParagraph(
                        imageRes = R.drawable.redhood_1,
                        content = "Once, there lived a young girl who consistently wore a vibrant red cloak when venturing outside. She resided with her mother in a charming village on the edge of a vast forest. One day, her mother asked her to visit her sick grandmother, so the girl set off, carrying a homemade cake as a gesture of kindness. With the sun shining brightly, the day seemed perfect as she walked through the forest.",
                        question = Question(
                            multipleChoice = true,
                            options = listOf(
                                "In a big city.",
                                "Near a forest.",
                            ),
                            correctAnswerIndex = 1,
                            text = "Where was the little girl’s residence?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.redhood_2,
                        content = "Before setting off, her mother admonished her to remain vigilant and avoid speaking to strangers. The little girl agreed, assuring her mother of her caution. As she wandered through the forest, she enjoyed the melodious birdsong and the colorful blooms, feeling no fear. Unbeknownst to her, a massive wolf silently watched from the shadows, waiting for the perfect moment to strike.",
                        question = Question(
                            multipleChoice = false,
                            options = listOf(
                                ""
                            ),
                            correctAnswerIndex = 0,
                            text = "What vow did the little girl make to her mother?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.redhood_3,
                        content = "Suddenly, the wolf appeared from behind the trees and questioned the little girl’s presence in the forest. Overlooking her mother's caution, she told him she was on her way to visit her ailing grandmother. With a wicked smile, the wolf let her proceed, secretly plotting his malicious scheme.",
                        question = Question(
                            multipleChoice = false,
                            options = listOf(
                                ""
                            ),
                            correctAnswerIndex = 0,
                            text = "What inquiry did the wolf pose to the young girl?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.redhood_4,
                        content = "The wolf swiftly approached the grandmother's cottage and knocked on the door. Mistaking him for her granddaughter, the unsuspecting grandmother opened it. In an instant, the wolf overpowered her, rendering her unconscious. He then hid her in a closet, donned her clothes and jewelry, and mimicked her voice.",
                        question = Question(
                            multipleChoice = false,
                            options = listOf(
                                ""
                            ),
                            correctAnswerIndex = 0,
                            text = "What actions did the wolf take against the grandmother?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.redhood_5,
                        content = "Upon arriving at her grandmother’s cottage and knocking, the little girl was met with silence as the wolf feigned sleep. Entering, she immediately observed the unnatural size of the \"grandmother's\" ears, eyes, hands, and mouth. It quickly became clear to her that the wolf had assumed her grandmother's guise.",
                        question = Question(
                            multipleChoice = true,
                            options = listOf(
                                "She looked smaller.",
                                "She appeared significantly larger and transformed.",
                            ),
                            correctAnswerIndex = 1,
                            text = "What did the young girl observe about her grandmother?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.redhood_6,
                        content = "In a frantic rush, she fled the house, crying out for help. A nearby hunter, hearing her cries, swiftly came to her rescue. After she explained the situation, the hunter pursued the wolf, who fled in fear. The little girl then found her grandmother hidden in the closet, and they joyfully reunited.",
                        question = Question(
                            multipleChoice = false,
                            options = listOf(
                                "",
                            ),
                            correctAnswerIndex = 0,
                            text = "How did the hunter respond upon hearing the girl's cries?",
                            userAnswer = "",
                        )
                    )
                )
            ),
            //endregion
        )
        _advancedStories.value = listAdvancedStories
        
        val listAdvanceCareen = listOf<Story>(
            //region No Smoking
            Story(
                title = "The Journey of Smoking",
                subtitle = "The Transformative Path of Quitting Smoking",
                imageRes = R.drawable.no_smoking,
                isComplete = false,
                bridgeHint = "Kamu akan membaca cerita The Journey of Smoking sambil mendengarkan audionya. Jawab pertanyaan dan ikuti instruksi yang diberikan.",
                closeStatement = "Kamu telah menyelesaikan cerita The Journey of Smoking. Selanjutnya kamu akan melihat analisis grammar dan vocabularymu.",
                paragraphs = listOf(

                    StoryParagraph(
                        imageRes = R.drawable.no_smoking_1,
                        content = "Cigarettes harm the body in multiple ways, starting with their 5,000 chemical substances. Tar coats the teeth, gums, and lungs, causing decay and damaging nerve endings, leading to loss of smell. Smoke weakens the lungs by destroying cilia, the tiny structures that keep airways clean. Carbon monoxide from cigarette smoke enters the bloodstream, displacing oxygen and causing oxygen deprivation and shortness of breath.",
                        question = Question(
                            multipleChoice = true,
                            options = listOf(
                                "Tar",
                                "Carbon monoxide",
                            ),
                            correctAnswerIndex = 1,
                            text = "What substance in cigarette smoke coats the teeth, gums, and lungs, causing decay?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.no_smoking_2,
                        content = "Within seconds of inhaling, nicotine reaches the brain, triggering the release of dopamine and other chemicals that make smoking addictive. Simultaneously, it restricts blood flow by narrowing blood vessels and thickening their walls. This increases the risk of clots, leading to heart attacks and strokes. The chemicals in cigarettes also mutate DNA, causing cancer, and hinder DNA repair, weakening the body's defenses against multiple diseases.",
                        question = Question(
                            multipleChoice = false,
                            options = listOf(
                                ""
                            ),
                            correctAnswerIndex = 0,
                            text = "How does nicotine affect blood vessels?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.no_smoking_3,
                        content = "Smoking isn’t limited to causing lung cancer; it can lead to cancers in other tissues and organs, damage eyesight, and weaken bones. It complicates pregnancy for women and causes erectile dysfunction in men. About one-third of cancer deaths in the U.S. are linked to smoking. These wide-ranging effects emphasize the extensive harm caused by cigarettes.",
                        question = Question(
                            multipleChoice = false,
                            options = listOf(
                                ""
                            ),
                            correctAnswerIndex = 0,
                            text = "How does smoking impact reproductive health in men and women?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.no_smoking_4,
                        content = "Quitting smoking brings almost instant health benefits. Within 20 minutes, heart rate and blood pressure normalize, and after 12 hours, carbon monoxide levels stabilize. In just one day, the risk of heart attack begins to decrease. By two days, senses of smell and taste start to recover. After a month, lungs function better, and symptoms like coughing reduce.",
                        question = Question(
                            multipleChoice = false,
                            options = listOf(
                                ""
                            ),
                            correctAnswerIndex = 0,
                            text = "What happens to carbon monoxide levels in the blood after 12 hours of quitting smoking?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.no_smoking_5,
                        content = "The body's recovery continues over months and years. By nine months, cilia in the lungs regenerate, reducing infections. After a year, heart disease risk is halved, and by five years, the chance of clots and strokes decreases significantly. A decade later, lung cancer risk drops by 50%, and after 15 years, the risk of coronary heart disease matches that of a non-smoker.",
                        question = Question(
                            multipleChoice = true,
                            options = listOf(
                                "9 months",
                                "1 month",
                            ),
                            correctAnswerIndex = 0,
                            text = "By what time frame do cilia in the lungs fully regenerate?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.no_smoking_6,
                        content = "Quitting smoking is challenging due to nicotine withdrawal, which can cause anxiety and depression. However, tools like nicotine replacement therapies (gum, patches, lozenges) and behavioral support (counseling, therapy, and exercise) help manage withdrawal symptoms and prevent relapse. With determination and the right resources, quitting sets the body on a path to health and longevity.",
                        question = Question(
                            multipleChoice = false,
                            options = listOf(
                                "",
                            ),
                            correctAnswerIndex = 0,
                            text = "What are two types of support that can help someone quit smoking?",
                            userAnswer = "",
                        )
                    )
                )
            ),
            //endregion

            //region Engineering
            Story(
                title = "How Hydroelectric Dams Generate Power",
                subtitle = "The Mechanics and Impact of Hydroelectric Dams",
                imageRes = R.drawable.engineering,
                isComplete = false,
                bridgeHint = "Kamu akan membaca cerita How Hydroelectric Dams Generate Power sambil mendengarkan audionya. Jawab pertanyaan dan ikuti instruksi yang diberikan.",
                closeStatement = "Kamu telah menyelesaikan cerita How Hydroelectric Dams Generate Power. Selanjutnya kamu akan melihat analisis grammar dan vocabularymu.",
                paragraphs = listOf(

                    StoryParagraph(
                        imageRes = R.drawable.engineering_1,
                        content = "Humans have harnessed water's energy for centuries, using it to drive mills and pumps. However, converting the potential energy of water into electricity is a relatively modern innovation. Hydroelectric dams require two key components to generate electricity: a plentiful water source and an elevation difference, known as the head, between the upstream headwater and downstream tailwater. The dam separates these two, creating the conditions needed for energy production.",
                        question = Question(
                            multipleChoice = true,
                            options = listOf(
                                "Solar panels",
                                "Water and elevation",
                            ),
                            correctAnswerIndex = 1,
                            text = "What is needed to generate electricity with a hydroelectric dam?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.engineering_2,
                        content = "The process begins as water enters the dam through penstocks at the main inlets. The water flows down the penstocks to the hydroelectric turbines housed in the powerhouse. As water moves over the turbine runners, the runners rotate, transforming the water’s energy into mechanical energy. This mechanical energy is then transferred to electrical generators connected to the turbines via common shafts, ultimately producing electricity.",
                        question = Question(
                            multipleChoice = false,
                            options = listOf(
                                ""
                            ),
                            correctAnswerIndex = 0,
                            text = "What energy transformation occurs in the turbine runners?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.engineering_3,
                        content = "The generated electricity is then distributed through transformers to the high-voltage national grid for use. The water completes its journey by flowing out of the powerhouse through draft tubes into the tailwater. This continuous cycle allows hydro dams to provide a reliable and constant source of power generation.",
                        question = Question(
                            multipleChoice = false,
                            options = listOf(
                                ""
                            ),
                            correctAnswerIndex = 0,
                            text = "What happens to the water after it passes through the powerhouse?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.engineering_4,
                        content = "Hydroelectric dams offer many advantages. They are a renewable energy source that produces no waste products and are efficient and economical to operate. Additionally, dams have a long service life, with some operating for over 100 years. Their consistent performance makes them a cornerstone of renewable energy solutions.",
                        question = Question(
                            multipleChoice = false,
                            options = listOf(
                                ""
                            ),
                            correctAnswerIndex = 0,
                            text = "How long can some hydroelectric dams remain operational?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.engineering_5,
                        content = "However, hydro dams are not without their drawbacks. Their construction can disrupt local habitats for plants and animals, requiring significant changes to the environment. Building dams is also costly, with high initial investments. Furthermore, while generally reliable, dam failures, though rare, can lead to catastrophic flooding downstream.",
                        question = Question(
                            multipleChoice = true,
                            options = listOf(
                                "They are cheap to construct",
                                "They can disrupt local habitats",
                            ),
                            correctAnswerIndex = 1,
                            text = "What is one major disadvantage of hydroelectric dams?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.engineering_6,
                        content = "Despite their challenges, hydroelectric dams remain a vital part of the energy landscape, offering sustainable power generation for the future. As iconic structures of engineering, they are likely to endure in our surroundings for many decades, contributing to energy needs worldwide.",
                        question = Question(
                            multipleChoice = false,
                            options = listOf(
                                "",
                            ),
                            correctAnswerIndex = 0,
                            text = "What role do hydroelectric dams play in the future of energy?",
                            userAnswer = "",
                        )
                    )
                )
            ),
            //endregion

            //region Money
            Story(
                title = "The Impact of Inflation on Prices and the Economy",
                subtitle = "How Rising Prices Affect Your Wallet and the Economy",
                imageRes = R.drawable.money,
                isComplete = false,
                bridgeHint = "Kamu akan membaca cerita The Impact of Inflation on Prices and the Economy sambil mendengarkan audionya. Jawab pertanyaan dan ikuti instruksi yang diberikan.",
                closeStatement = "Kamu telah menyelesaikan cerita The Impact of Inflation on Prices and the Economy. Selanjutnya kamu akan melihat analisis grammar dan vocabularymu.",
                paragraphs = listOf(

                    StoryParagraph(
                        imageRes = R.drawable.money_1,
                        content = "In 1967, McDonald's launched the Big Mac for just 45 cents, but today the same burger costs \$3.99. This significant price increase is an example of inflation, which is the rising cost of goods and services over time.",
                        question = Question(
                            multipleChoice = true,
                            options = listOf(
                                "\$3.99",
                                "45 cents",
                            ),
                            correctAnswerIndex = 1,
                            text = "What was the price of a Big Mac when McDonald's launched it in 1967?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.money_2,
                        content = "Inflation is monitored by statistical bureaus, which track price changes for everything, from everyday items like leg wax to luxury goods like whiskey. The data is then used to calculate the rate of inflation, a single percentage number.",
                        question = Question(
                            multipleChoice = false,
                            options = listOf(
                                ""
                            ),
                            correctAnswerIndex = 0,
                            text = "What is the purpose of monitoring inflation?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.money_3,
                        content = "Governments use the inflation rate to adjust welfare payments and to help workers determine fair pay raises. In a stable economy, prices usually rise slowly, by around 1 to 2% each year.",
                        question = Question(
                            multipleChoice = false,
                            options = listOf(
                                ""
                            ),
                            correctAnswerIndex = 0,
                            text = "How much do prices typically rise in a stable economy each year?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.money_4,
                        content = "However, inflation can sometimes spiral out of control, as seen in Zimbabwe in 2008, where prices doubled every 24 hours due to an overabundance of money. This hyperinflation caused people to become trillionaires, and the country even ran out of paper to print banknotes.",
                        question = Question(
                            multipleChoice = false,
                            options = listOf(
                                ""
                            ),
                            correctAnswerIndex = 0,
                            text = "What was the cause of hyperinflation in Zimbabwe?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.money_5,
                        content = "Once inflation spirals like this, it's hard to recover. In such cases, governments may be forced to devalue their currency or abandon it altogether, as was the case in Germany during the 1920s.",
                        question = Question(
                            multipleChoice = true,
                            options = listOf(
                                "Increase wages",
                                "Devalue or abandon their currency",
                            ),
                            correctAnswerIndex = 1,
                            text = "What must governments do if inflation becomes uncontrollable?",
                            userAnswer = "",
                        )
                    ),

                    StoryParagraph(
                        imageRes = R.drawable.money_6,
                        content = "While inflation can be harmful when it becomes excessive, a small amount of inflation is actually beneficial for the economy. It helps stimulate growth and prevents deflation. However, inflation can negatively affect savers, as the value of money decreases over time if it isn't growing at the same rate.",
                        question = Question(
                            multipleChoice = false,
                            options = listOf(
                                "",
                            ),
                            correctAnswerIndex = 0,
                            text = "How does inflation affect savers?",
                            userAnswer = "",
                        )
                    )
                )
            ),
            //endregion
        )
        _advancedCareerStories.value = listAdvanceCareen
    }

    fun getListCompletedStories() {
        viewModelScope.launch{
            getCompletedStories().collect {response ->
                response.onSuccess {
                    val completedStories = it
                    _beginnerCompletedStories.addAll(completedStories.beginner )
                    _intermediateCompletedStories.addAll(completedStories.intermediate)
                    _advancedCompletedStories.addAll(completedStories.advancedTales)
                    _advancedCareerCompletedStories.addAll(completedStories.careerTales)
                    Log.d("StoryScapeViewModel", "_beginner: $_beginnerCompletedStories")
                }
            }
        }
    }

    fun updateCompletedStries(storyCategory: StoryCategory, index: Int, value: Boolean){
        viewModelScope.launch {
            when(storyCategory){
                StoryCategory.BEGINNER -> {
                    _beginnerCompletedStories[index] = value
                }
                StoryCategory.INTERMEDIATE -> {
                    _intermediateCompletedStories[index] = value
                }
                StoryCategory.ADVANCED -> {
                    _advancedCompletedStories[index] = value
                }
                StoryCategory.ADVANCED_CAREER -> {
                    _advancedCareerCompletedStories[index] = value
                }
            }
            val completedStories = CompletedStories(
                beginner = arrayListOf(_beginnerCompletedStories[0], _beginnerCompletedStories[1], _beginnerCompletedStories[2]),
                intermediate = arrayListOf(_intermediateCompletedStories[0], _intermediateCompletedStories[1], _intermediateCompletedStories[2]),
                advancedTales = arrayListOf(_advancedCompletedStories[0], _advancedCompletedStories[1], _advancedCompletedStories[2]),
                careerTales = arrayListOf(_advancedCareerCompletedStories[0], _advancedCareerCompletedStories[1], _advancedCareerCompletedStories[2])
            )

            Log.d("StoryScapeViewModel", "Completed Stories: $completedStories")

            updateCompletedStories(completedStories).collect {response ->
                response.onSuccess {
                    Log.d("StoryScapeViewModel", "Completed Stories Updated")
                }
                response.onFailure {
                    Log.d("StoryScapeViewModel", "Error: ${it.message}")
                }
            }
        }
    }

    fun getTotalCompletedStoriesInEachCategory() {
        viewModelScope.launch {
            getCompletedStories().collect {response ->
                response.onSuccess {
                    val completedStories = it
                    _beginnerCompletedStories.addAll(completedStories.beginner )
                    _intermediateCompletedStories.addAll(completedStories.intermediate)
                    _advancedCompletedStories.addAll(completedStories.advancedTales)
                    _advancedCareerCompletedStories.addAll(completedStories.careerTales)

                    _totalCompletedStories.value += _beginnerCompletedStories.filter { it == true }.size
                    _totalCompletedStories.value += _intermediateCompletedStories.filter { it == true }.size
                    _totalCompletedStories.value += _advancedCompletedStories.filter { it == true }.size
                    _totalCompletedStories.value += _advancedCareerCompletedStories.filter { it == true }.size

                }
            }
        }
    }

    fun analyzeText(category: StoryCategory) {
        viewModelScope.launch {
            try {

                _uiStateGemini.value = UIState.Loading

                val answerList = getAllUserAnswersNotMultipleChoice(category)

                val answer1 = answerList.random()

                val answer2 = answerList.random()

                // Get grammar analysis
                val grammarPrompt = generateGrammarPrompt(answer1)
                val grammarResponse = geminiModel.generateContent(grammarPrompt)
                    .text?.replace("```json", "")?.replace("```", "")?.let { Json.decodeFromString<GrammarResponse>(it) }
                _grammarState.value = grammarResponse


                // Get vocabulary analysis
                val vocabPrompt = generateVocabPrompt(answer2)
                val vocabResponse = geminiModel.generateContent(vocabPrompt)
                    .text?.replace("```json", "")?.replace("```", "")?.let { Json.decodeFromString<VocabularyResponse>(it) }
                _vocabularyState.value = vocabResponse

                _uiStateGemini.value = UIState.Success("Analysis completed")

            } catch (e: Exception) {
                // Handle errors
                _uiStateGemini.value = UIState.Error(e.message ?: "Unknown error")
                Log.d("StoryScapeViewModel", "Error: ${e.message}")
            }
        }
    }

    fun chooseStory(index: Int) {
        _currentStoryIndex.intValue = index
    }

    fun moveToNextQuestion(category: StoryCategory) {
        if (_currentPharagraphIndex.intValue < when (category) {
                StoryCategory.BEGINNER -> _beginnerStories.value[_currentStoryIndex.intValue].paragraphs.size - 1
                StoryCategory.INTERMEDIATE -> _intermediateStories.value[_currentStoryIndex.intValue].paragraphs.size - 1
                StoryCategory.ADVANCED -> _advancedStories.value[_currentStoryIndex.intValue].paragraphs.size - 1
                StoryCategory.ADVANCED_CAREER -> _advancedCareerStories.value[_currentStoryIndex.intValue].paragraphs.size - 1
            }) {
            _currentPharagraphIndex.intValue++
            Log.d("StoryScapeViewModel", "Current Paragraph Index: ${_currentPharagraphIndex.intValue}")
        } else {
            _currentPharagraphIndex.intValue = 0
        }
    }

    fun resetParagraphIndex() {
        _currentPharagraphIndex.intValue = 0
    }

    fun answerQuestion(answer: String, category: StoryCategory) {
        val currentParagraphQuestion = when (category) {
            StoryCategory.BEGINNER -> _beginnerStories.value[_currentStoryIndex.intValue].paragraphs[_currentPharagraphIndex.intValue].question
            StoryCategory.INTERMEDIATE -> _intermediateStories.value[_currentStoryIndex.intValue].paragraphs[_currentPharagraphIndex.intValue].question
            StoryCategory.ADVANCED -> _advancedStories.value[_currentStoryIndex.intValue].paragraphs[_currentPharagraphIndex.intValue].question
            StoryCategory.ADVANCED_CAREER -> _advancedCareerStories.value[_currentStoryIndex.intValue].paragraphs[_currentPharagraphIndex.intValue].question
        }

        currentParagraphQuestion.userAnswer = answer

        Log.d("StoryScapeViewModel", "User Answer: ${currentParagraphQuestion.userAnswer}")
    }

    private fun getAllUserAnswersNotMultipleChoice(category: StoryCategory): List<String> {
        when (category) {
            StoryCategory.BEGINNER -> {
                val userAnswers = mutableListOf<String>()
                _beginnerStories.value[_currentStoryIndex.intValue].paragraphs.forEach { paragraph ->
                    if (!paragraph.question.multipleChoice) {
                        userAnswers.add(paragraph.question.userAnswer)
                    }
                }
                _listOfAnswers.addAll(userAnswers)
                return userAnswers
            }
            StoryCategory.INTERMEDIATE -> {
                val userAnswers = mutableListOf<String>()
                _intermediateStories.value[_currentStoryIndex.intValue].paragraphs.forEach { paragraph ->
                    if (!paragraph.question.multipleChoice) {
                        userAnswers.add(paragraph.question.userAnswer)
                    }
                }
                _listOfAnswers.addAll(userAnswers)
                return userAnswers
            }
            StoryCategory.ADVANCED -> {
                val userAnswers = mutableListOf<String>()
                _advancedStories.value[_currentStoryIndex.intValue].paragraphs.forEach { paragraph ->
                    if (!paragraph.question.multipleChoice) {
                        userAnswers.add(paragraph.question.userAnswer)
                    }
                }
                _listOfAnswers.addAll(userAnswers)
                return userAnswers
            }
            StoryCategory.ADVANCED_CAREER -> {
                val userAnswers = mutableListOf<String>()
                _advancedCareerStories.value[_currentStoryIndex.intValue].paragraphs.forEach { paragraph ->
                    if (!paragraph.question.multipleChoice) {
                        userAnswers.add(paragraph.question.userAnswer)
                    }
                }
                _listOfAnswers.addAll(userAnswers)
                return userAnswers
            }
        }
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

    public enum class StoryCategory {
        BEGINNER,
        INTERMEDIATE,
        ADVANCED,
        ADVANCED_CAREER
    }
}