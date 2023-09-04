package com.fullrandomstudio.todosimply.task.database.time

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

private val isoZonedDateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

class ZonedDateTimeConverter {
    @TypeConverter
    fun fromString(value: String?): ZonedDateTime? {
        return value?.let { ZonedDateTime.parse(it, isoZonedDateTimeFormatter) }
    }

    @TypeConverter
    fun toString(value: ZonedDateTime?): String? {
        return value?.format(isoZonedDateTimeFormatter)
    }
}

class LocalDateTimeConverter {
    @TypeConverter
    fun fromString(value: String?): LocalDateTime? {
        return value?.let { LocalDateTime.parse(it) }
    }

    @TypeConverter
    fun toString(value: LocalDateTime?): String? {
        return value?.toString()
    }
}

class ZoneIdConverter {
    @TypeConverter
    fun fromString(value: String?): ZoneId? {
        return value?.let { ZoneId.of(it) }
    }

    @TypeConverter
    fun toString(value: ZoneId?): String? {
        return value?.toString()
    }
}
