package com.fullrandomstudio.todosimply.task.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fullrandomstudio.todosimply.task.database.entity.TaskCategoryEntity

@Dao
interface TaskCategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(map: List<TaskCategoryEntity>)

    @Query("SELECT COUNT() FROM task_category LIMIT 1")
    suspend fun anyExists(): Boolean
}
