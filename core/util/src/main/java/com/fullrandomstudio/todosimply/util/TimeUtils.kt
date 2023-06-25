package com.fullrandomstudio.todosimply.util

import kotlin.time.DurationUnit
import kotlin.time.toDuration

fun Long.durationDayToMs() = toDuration(DurationUnit.DAYS).inWholeMilliseconds
