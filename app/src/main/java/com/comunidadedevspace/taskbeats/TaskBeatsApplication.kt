package com.comunidadedevspace.taskbeats

import android.app.Application
import androidx.room.Room
import com.comunidadedevspace.taskbeats.data.AppDatabase

class TaskBeatsApplication: Application() {

    private lateinit var database: AppDatabase
    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-taskbeat"
        ).build()
    }

    fun getAppDataBase(): AppDatabase {
        return database
    }
}