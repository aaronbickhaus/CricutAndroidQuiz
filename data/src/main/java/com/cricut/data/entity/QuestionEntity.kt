package com.cricut.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cricut.common.model.QuestionType

@Entity(tableName = "questions")
data class QuestionEntity(
    @ColumnInfo(name = "answerType") val questionType : QuestionType,
    @PrimaryKey(autoGenerate = true) val id: Int,
    val question: String,
    @ColumnInfo(name = "answer_ids") val answerIds: List<Int>
)
