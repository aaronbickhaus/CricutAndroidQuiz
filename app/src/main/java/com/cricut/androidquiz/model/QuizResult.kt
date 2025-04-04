package com.cricut.androidquiz.model

data class QuizResult(
    val questionText: String,
    val correctAnswer: String,
    val userAnswer: String,
    val isCorrect: Boolean
)