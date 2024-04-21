package com.comunidadedevspace.taskbeats.presentation

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.TaskBeatsApplication
import com.comunidadedevspace.taskbeats.data.AppDatabase
import com.comunidadedevspace.taskbeats.data.Task
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

    private val viewModel: TaskListViewModel by lazy {
        TaskListViewModel.create(application)
    }

    lateinit var database: AppDatabase

    private val dao by lazy{
        database.taskDao()
    }

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val taskAction = data?.getSerializableExtra(TASK_ACTION_RESULT) as TaskAction
                val task: Task = taskAction.task

                when (taskAction.actionType) {
                    ActionType.DELETE.name -> deleteByIdDb(task.id)
                    ActionType.CREATE.name -> insertIntoDb(task)
                    ActionType.UPDATE.name -> updateIntoDb(task)
                }

            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)
        setSupportActionBar(findViewById(R.id.toolbar))

        ctnContent = findViewById(R.id.ctn_content)

        val rvTaskList: RecyclerView = findViewById(R.id.rv_task_list)

        rvTaskList.adapter = adapter

        val fab: View = findViewById(R.id.fab_add)
        fab.setOnClickListener { view ->
            openTaskListDetail(null)
        }

    }

    override fun onStart() {
        super.onStart()

        database = (application as TaskBeatsApplication).getAppDataBase()

        listFromDb()
        
    }

    private fun deleteByIdDb(id: Int) {
        CoroutineScope(IO).launch {
            dao.deleteTask(id)
            listFromDb()
        }
    }

    private fun deleteAllDb() {
        CoroutineScope(IO).launch {
            dao.deleteAll()
            listFromDb()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_task_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection.
        return when (item.itemId) {
            R.id.delete_all_task -> {
                deleteAllDb()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}

enum class ActionType {
    DELETE,
    UPDATE,
    CREATE
}

data class TaskAction(val task: Task, val actionType: String) : Serializable

const val TASK_ACTION_RESULT = "TASK_ACTION_RESULT"