@file:Suppress("Filename", "MatchingDeclarationName")

package com.fullrandomstudio.todosimply.task.database.time

import androidx.room.TypeConverter
import java.time.ZonedDateTime

class ZonedDateTimeConverter {
    @TypeConverter
    fun fromString(value: String?): ZonedDateTime? {
        return value?.let { ZonedDateTime.parse(it) }
    }

    @TypeConverter
    fun toString(date: ZonedDateTime?): String? {
        return date?.toString()
    }
}
