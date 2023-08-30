package com.fullrandomstudio.todosimply.task.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fullrandomstudio.todosimply.task.database.entity.TaskAlarmEntity

@Dao
interface TaskAlarmDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: TaskAlarmEntity): Long

    @Query("""DELETE FROM task_alarm WHERE task_id = :taskId""")
    fun deleteForTask(taskId: Long)
}
