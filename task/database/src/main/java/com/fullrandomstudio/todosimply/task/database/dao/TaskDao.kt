package com.fullrandomstudio.todosimply.task.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fullrandomstudio.todosimply.task.database.entity.TaskEntity
import com.fullrandomstudio.todosimply.task.database.view.TaskFullView

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: List<TaskEntity>)

    @Query("""SELECT * FROM task_full_view WHERE id = :taskId""")
    fun getFullView(taskId: Long): TaskFullView?
}
