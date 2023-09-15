package com.fullrandomstudio.core.ui.toast

import android.content.Context
import android.widget.Toast

fun showToast(
    context: Context,
    text: String,
    length: Int = Toast.LENGTH_SHORT
) {
    Toast.makeText(
        context,
        text,
        length
    ).show()
}
