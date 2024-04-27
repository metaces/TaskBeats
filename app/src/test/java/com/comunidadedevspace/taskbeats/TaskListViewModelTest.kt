package com.comunidadedevspace.taskbeats

import com.comunidadedevspace.taskbeats.data.Task
import com.comunidadedevspace.taskbeats.data.TaskDao
import com.comunidadedevspace.taskbeats.presentation.ActionType
import com.comunidadedevspace.taskbeats.presentation.TaskAction
import com.comunidadedevspace.taskbeats.presentation.TaskListViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class TaskListViewModelTest {

    private val taskDao: TaskDao = mock()
    private val underTest: TaskListViewModel by lazy {
        TaskListViewModel(taskDao)
    }

    // testes cases Delete_all

    @Test
    fun delete_all() = runTest(){
        //given
        val taskAction = TaskAction(
            task = null,
            actionType = ActionType.DELETE_ALL.name
        )
        //when
        underTest.execute(taskAction)
        //then
        verify(taskDao).deleteAll()
    }

    @Test
    fun create_task() = runTest(){
        val task = Task(
            id = 1,
            title = "title",
            description = "desc"
        )
        //given
        val taskAction = TaskAction(
            task = task,
            actionType = ActionType.CREATE.name
        )
        //when
        underTest.execute(taskAction)
        //then
        verify(taskDao).insert(task)
    }

    @Test
    fun update_task() = runTest(){
        val task = Task(
            id = 1,
            title = "title1",
            description = "desc1"
        )
        //given
        val taskAction = TaskAction(
            task = task,
            actionType = ActionType.UPDATE.name
        )
        //when
        underTest.execute(taskAction)
        //then
        verify(taskDao).update(task)
    }

    @Test
    fun delete_task() = runTest(){
        val task = Task(
            id = 1,
            title = "title",
            description = "desc"
        )
        //given
        val taskAction = TaskAction(
            task = task,
            actionType = ActionType.DELETE.name
        )
        //when
        underTest.execute(taskAction)
        //then
        verify(taskDao).deleteTask(task.id)
    }
}