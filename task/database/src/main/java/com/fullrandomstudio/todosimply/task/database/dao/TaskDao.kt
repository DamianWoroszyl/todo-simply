package com.fullrandomstudio.todosimply.task.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fullrandomstudio.todosimply.task.database.entity.TaskEntity
import com.fullrandomstudio.todosimply.task.database.view.TaskFullView
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: List<TaskEntity>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: TaskEntity): Long

    @Query("""SELECT * FROM task_full_view WHERE id = :taskId""")
    fun getFullView(taskId: Long): TaskFullView?

    /**
     * Dates must be formatted with ISO-8601 format. Preferably
     * DateTimeFormatter.ISO_OFFSET_DATE_TIME
     */
    @Query(
        """
        SELECT * 
        FROM task_full_view 
        WHERE datetime(schedule_date_time) BETWEEN datetime(:startDateTime) AND datetime(:endDateTime)
        """
    )
    fun get(startDateTime: String, endDateTime: String): Flow<List<TaskFullView>>

    @Query("""DELETE FROM task WHERE id = :id""")
    fun delete(id: Long)
}
