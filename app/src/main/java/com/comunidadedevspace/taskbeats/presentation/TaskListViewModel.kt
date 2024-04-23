package com.comunidadedevspace.taskbeats.presentation

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comunidadedevspace.taskbeats.TaskBeatsApplication
import com.comunidadedevspace.taskbeats.data.Task
import com.comunidadedevspace.taskbeats.data.TaskDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskListViewModel(private val taskDao: TaskDao): ViewModel() {

    val taskListLiveData: LiveData<List<Task>> = taskDao.getAll()

    fun execute(taskAction: TaskAction) {
        when (taskAction.actionType) {
            ActionType.DELETE.name -> deleteByIdDb(taskAction.task!!.id)
            ActionType.CREATE.name -> insertIntoDb(taskAction.task!!)
            ActionType.UPDATE.name -> updateIntoDb(taskAction.task!!)
            ActionType.DELETE_ALL.name -> deleteAllDb()
        }
    }

    private fun deleteByIdDb(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.deleteTask(id)
        }
    }

    private fun insertIntoDb(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.insert(task)
        }
    }

    private fun updateIntoDb(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.update(task)
        }
    }

    private fun deleteAllDb() {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.deleteAll()
        }
    }


    companion object {
        fun create(application: Application): TaskListViewModel {
            val dataBaseInstance = (application as TaskBeatsApplication).getAppDataBase()
            val dao = dataBaseInstance.taskDao()
            return TaskListViewModel(dao)
        }
    }
}