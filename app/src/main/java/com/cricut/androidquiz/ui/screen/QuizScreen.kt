package com.cricut.androidquiz.ui.screen

import QuizPager
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.cricut.androidquiz.ui.viewmodel.MainViewModel
import androidx.hilt.navigation.compose.hiltViewModel

/**
 *  [QuizScreen]
 *
 *   Main screen that loads the [QuizPager]
 *
 *  @param viewModel [MainViewModel]
 */
@Composable
fun QuizScreen(viewModel: MainViewModel = hiltViewModel()) {
    val questions = viewModel.questions
    val isLoading = viewModel.isLoading

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        QuizPager(
            questions = questions,
            onQuestionAnswered = { index, answer ->
                viewModel.saveAnswer(index, answer)
            },
            onRestart = {
                viewModel.resetQuiz()
            },
            isQuestionAnswered = { index ->
                viewModel.isQuestionAnswered(index)
            }
        )
    }
}
