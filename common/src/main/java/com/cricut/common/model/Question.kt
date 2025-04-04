package com.cricut.common.model

data class Question(
    val questionType : QuestionType,
    val questionId: Int,
    val questionText: String,
    val  answers: List<Answer>
)
