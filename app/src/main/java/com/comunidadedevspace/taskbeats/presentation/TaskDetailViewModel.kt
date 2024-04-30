package com.comunidadedevspace.taskbeats.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.comunidadedevspace.taskbeats.TaskBeatsApplication
import com.comunidadedevspace.taskbeats.data.Task
import com.comunidadedevspace.taskbeats.data.TaskDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskDetailViewModel(
    private val taskDao: TaskDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO): ViewModel() {

    fun execute(taskAction: TaskAction) {
        when (taskAction.actionType) {
            ActionType.DELETE.name -> deleteByIdDb(taskAction.task!!.id)
            ActionType.CREATE.name -> insertIntoDb(taskAction.task!!)
            ActionType.UPDATE.name -> updateIntoDb(taskAction.task!!)
//            ActionType.DELETE_ALL.name -> deleteAllDb()
        }
    }

    private fun deleteByIdDb(id: Int) {
        viewModelScope.launch(dispatcher) {
            taskDao.deleteTask(id)
        }
    }

    private fun insertIntoDb(task: Task) {
        viewModelScope.launch(dispatcher) {
            taskDao.insert(task)
        }
    }

    private fun updateIntoDb(task: Task) {
        viewModelScope.launch(dispatcher) {
            taskDao.update(task)
        }
    }

//    private fun deleteAllDb() {
//        viewModelScope.launch(Dispatchers.IO) {
//            taskDao.deleteAll()
//        }
//    }

    companion object {

        fun getVmFactory(application: Application): ViewModelProvider.Factory {
            val dataBaseInstance = (application as TaskBeatsApplication).getAppDataBase()
            val dao = dataBaseInstance.taskDao()
            val factory = object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return TaskDetailViewModel(dao) as T
                }
            }

            return factory
        }
    }
}