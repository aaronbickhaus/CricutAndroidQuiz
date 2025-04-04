package com.cricut.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.cricut.common.model.Answer

@Entity(
    tableName = "answers",
    foreignKeys = [
        ForeignKey(
            entity = QuestionEntity::class,
            parentColumns = ["id"],
            childColumns = ["questionId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["questionId"])]
)
data class AnswerEntity(
    @PrimaryKey(autoGenerate = true) val id : Int,
    val questionId: Int,
    val displayAnswers: List<String>?,
    val answer : String
)

fun AnswerEntity.toAnswer(): Answer {
    return Answer(
        id = this.id,
        answer = this.answer,
        displayAnswers = this.displayAnswers ?: emptyList()
    )
}

