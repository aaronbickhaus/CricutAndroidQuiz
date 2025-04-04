import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import com.cricut.common.model.QuestionType
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.cricut.androidquiz.ui.page.QuizResultPage
import com.cricut.androidquiz.ui.page.WelcomePage
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch
import androidx.compose.ui.unit.dp
import com.cricut.androidquiz.ui.page.MultipleChoiceQuestionPage
import com.cricut.androidquiz.ui.page.SingleChoiceQuestionPage
import com.cricut.androidquiz.ui.page.TrueFalseQuestionPage
import com.cricut.androidquiz.ui.page.TextAnswerQuestionPage
import com.cricut.common.constants.Constants
import com.cricut.common.model.Question

/**
 * [QuizPager]
 *
 * @param questions [List][Question]
 * @param onQuestionAnswered
 * @param onRestart
 * @param isQuestionAnswered
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun QuizPager(
    questions: List<Question>,
    onQuestionAnswered: (Int, Any) -> Unit,
    onRestart: () -> Unit,
    isQuestionAnswered: (Int) -> Boolean
) {
    val totalPages = questions.size + 2
    val pagerState = rememberPagerState(initialPage = 0)
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            count = totalPages,
            state = pagerState,
            userScrollEnabled = false,
            modifier = Modifier.weight(1f)
        ) { page ->
            when (page) {
                0 -> {
                    WelcomePage {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(1)
                        }
                    }
                }
                totalPages - 1 -> {
                    QuizResultPage(
                        onRestart = {
                            coroutineScope.launch {
                                pagerState.scrollToPage(0)
                            }
                            onRestart()
                        }
                    )
                }
                else -> {
                    val question = questions[page - 1]
                    when (question.questionType) {
                        QuestionType.SingleAnswer -> SingleChoiceQuestionPage(
                            question = question,
                            onAnswerSelected = { answer ->
                                onQuestionAnswered(page - 1, answer)
                            }
                        )
                        QuestionType.MultipleAnswer -> MultipleChoiceQuestionPage(
                            question = question,
                            onAnswerToggled = { index, checked ->
                                onQuestionAnswered(page - 1, Pair(index, checked))
                            }
                        )
                        QuestionType.TextAnswer -> TextAnswerQuestionPage(
                            question = question,
                            onTextChanged = { text ->
                                onQuestionAnswered(page - 1, text)
                            }
                        )
                        QuestionType.TrueFalseAnswer -> TrueFalseQuestionPage(
                            question = question,
                            onAnswerSelected = { answer ->
                                onQuestionAnswered(page - 1, answer)
                            }
                        )
                    }
                }
            }
        }
        if (pagerState.currentPage in 1 until totalPages - 1) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (pagerState.currentPage > 1) {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        }
                    ) {
                        Text(Constants.BACK)
                    }
                } else {
                    Spacer(modifier = Modifier.width(64.dp))
                }
                Button(
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    },
                    enabled = isQuestionAnswered(pagerState.currentPage - 1)
                ) {
                    Text(Constants.NEXT)
                }
            }
        }
    }
}
