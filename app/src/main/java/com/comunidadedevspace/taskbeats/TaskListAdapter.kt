package com.comunidadedevspace.taskbeats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskListAdapter(
    private val listTask: List<Task>,
    private val openTaskDetailView:(task: Task) -> Unit
    ):
    RecyclerView.Adapter<TaskListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listTask.size
    }

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        val task = listTask[position]
        holder.bind(task, openTaskDetailView)
    }

}

class TaskListViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

    private val tvTitle = view.findViewById<TextView>(R.id.tv_task_title)
    private val tvDescription = view.findViewById<TextView>(R.id.tv_task_desc)

    fun bind(
        task: Task,
        openTaskDetailView:(task: Task) -> Unit
    ) {
        tvTitle.text = task.Title
        tvDescription.text = task.Description

        view.setOnClickListener { openTaskDetailView.invoke(task) }
    }
}