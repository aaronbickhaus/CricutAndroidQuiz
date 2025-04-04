package com.cricut.data.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * [Converters]
 *
 * used to convert items from/to models in Room
 */
class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromIntList(value: List<Int>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toIntList(value: String): List<Int> {
        return if (value.trim().startsWith("[")) {
            val listType = object : TypeToken<List<Int>>() {}.type
            gson.fromJson(value, listType)
        } else {
            value.split(",")
                .mapNotNull { it.trim().toIntOrNull() }
        }
    }

    @TypeConverter
    fun fromStringList(list: List<String>?): String? {
        return if (list == null) null else gson.toJson(list)
    }

    @TypeConverter
    fun toStringList(data: String?): List<String>? {
        if (data == null) return null
        return if (data.trim().startsWith("[")) {
            val listType = object : TypeToken<List<String>>() {}.type
            gson.fromJson(data, listType)
        } else {
            data.split(",")
                .map { it.trim() }
        }
    }
}
