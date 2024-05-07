package com.comunidadedevspace.taskbeats

import com.comunidadedevspace.taskbeats.data.local.Task
import com.comunidadedevspace.taskbeats.data.local.TaskDao
import com.comunidadedevspace.taskbeats.presentation.ActionType
import com.comunidadedevspace.taskbeats.presentation.TaskAction
import com.comunidadedevspace.taskbeats.presentation.TaskDetailViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class TaskDetailViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val taskDao: TaskDao = mock()
    private val underTest: TaskDetailViewModel by lazy {
        TaskDetailViewModel(taskDao)
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