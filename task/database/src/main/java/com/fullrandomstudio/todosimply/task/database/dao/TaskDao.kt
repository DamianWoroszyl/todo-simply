package com.fullrandomstudio.todosimply.task.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fullrandomstudio.todosimply.task.database.entity.TaskEntity
import com.fullrandomstudio.todosimply.task.database.view.TaskFullView
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

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
        AND soft_deleted = 0
        ORDER BY datetime(schedule_date_time)
        """
    )
    fun get(startDateTime: String, endDateTime: String): Flow<List<TaskFullView>>

    @Query("""UPDATE task SET soft_deleted = :value WHERE id = :taskId""")
    fun setSoftDeleted(taskId: Long, value: Boolean): Int

    @Query("""SELECT name from task WHERE id = :taskId""")
    fun getName(taskId: Long): String

    @Query("""SELECT id FROM task WHERE soft_deleted = 1""")
    fun getSoftDeletedIds(): List<Long>

    @Query("""DELETE FROM task WHERE id IN (:ids)""")
    fun remove(ids: List<Long>)

    @Query("""UPDATE task SET finish_date_time_utc = :finishDateTimeUtc WHERE id = :taskId""")
    fun setTaskFinishDateTime(taskId: Long, finishDateTimeUtc: LocalDateTime?)
}
