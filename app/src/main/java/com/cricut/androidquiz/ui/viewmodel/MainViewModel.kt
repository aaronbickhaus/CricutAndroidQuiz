package com.cricut.androidquiz.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cricut.androidquiz.model.QuizResult
import com.cricut.common.constants.Constants
import com.cricut.common.logger.Logger
import com.cricut.common.logger.LoggerImpl
import com.cricut.common.model.Question
import com.cricut.common.model.QuestionType
import com.cricut.common.provider.DispatchProvider
import com.cricut.domain.usecase.concrete.FetchQuestionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.cricut.common.model.Result

/**
 * [MainViewModel][HiltViewModel]
 *
 * @param dispatchProvider [DispatchProvider]
 * @param logger [Logger]
 * @param fetchQuestionsUseCase [FetchQuestionsUseCase]
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val dispatchProvider: DispatchProvider,
    private val logger: Logger,
    private val fetchQuestionsUseCase: FetchQuestionsUseCase
) : ViewModel() {

    var questions by mutableStateOf<List<Question>>(emptyList())
        private set

    var isLoading by mutableStateOf(true)
        private set

    private val _userAnswers = mutableStateMapOf<Int, Any>()
    val userAnswers = _userAnswers

    init {
       getQuestions()
    }

    fun saveAnswer(index: Int, answer: Any) {
        _userAnswers[index] = answer
    }

    fun resetQuiz() {
        _userAnswers.clear()
        getQuestions()
    }

    fun isQuestionAnswered(index: Int): Boolean {
        return _userAnswers.containsKey(index)
    }

    private fun getQuestions() {
        viewModelScope.launch(dispatchProvider.io) {
            when (val result = fetchQuestionsUseCase(params = Unit, dispatcher = dispatchProvider.io)) {
                is Result.Success -> {
                    questions = result.data
                    isLoading = false
                    LoggerImpl.debug(result.data.toString())
                }
                is Result.Failure -> {
                    result.error.message?.let { msg ->
                        LoggerImpl.debug(msg)
                    }
                }
            }
        }
    }

    fun getQuizResults(): List<QuizResult> {
        return questions.map { question ->
            when (question.questionType) {
                QuestionType.SingleAnswer -> {
                    val userIndex = _userAnswers[question.questionId] as? Int
                    val displayOptions = question.answers.firstOrNull()?.displayAnswers ?: emptyList()
                    val userAnswerText = if (userIndex != null && userIndex in displayOptions.indices) {
                        displayOptions[userIndex]
                    } else {
                        Constants.NOT_ANSWERED
                    }

                    val correctAnswerText = question.answers.firstOrNull()?.answer ?:  Constants.NO_ANSWER
                    QuizResult(
                        questionText = question.questionText,
                        correctAnswer = correctAnswerText,
                        userAnswer = userAnswerText,
                        isCorrect = userAnswerText.equals(correctAnswerText, ignoreCase = true)
                    )
                }
                QuestionType.MultipleAnswer -> {
                    val userSelections = _userAnswers[question.questionId] as? List<Boolean> ?: emptyList()
                    val displayOptions = question.answers.firstOrNull()?.displayAnswers ?: emptyList()
                    val userAnswerText = if (userSelections.isNotEmpty()) {
                        userSelections.withIndex()
                            .filter { it.value }
                            .mapNotNull { displayOptions.getOrNull(it.index) }
                            .joinToString(", ")
                    } else  Constants.NOT_ANSWERED

                    val correctAnswerText = question.answers.firstOrNull()?.answer ?:  Constants.NO_ANSWER
                    val isCorrect = userAnswerText.equals(correctAnswerText, ignoreCase = true)
                    QuizResult(
                        questionText = question.questionText,
                        correctAnswer = correctAnswerText,
                        userAnswer = userAnswerText,
                        isCorrect = isCorrect
                    )
                }
                QuestionType.TextAnswer -> {
                    val userText = _userAnswers[question.questionId] as? String ?:  Constants.NOT_ANSWERED
                    val correctAnswerText = question.answers.firstOrNull()?.answer ?:  Constants.NO_ANSWER
                    QuizResult(
                        questionText = question.questionText,
                        correctAnswer = correctAnswerText,
                        userAnswer = userText,
                        isCorrect = userText.trim().equals(correctAnswerText, ignoreCase = true)
                    )
                }
                QuestionType.TrueFalseAnswer -> {
                    val userBool = _userAnswers[question.questionId] as? Boolean
                    val userAnswerText = when (userBool) {
                        true -> Constants.TRUE
                        false -> Constants.FALSE
                        else ->  Constants.NOT_ANSWERED
                    }
                    val correctAnswerText = question.answers.firstOrNull()?.answer ?: Constants.NO_ANSWER
                    QuizResult(
                        questionText = question.questionText,
                        correctAnswer = correctAnswerText,
                        userAnswer = userAnswerText,
                        isCorrect = userAnswerText.equals(correctAnswerText, ignoreCase = true)
                    )
                }
            }
        }
    }
}