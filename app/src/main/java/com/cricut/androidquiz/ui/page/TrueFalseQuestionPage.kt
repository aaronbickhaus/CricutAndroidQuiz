package com.cricut.androidquiz.ui.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cricut.common.model.Question
import androidx.hilt.navigation.compose.hiltViewModel
import com.cricut.androidquiz.ui.components.QuizCard
import com.cricut.androidquiz.ui.viewmodel.MainViewModel

/**
 * [TrueFalseQuestionPage]
 *
 *  Page that displays a true false question
 *
 * @param question [Question]
 * @param onAnswerSelected [Boolean]
 * @param initialSelection [Boolean]
 * @param viewModel [MainViewModel]
 */
@Composable
fun TrueFalseQuestionPage(
    question: Question,
    onAnswerSelected: (Boolean) -> Unit,
    initialSelection: Boolean? = null,
    viewModel: MainViewModel = hiltViewModel()
) {
    val savedAnswer = viewModel.userAnswers[question.questionId] as? Boolean ?: initialSelection
    val options = listOf("True", "False")

    QuizCard(questionText = question.questionText, content = {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            options.forEachIndexed { index, option ->
                val isSelected = savedAnswer == (index == 0)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    RadioButton(
                        selected = isSelected,
                        onClick = {
                            val answer = index == 0
                            viewModel.saveAnswer(question.questionId, answer)
                            onAnswerSelected(answer)
                        },
                        colors = RadioButtonDefaults.colors(
                            MaterialTheme.colorScheme.onPrimary,
                            MaterialTheme.colorScheme.onPrimary,
                            MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.38f)
                        )
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                    Text(
                        text = option,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    })
}