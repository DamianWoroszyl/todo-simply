package com.fullrandomstudio.todosimply.util

import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

fun formatTimeLocalized(instant: Instant): String =
    DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
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
    DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
        .withLocale(Locale.getDefault())
        .format(zonedDateTime)

fun formatDateWithDay(zonedDateTime: ZonedDateTime): String =
    DateTimeFormatter.ofPattern("EEE dd.MM.yyyy")
        .withLocale(Locale.getDefault())
        .format(zonedDateTime)

fun formatTimeLocalized(time: LocalTime): String =
    DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
        .withLocale(Locale.getDefault())
        .format(time)

fun formatDateLocalized(date: LocalDate): String =
    DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        .withLocale(Locale.getDefault())
        .format(date)
