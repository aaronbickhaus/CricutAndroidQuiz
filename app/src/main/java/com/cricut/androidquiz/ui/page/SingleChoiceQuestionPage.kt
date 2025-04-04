package com.cricut.androidquiz.ui.page

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cricut.common.model.Question
import androidx.hilt.navigation.compose.hiltViewModel
import com.cricut.androidquiz.ui.components.QuizCard
import com.cricut.androidquiz.ui.viewmodel.MainViewModel

/**
 *  [SingleChoiceQuestionPage]
 *
 *  Page that displays single choice question
 *
 *  @param question [Question]
 *  @param onAnswerSelected
 *  @param viewModel [MainViewModel]
 *  @param initialSelection
 */
@Composable
fun SingleChoiceQuestionPage(
    question: Question,
    onAnswerSelected: (Int) -> Unit,
    viewModel: MainViewModel = hiltViewModel(),
    initialSelection: Int? = null
) {
    val selectedOption = viewModel.userAnswers[question.questionId] as? Int ?: initialSelection
    val options: List<String> = question.answers.firstOrNull()?.displayAnswers ?: emptyList()

    QuizCard(questionText = question.questionText, content = {
        options.forEachIndexed { index, option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                RadioButton(
                    selected = selectedOption == index,
                    onClick = {
                        onAnswerSelected(index)
                        viewModel.saveAnswer(question.questionId, index)
                    },
                    colors = RadioButtonDefaults.colors(
                        MaterialTheme.colorScheme.onPrimary,
                        MaterialTheme.colorScheme.onPrimary,
                        MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.38f)
                    )
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = option,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Start
                )
            }
        }
    })
}