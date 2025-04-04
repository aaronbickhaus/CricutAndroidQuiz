package com.cricut.data.converters

import androidx.room.TypeConverter
import com.cricut.common.model.QuestionType

/**
 * [Converters]
 *
 * used to convert items from/to models in Room
 */
class EnumConverters {
    @TypeConverter
    fun fromQuestionType(questionType: QuestionType): String {
        return questionType.name
    }

    @TypeConverter
    fun toQuestionType(value: String): QuestionType {
        return QuestionType.valueOf(value)
    }
}