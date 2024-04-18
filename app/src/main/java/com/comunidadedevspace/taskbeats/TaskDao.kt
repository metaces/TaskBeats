package com.comunidadedevspace.taskbeats

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(task: Task)
    @Query("SELECT * FROM task")
    fun getAll(): List<Task>
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(task: Task)
}