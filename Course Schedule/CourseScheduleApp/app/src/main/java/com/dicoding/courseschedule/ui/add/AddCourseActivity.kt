package com.dicoding.courseschedule.ui.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.data.DataRepository
import com.dicoding.courseschedule.util.TimePickerFragment

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {

    private lateinit var viewModel: AddCourseViewModel
    private lateinit var repository: DataRepository

    // initiate ui
    private lateinit var edCourseName: EditText
    private lateinit var spinnerDaya: Spinner
    private lateinit var ibStartTime: ImageButton
    private lateinit var ibEndTime: ImageButton
    private lateinit var tvStartTime: TextView
    private lateinit var tvEndTime: TextView
    private lateinit var edLecturer: EditText
    private lateinit var edNote: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)
        supportActionBar?.title = resources.getString(R.string.add_course)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        repository = DataRepository.getInstance(this)!!
        viewModel = AddCourseViewModel(repository)

        initiateUi()

        ibStartTime.setOnClickListener {
            showTimePicker(it)
        }
        ibEndTime.setOnClickListener {
            showTimePicker(it)
        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_insert -> {
                viewModel.insertCourse(
                    day = spinnerDaya.selectedItemPosition,
                    startTime = tvStartTime.text.toString(),
                    endTime = tvEndTime.text.toString(),
                    courseName = edCourseName.text.toString(),
                    lecturer = edLecturer.text.toString(),
                    note = edNote.text.toString()
                )
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun initiateUi() {
        edCourseName = findViewById(R.id.ed_course_name)
        spinnerDaya = findViewById(R.id.spinner_day)
        ibStartTime = findViewById(R.id.ib_start_time)
        ibEndTime = findViewById(R.id.ib_end_time)
        tvStartTime = findViewById(R.id.tv_start_time)
        tvEndTime = findViewById(R.id.tv_end_time)
        edLecturer = findViewById(R.id.ed_lecturer)
        edNote = findViewById(R.id.ed_note)
    }

    private fun showTimePicker(v: View) {
        val timePickerFragment = TimePickerFragment()
        when (v.id) {
            R.id.ib_start_time -> {
                timePickerFragment.show(supportFragmentManager, "startTime")
            }

            R.id.ib_end_time -> {
                timePickerFragment.show(supportFragmentManager, "endTime")
            }
        }
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val timeFormat = String.format("%02d:%02d", hour, minute)
        when (tag) {
            "startTime" -> {
                tvStartTime.text = timeFormat
            }

            "endTime" -> {
                tvEndTime.text = timeFormat
            }
        }
    }
}