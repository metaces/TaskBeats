package com.comunidadedevspace.taskbeats

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private val taskList = arrayListOf(
        Task(0, "title0", "desc0"),
        Task(1, "title1", "desc1")
    )

    private val adapter = TaskListAdapter(::openTaskDetailView)

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
        if(result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val taskAction = data?.getSerializableExtra(TASK_ACTION_RESULT) as TaskAction
            val task: Task = taskAction.task
            taskList.remove(task)
            adapter.submit(taskList)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter.submit(taskList)

        val rvTaskList: RecyclerView = findViewById(R.id.rv_task_list)

        rvTaskList.adapter = adapter
    }

    private fun openTaskDetailView(task: Task) {
        val intent = TaskDetailActivity.start(this, task)

        startForResult.launch(intent)
    }
}

sealed class ActionType : Serializable {
    object DELETE: ActionType()
    object UPDATE: ActionType()
    object CREATE: ActionType()
}

data class TaskAction(val task: Task, val actionType: ActionType): Serializable

const val TASK_ACTION_RESULT = "TASK_ACTION_RESULT"