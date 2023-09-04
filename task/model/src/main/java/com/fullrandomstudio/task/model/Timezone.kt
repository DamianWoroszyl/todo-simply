package com.fullrandomstudio.task.model

import java.time.ZoneId
import java.time.format.DateTimeFormatter

val UTC_ZONE_ID: ZoneId = ZoneId.of("UTC")

val ISO_ZONED_DATE_TIME_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME
