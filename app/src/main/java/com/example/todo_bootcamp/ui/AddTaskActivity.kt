package com.example.todo_bootcamp.ui

import android.app.Activity
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.StateSet.TAG
import com.example.todo_bootcamp.databinding.ActivityAddTaskBinding
import com.example.todo_bootcamp.datasource.TaskDataSource
import com.example.todo_bootcamp.extensions.format
import com.example.todo_bootcamp.extensions.text
import com.example.todo_bootcamp.model.Task
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.hasExtra(TASK_ID)){
            val taskId = intent.getIntExtra(TASK_ID, 0)
            TaskDataSource.findById(taskId)?.let {
                binding.tilTitle.text = it.title
                binding.tilDescription.text = it.description
                binding.tilDate.text = it.date
                binding.tilHour.text = it.hour
            }
        }
        insertListeners()
    }

    private fun insertListeners() {
        binding.tilDate.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offSet = timeZone.getOffset(Date().time) * -1
                binding.tilDate.text = Date(it + offSet).format()
            }
            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
        }

        binding.tilHour.editText?.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()
            timePicker.addOnPositiveButtonClickListener{
                val minute = if(timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute
                val hour = if(timePicker.hour in 0..9) "0${timePicker.hour}" else timePicker.hour

                binding.tilHour.text = "$hour:$minute"
            }
            timePicker.show(supportFragmentManager, null)
        }

        binding.btnTaskCancel.setOnClickListener {
            finish()
        }

        binding.btnCreateTask.setOnClickListener {
            val title = binding.tilTitle.text.toString()
            val date = binding.tilDate.text.toString()
            if (title.isNotEmpty() && date.isNotEmpty()) {
                val task = Task(
                    title = binding.tilTitle.text,
                    date = binding.tilDate.text,
                    hour = binding.tilHour.text,
                    description = binding.tilDescription.text,
                    id = intent.getIntExtra(TASK_ID, 0)
                )
                TaskDataSource.insertTask(task)
                setResult(Activity.RESULT_OK)
                finish()
            } else {
                Toast.makeText(applicationContext, "Preencha os campos necessarios", Toast.LENGTH_SHORT).show()
                binding.tilTitle.error = "Preencher"
                binding.tilDate.error = "Preencher"
            }
        }
    }

    companion object {
        const val TASK_ID = "task_id"
    }
}