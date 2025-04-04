package com.cricut.androidquiz.ui.page

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.text.TextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.cricut.common.model.Question
import androidx.hilt.navigation.compose.hiltViewModel
import com.cricut.androidquiz.ui.components.QuizCard
import com.cricut.androidquiz.ui.viewmodel.MainViewModel
import com.cricut.common.constants.Constants

/**
 * [TextAnswerQuestionPage]
 *
 * Page that displays text answer question
 *
 * @param question [Question]
 * @param onTextChanged
 * @param viewModel [MainViewModel]
 * @param initialText [String]
 */
@Composable
fun TextAnswerQuestionPage(
    question: Question,
    onTextChanged: (String) -> Unit,
    viewModel: MainViewModel = hiltViewModel(),
    initialText: String = ""
) {
    val answerText = viewModel.userAnswers[question.questionId] as? String ?: initialText

    QuizCard(questionText = question.questionText, content = {
        OutlinedTextField(
            value = answerText,
            onValueChange = { newText ->
                onTextChanged(newText)
                viewModel.saveAnswer(question.questionId, newText)
            },
            label = {
                Text(
                    Constants.YOUR_ANSWER,
                    color = MaterialTheme.colorScheme.onPrimary // Label color
                )
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            textStyle = TextStyle(color = MaterialTheme.colorScheme.onPrimary),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
                cursorColor = MaterialTheme.colorScheme.onPrimary,
                errorBorderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
                focusedPlaceholderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
            )
        )
    })
}

