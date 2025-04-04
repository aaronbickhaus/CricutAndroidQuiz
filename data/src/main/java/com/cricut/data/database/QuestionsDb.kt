package com.cricut.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cricut.data.converters.Converters
import com.cricut.data.converters.EnumConverters
import com.cricut.data.dao.QuestionsDao
import com.cricut.data.entity.AnswerEntity
import com.cricut.data.entity.QuestionEntity

/**
 * [QuestionsDb]
 *
 * DB for quiz questions
 */
@Database(entities = [QuestionEntity::class, AnswerEntity::class], version = 1)
@TypeConverters(Converters::class, EnumConverters::class)
abstract class QuestionsDb : RoomDatabase() {
    abstract fun questionDao(): QuestionsDao
}