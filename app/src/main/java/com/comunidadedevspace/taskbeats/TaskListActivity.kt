package com.comunidadedevspace.taskbeats

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private lateinit var ctnContent: LinearLayout

    private val adapter: TaskListAdapter by lazy {
        TaskListAdapter((::onListItemClicked))
    }


    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-taskbeat"
        ).build()
    }


    private val dao by lazy{
        database.taskDao()
    }

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val taskAction = data?.getSerializableExtra(TASK_ACTION_RESULT) as TaskAction
                val task: Task = taskAction.task

                if (taskAction.actionType == ActionType.DELETE.name) {
//                    val newList = arrayListOf<Task>()
//                        .apply {
//                            addAll(taskList)
//                        }
//                    newList.remove(task)
//
//                    showMessage(ctnContent, "Item deletado ${task.title}")
//                    if (newList.size == 0) {
//                        ctnContent.visibility = View.VISIBLE
//                    }
//                    adapter.submitList(newList)
//
//                    taskList = newList
                } else if (taskAction.actionType == ActionType.CREATE.name) {
                    insertIntoDb(task)

                } else if (taskAction.actionType == ActionType.UPDATE.name) {
                    updateIntoDb(task)
                }

            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)

        listFromDb()
        ctnContent = findViewById(R.id.ctn_content)

        val rvTaskList: RecyclerView = findViewById(R.id.rv_task_list)

        rvTaskList.adapter = adapter

        val fab: View = findViewById(R.id.fab_add)
        fab.setOnClickListener { view ->
            openTaskListDetail(null)
        }

    }

    private fun updateIntoDb(task: Task) {
        CoroutineScope(IO).launch {
            dao.update(task)
            listFromDb()
        }
    }

    private fun insertIntoDb(task: Task) {
        CoroutineScope(IO).launch {
            dao.insert(task)
            listFromDb()
        }
    }

    private fun listFromDb() {
        CoroutineScope(IO).launch {
            val dbList: List<Task> = dao.getAll()
            adapter.submitList(dbList)

//            if (dbList.isEmpty()) {
//                ctnContent.visibility = View.VISIBLE
//            } else {
//                ctnContent.visibility = View.GONE
//            }
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

data class TaskAction(val task: Task, val actionType: String) : Serializable

const val TASK_ACTION_RESULT = "TASK_ACTION_RESULT"