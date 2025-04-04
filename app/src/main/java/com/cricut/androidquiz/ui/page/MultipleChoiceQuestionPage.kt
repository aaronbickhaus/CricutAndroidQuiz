package com.cricut.androidquiz.ui.page

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
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
 *  [MultipleChoiceQuestionPage]
 *
 * Page used to display multiple choice question
 *
 *  @param question [Question]
 *  @param onAnswerToggled
 *  @param viewModel [MainViewModel]
 *  @param initialSelections
 */
@Composable
fun MultipleChoiceQuestionPage(
    question: Question,
    onAnswerToggled: (Int, Boolean) -> Unit,
    viewModel: MainViewModel = hiltViewModel(),
    initialSelections: List<Boolean> = List(
        question.answers.firstOrNull()?.displayAnswers?.size ?: 0
    ) { false }
) {
    val options: List<String> = question.answers.firstOrNull()?.displayAnswers ?: emptyList()

    val savedResponse = viewModel.userAnswers[question.questionId]
    val selectedOptions = if (savedResponse is List<*>) {
        @Suppress("UNCHECKED_CAST")
        savedResponse as List<Boolean>
    } else {
        initialSelections
    }

    QuizCard(questionText = question.questionText, content = {
        options.forEachIndexed { index, option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Checkbox(
                    checked = selectedOptions.getOrNull(index) ?: false,
                    onCheckedChange = { checked ->
                        val updated = selectedOptions.toMutableList().also { it[index] = checked }
                        viewModel.saveAnswer(question.questionId, updated)
                        onAnswerToggled(index, checked)
                    },
                    colors = CheckboxDefaults.colors(
                        MaterialTheme.colorScheme.onPrimary,
                        MaterialTheme.colorScheme.onPrimary,
                        MaterialTheme.colorScheme.onPrimary,
                        MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.38f)
                    )
                )
                Spacer(modifier = Modifier.padding(horizontal = 12.dp))
                Text(
                    text = option,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    })
}