package com.fullrandomstudio.todosimply.task.domain

import com.fullrandomstudio.task.model.TaskAlarm
import com.fullrandomstudio.todosimply.task.data.repository.TaskRepository
import javax.inject.Inject

class SetTaskAlarmUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {

    suspend operator fun invoke(taskId:Long, taskAlarm: TaskAlarm?) {
        taskRepository.setTaskAlarm(taskId, taskAlarm)
    }
}
