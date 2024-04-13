package com.comunidadedevspace.taskbeats

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private lateinit var ctnContent: LinearLayout

    private var taskList = arrayListOf(
        Task(0, "title0", "desc0"),
        Task(1, "title1", "desc1")
    )

    private val adapter = TaskListAdapter(::onListItemClicked)

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
        if(result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val taskAction = data?.getSerializableExtra(TASK_ACTION_RESULT) as TaskAction
            val task: Task = taskAction.task

            if(taskAction.actionType == ActionType.DELETE.name) {
                val newList = arrayListOf<Task>()
                    .apply {
                        addAll(taskList)
                    }
                newList.remove(task)

                showMessage(ctnContent, "Item deletado ${task.Title}")
                if(newList.size == 0) {
                    ctnContent.visibility = View.VISIBLE
                }
                adapter.submitList(newList)

                taskList = newList
            } else if(taskAction.actionType == ActionType.CREATE.name) {
                val newList = arrayListOf<Task>()
                    .apply {
                        addAll(taskList)
                    }
                newList.add(task)

                showMessage(ctnContent, "Item adicionado ${task.Title}")
                adapter.submitList(newList)

                taskList = newList

            }

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)

        ctnContent = findViewById(R.id.ctn_content)

        adapter.submitList(taskList)

        val rvTaskList: RecyclerView = findViewById(R.id.rv_task_list)

        rvTaskList.adapter = adapter

        val fab: View = findViewById(R.id.fab_add)
        fab.setOnClickListener { view ->
            openTaskListDetail(null)
        }

    }

    private fun showMessage(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()
    }

    private fun onListItemClicked(task: Task) {
        openTaskListDetail(task)
    }

    private fun openTaskListDetail(task: Task? = null) {
        val intent = TaskDetailActivity.start(this, task)

        startForResult.launch(intent)
    }
}

enum class ActionType {
    DELETE,
    UPDATE,
    CREATE
}

data class TaskAction(val task: Task, val actionType: String): Serializable

const val TASK_ACTION_RESULT = "TASK_ACTION_RESULT"