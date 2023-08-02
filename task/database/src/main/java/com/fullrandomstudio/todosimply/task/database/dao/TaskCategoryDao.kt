package com.fullrandomstudio.todosimply.task.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fullrandomstudio.todosimply.task.database.entity.TaskCategoryEntity

@Dao
interface TaskCategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: List<TaskCategoryEntity>)

    @Query("SELECT COUNT() FROM task_category LIMIT 1")
    suspend fun anyExists(): Boolean

    @Query("SELECT * FROM task_category WHERE is_default = 1")
    suspend fun getDefault(): TaskCategoryEntity?

    @Query("""SELECT * FROM task_category""")
    suspend fun getAll(): List<TaskCategoryEntity>
}
