package com.cricut.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.cricut.data.entity.QuestionEntity
import com.cricut.common.model.QuestionType
import com.cricut.data.entity.QuestionWithAnswers

/**
 * [QuestionsDao]
 *
 * Dao used for Questions DB
 */
@Dao
interface QuestionsDao {

    @Transaction
    @Query("""
    SELECT * FROM questions
    WHERE id IN (
        SELECT id FROM questions WHERE answerType = :single ORDER BY RANDOM() LIMIT 1
    )
    OR id IN (
        SELECT id FROM questions WHERE answerType = :multiple ORDER BY RANDOM() LIMIT 1
    )
    OR id IN (
        SELECT id FROM questions WHERE answerType = :text ORDER BY RANDOM() LIMIT 1
    )
    OR id IN (
        SELECT id FROM questions WHERE answerType = :trueFalse ORDER BY RANDOM() LIMIT 1
    )
""")
    suspend fun fetchFourQuestions(
        single: String = QuestionType.SingleAnswer.name,
        multiple: String = QuestionType.MultipleAnswer.name,
        text: String = QuestionType.TextAnswer.name,
        trueFalse: String = QuestionType.TrueFalseAnswer.name
    ): List<QuestionWithAnswers>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<QuestionEntity>)
}