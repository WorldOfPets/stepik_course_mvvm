package com.example.mvvm_paperdb_retrofit.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvm_paperdb_retrofit.model.tasks.TaskLocalApi
import com.example.mvvm_paperdb_retrofit.model.tasks.TaskModel
import com.example.mvvm_paperdb_retrofit.model.tasks.TaskServerApi
import com.example.mvvm_paperdb_retrofit.repository.TaskRepository

class TaskViewModel:ViewModel() {

    private var serverRepo = TaskRepository(TaskServerApi())
    private var localRepo = TaskRepository(TaskLocalApi())

    private var _tasks = MutableLiveData<List<TaskModel>>()
    val tasks:LiveData<List<TaskModel>> = _tasks

    init {
        _tasks.value = localRepo.getTasks()
    }

    fun addTask(task: TaskModel) {
        localRepo.addTask(task)
        _tasks.value = localRepo.getTasks()
    }

    fun updateTask(task: TaskModel) {
        localRepo.updateTask(task)
        _tasks.value = localRepo.getTasks()
    }

    fun deleteTask(id: Int) {
        localRepo.deleteTask(id)
        _tasks.value = localRepo.getTasks()
    }
}