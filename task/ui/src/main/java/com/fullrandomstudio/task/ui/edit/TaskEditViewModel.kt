package com.fullrandomstudio.task.ui.edit

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskEditViewModel @Inject constructor() : ViewModel() {

    val taskName: MutableState<String> = mutableStateOf("")
    val taskDescription: MutableState<String> = mutableStateOf("")

    fun onNameChange(value: String) {
        taskName.value = value
    }

    fun onDescriptionChange(value: String) {
        taskDescription.value = value
    }
}
