package com.example.mvvm_paperdb_retrofit.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvm_paperdb_retrofit.model.tasks.TaskLocalApi
import com.example.mvvm_paperdb_retrofit.model.tasks.TaskModel
import com.example.mvvm_paperdb_retrofit.model.tasks.TaskServerApi
import com.example.mvvm_paperdb_retrofit.repository.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel:ViewModel() {

    private var serverRepo = TaskRepository(TaskServerApi())
    private var localRepo = TaskRepository(TaskLocalApi())


    private var _tasks = MutableLiveData<List<TaskModel>>()
    val tasks:LiveData<List<TaskModel>> = _tasks

    private var _currentTask = MutableLiveData<TaskModel?>()
    val currentTask:LiveData<TaskModel?> = _currentTask


    var showActive:Boolean = true
        set(value){
            field = value
            _tasks.value = _tasks.value
        }
    init {
        _tasks.value = localRepo.getTasks()
        _currentTask.value = null

        CoroutineScope(Dispatchers.IO).launch {
            Log.e(TaskViewModel::class.java.simpleName, serverRepo.getTaskById("kasjdflkj").toString())
        }
        //Log.e(TaskViewModel::class.java.simpleName, serverRepo.getTaskById("1").toString())
    }

    suspend fun getTaskById():TaskModel{
        return serverRepo.getTaskById("1")
    }

    fun completeTask(id:String){
        localRepo.completeTask(id)
        _tasks.value = localRepo.getTasks()
    }
    fun addTask(task: TaskModel) {
        localRepo.addTask(task)
        _tasks.value = localRepo.getTasks()
    }
    fun setCurrentTask(task: TaskModel?){
        _currentTask.value = task
    }

    fun updateTask(task: TaskModel) {
        task.id = _currentTask.value?.id.toString()
        task.timeCreated = _currentTask.value?.timeCreated.toString()
        _currentTask.value = null
        localRepo.updateTask(task)
        _tasks.value = localRepo.getTasks()
    }

    fun deleteTask(id: String) {
        localRepo.deleteTask(id)
        _tasks.value = localRepo.getTasks()
    }
}