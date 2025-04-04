package com.cricut.data.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.cricut.common.model.Question

data class QuestionWithAnswers(
    @Embedded val question: QuestionEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "questionId"
    )
    val answers: List<AnswerEntity>
)

fun QuestionWithAnswers.toQuestion(): Question {
    return Question(
        questionType = this.question.questionType,
        questionId = this.question.id,
        questionText = this.question.question,
        answers = this.answers.map { it.toAnswer() }
    )
}