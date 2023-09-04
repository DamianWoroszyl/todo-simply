package com.fullrandomstudio.todosimply.common.resources

import android.content.Context
import androidx.annotation.ArrayRes
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface ResourceProvider {
    fun getString(@StringRes resId: Int): String
    fun getColor(@ColorRes resId: Int): Int
    fun getIntArray(@ArrayRes resId: Int): IntArray
}

class DefaultResourceProvider @Inject constructor(
    @ApplicationContext val context: Context
) : ResourceProvider {

    override fun getString(resId: Int): String {
        return context.getString(resId)
    }

    override fun getColor(resId: Int): Int {
        return context.getColor(resId)
    }

    override fun getIntArray(resId: Int): IntArray {
        return context.resources.getIntArray(resId)
    }
}
