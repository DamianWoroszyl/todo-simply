package com.fullrandomstudio.todosimply.util

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale


fun formatTimeLocalized(instant: Instant): String = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
    .withLocale(Locale.getDefault())
    .withZone(ZoneId.systemDefault())
    .format(instant)

fun formatTimeLocalized(zonedDateTime: ZonedDateTime): String =
    DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
        .withLocale(Locale.getDefault())
        .format(zonedDateTime)

fun formatDayNameAndDateLocalized(zonedDateTime: ZonedDateTime): String =
    DateTimeFormatter.ofPattern("EEEE").format(zonedDateTime).capitalize() + " " +
            DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
                .withLocale(Locale.getDefault())
                .format(zonedDateTime)

fun formatDateLocalized(zonedDateTime: ZonedDateTime): String =
    DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        .withLocale(Locale.getDefault())
        .format(zonedDateTime)


