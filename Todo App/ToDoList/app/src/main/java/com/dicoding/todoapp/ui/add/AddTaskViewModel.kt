package com.dicoding.todoapp.ui.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.todoapp.data.Task
import com.dicoding.todoapp.data.TaskRepository
import kotlinx.coroutines.launch

class AddTaskViewModel(private val taskRepository: TaskRepository) : ViewModel() {

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    fun insertTask(title: String, description: String, dueTime: Long) {
        viewModelScope.launch {
            val task = Task(title = title, description = description, dueDateMillis = dueTime)
            taskRepository.insertTask(task)
            _isSuccess.value = true
        }
    }


}