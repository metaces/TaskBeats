package com.comunidadedevspace.taskbeats.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.comunidadedevspace.taskbeats.R
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomAppBar = findViewById<BottomAppBar>(R.id.bottomAppBar)
        val floatActionButton = findViewById<FloatingActionButton>(R.id.floatActionButton)

        val taskListFragment = TaskListFragment.newInstance()
        val newsListFragment = NewsListFragment.newInstance()

        supportFragmentManager.commit {
            replace(R.id.fragment_container_view, taskListFragment)
            setReorderingAllowed(true)

        }

        floatActionButton.setOnClickListener {
            openTaskListDetail()
        }

        bottomAppBar.setOnMenuItemClickListener { menuItem ->
            val fragment = when(menuItem.itemId) {
                R.id.task_list -> taskListFragment
                R.id.news_list -> newsListFragment
                else -> taskListFragment
            }

            supportFragmentManager.commit {
                replace(R.id.fragment_container_view, fragment)
                setReorderingAllowed(true)

            }

            true
        }

    }

    private fun openTaskListDetail() {
        val intent = TaskDetailActivity.start(this, null)
        startActivity(intent)
    }
}