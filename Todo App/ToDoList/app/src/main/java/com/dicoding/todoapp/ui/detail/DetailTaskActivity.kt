package com.dicoding.todoapp.ui.detail

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.todoapp.R
import com.dicoding.todoapp.ui.ViewModelFactory
import com.dicoding.todoapp.ui.list.TaskAdapter
import com.dicoding.todoapp.utils.DateConverter
import com.dicoding.todoapp.utils.TASK_ID

class DetailTaskActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailTaskViewModel
    private lateinit var detailEdTitle: TextView
    private lateinit var detailEdDescription: TextView
    private lateinit var detailEdDueDate: TextView
    private lateinit var btnDeleteTask: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        //TODO 11 : Show detail task and implement delete action

        detailEdTitle = findViewById(R.id.detail_ed_title)
        detailEdDescription = findViewById(R.id.detail_ed_description)
        detailEdDueDate = findViewById(R.id.detail_ed_due_date)
        btnDeleteTask = findViewById(R.id.btn_delete_task)

        val id = intent.getIntExtra(TASK_ID, 0)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailTaskViewModel::class.java]

        viewModel.setTaskId(id!!.toInt())
        viewModel.task.observe(this) { task ->
            if (task != null) {
                detailEdTitle.text = task.title
                detailEdDescription.text = task.description
                detailEdDueDate.text = DateConverter.convertMillisToString(task.dueDateMillis)

                btnDeleteTask.setOnClickListener {
                    viewModel.deleteTask()
                    finish()
                }
            }
        }
    }
}