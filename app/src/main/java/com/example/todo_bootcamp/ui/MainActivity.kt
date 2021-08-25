package com.example.todo_bootcamp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todo_bootcamp.databinding.ActivityMainBinding
import com.example.todo_bootcamp.datasource.TaskDataSource

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { TaskAdapter() } // o lay vau esperar a variavel ser chamada para instanciar o adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        insertListeners()
    }

    private fun insertListeners() {
        binding.fab.setOnClickListener {
            startActivityForResult(Intent(this,AddTaskActivity::class.java), CREATE_NEW_TASK)
        }

        adapter.listenerEdit = {

        }

        adapter.listenerDelete = {

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE_NEW_TASK) {
            binding.rvTasks.adapter = adapter
            adapter.submitList(TaskDataSource.getList())
        }
    }

    companion object {
        private const val CREATE_NEW_TASK = 1000
    }

}