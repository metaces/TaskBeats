package com.comunidadedevspace.taskbeats

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val taskList: List<Task>  = listOf(
            Task("title0", "desc0"),
            Task("title1", "desc1")
        )
        val adapter = TaskListAdapter(taskList, ::openTaskDetailView)
        val rvTaskList: RecyclerView = findViewById(R.id.rv_task_list)

        rvTaskList.adapter = adapter
    }

    private fun openTaskDetailView(task: Task) {
        val intent = Intent(this, TaskDetailActivity::class.java)
            .apply {
                putExtra(TaskDetailActivity.TASK_TITLE_EXTRA, task.Title)
            }
        startActivity(intent)
    }
}