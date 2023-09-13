package com.fullrandomstudio.task.model

import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

val UTC_ZONE_ID: ZoneId = ZoneId.of("UTC")

val ISO_ZONED_DATE_TIME_FORMATTER: DateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

fun ZonedDateTime.toUtcSameInstant(): ZonedDateTime = this.withZoneSameInstant(UTC_ZONE_ID)
