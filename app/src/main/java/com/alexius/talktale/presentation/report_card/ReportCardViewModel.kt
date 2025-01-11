package com.alexius.talktale.presentation.report_card

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexius.core.R
import com.alexius.core.domain.model.QuestionReportCard
import com.alexius.core.domain.model.ReportCardAnalysis
import com.alexius.core.domain.usecases.talktalenav.GenerateGrammarReportCardPrompt
import com.alexius.core.domain.usecases.talktalenav.GenerateVocabReportCardPrompt
import com.alexius.core.util.UIState
import com.google.ai.client.generativeai.GenerativeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class ReportCardViewModel @Inject constructor(
    private val generateGrammarReportCardPrompt: GenerateGrammarReportCardPrompt,
    private val generateVocabReportCardPrompt: GenerateVocabReportCardPrompt,
    private val geminiModel: GenerativeModel
): ViewModel() {

    private val _listGrammarResponse = MutableStateFlow<List<ReportCardAnalysis>>(emptyList())
    val listGrammarResponse: StateFlow<List<ReportCardAnalysis>> = _listGrammarResponse

    private val _listVocabResponse = MutableStateFlow<List<ReportCardAnalysis>>(emptyList())
    val listVocabResponse: StateFlow<List<ReportCardAnalysis>> = _listVocabResponse

    private val _currentQuestionIndex = mutableIntStateOf(0)
    val currentQuestionIndex: State<Int> = _currentQuestionIndex

    private val _questions = mutableStateListOf<QuestionReportCard>()
    val questions: List<QuestionReportCard> = _questions

    private val _uiStateGemini = MutableStateFlow<UIState<String>>(UIState.Loading)
    val uiStateGemini: StateFlow<UIState<String>> = _uiStateGemini

    init {
        _questions.addAll(listOf(
            QuestionReportCard("Mengapa kata \"wants\" digunakan dalam kalimat \"The Giant wants Mbok Srini to raise the child\"?",
                listOf("Karena subjek tunggal membutuhkan akhiran -s.", "Karena menunjukkan tindakan di masa lampau.", "Karena subjek jamak membutuhkan kata kerja tanpa akhiran -s."),
                R.drawable.timun_mas_2,
                0),
            QuestionReportCard("Apa bentuk past tense dari kata kerja berikut: run, wake, eat?",
                listOf("run, wake, eaten", "ran, woke, ate", "ran, wake, eaten"),
                R.drawable.lion_1,
                1),
            QuestionReportCard("Sebutkan sinonim dari kata \"very thin\" yang lebih formal dan kaya makna.",
                listOf("Robust", "Plump", "Delicate"),
                R.drawable.timun_mas_4,
                2),
            QuestionReportCard("Apa sinonim yang lebih baik untuk kata \"attacked\" pada kalimat \"The wolf attacked the grandmother\"?",
                listOf("Defended", "Chased", "Protected"),
                R.drawable.redhood_4,
                1),
        ))
    }

    fun analyzeAnswers(answers: List<String>){
        viewModelScope.launch{
            try {
                _uiStateGemini.value = UIState.Loading

                val listResponseGrammar: ArrayList<ReportCardAnalysis> = arrayListOf()
                val listResponseVocab: ArrayList<ReportCardAnalysis> = arrayListOf()

                for (i in 0 .. 2){
                    answers[i].let {
                        val grammarPrompt = generateGrammarReportCardPrompt(it)
                        val grammarResponse = geminiModel.generateContent(grammarPrompt)
                            .text?.replace("```json", "")?.replace("```", "")?.let { Json.decodeFromString<ReportCardAnalysis>(it) }
                        listResponseGrammar.add(grammarResponse?: ReportCardAnalysis(
                            "", "",
                            suggestion = ""
                        ))
                    }
                }
                _listGrammarResponse.value = listResponseGrammar

                for (i in 3 .. 5){
                    answers[i].let {
                        val vocabPrompt = generateVocabReportCardPrompt(it)
                        val vocabResponse = geminiModel.generateContent(vocabPrompt)
                            .text?.replace("```json", "")?.replace("```", "")?.let { Json.decodeFromString<ReportCardAnalysis>(it) }
                        listResponseVocab.add(vocabResponse?: ReportCardAnalysis(
                            "", "",
                            suggestion = ""
                        ))
                    }
                }
                _listVocabResponse.value = listResponseVocab
            } catch (e: Exception) {
                _uiStateGemini.value = UIState.Error(e.message ?: "Unknown error")
                Log.d("ReportCardViewModel", "analyzeAnswers: ${e.message}")
            }
        }
    }

    fun selectAnswer(index: Int) {
        _questions[_currentQuestionIndex.intValue].selectedAnswer = index
    }

    fun moveToNextQuestion() {
       /* checkAnswer()*/
        if (_currentQuestionIndex.intValue < _questions.size - 1) {
            _currentQuestionIndex.intValue++
        }
    }
}