package com.joaoferreira.data.database.converters

import androidx.room.TypeConverter
import com.joaoferreira.domain.utils.Categories

class Converters {
    @TypeConverter
    fun fromCategories(value: Categories?): String = value?.name ?: ""

    @TypeConverter
    fun toCategories(value: String): Categories? =
        if (value.isNotEmpty()) enumValueOf<Categories>(value) else null
}