package com.fullrandomstudio.todosimply.util

import java.util.Locale

fun String.capitalize(): String {
    return replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
}
