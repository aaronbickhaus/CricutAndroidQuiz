package com.cricut.androidquiz.ui.page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cricut.androidquiz.ui.viewmodel.MainViewModel
import com.cricut.common.constants.Constants

/**
 *  @[QuizResultPage]
 *
 *  displays results of quiz.
 *
 *   @param viewModel [MainViewModel]
 *   @param onRestart is a handler to restart quiz
 */
@Composable
fun QuizResultPage(
    viewModel: MainViewModel = hiltViewModel(),
    onRestart: () -> Unit
) {
    val results = viewModel.getQuizResults()
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text("Quiz Results", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(bottom = 16.dp, top = 8.dp), // Extra padding at the bottom (and top, if desired)
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(results) { result ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = if (result.isCorrect) Color(Constants.SUCCESS_GREEN) else Color(
                                Constants.ERROR_RED
                            )
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                result.questionText,
                                style = MaterialTheme.typography.headlineSmall.copy(color = MaterialTheme.colorScheme.onPrimary)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "${Constants.YOUR_ANSWER}${result.userAnswer}",
                                style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onPrimary)
                            )
                            Text(
                                "${Constants.CORRECT_ANSWER}${result.correctAnswer}",
                                style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onPrimary)
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    viewModel.resetQuiz()
                    onRestart()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(Constants.RESTART_QUIZ)
            }
        }
    }
}