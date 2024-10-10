package com.example.mvvm_paperdb_retrofit.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvm_paperdb_retrofit.model.MyCustomCallback
import com.example.mvvm_paperdb_retrofit.model.tasks.TaskLocalApi
import com.example.mvvm_paperdb_retrofit.model.tasks.TaskModel
import com.example.mvvm_paperdb_retrofit.model.tasks.TaskServerApi
import com.example.mvvm_paperdb_retrofit.repository.TaskRepository
import com.example.mvvm_paperdb_retrofit.retrofit.RetrofitService

class TaskViewModel:ViewModel(),
    MyCustomCallback<TaskModel>{

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
        localRepo.getTasks(this@TaskViewModel)
        Log.e(TaskViewModel::class.java.simpleName, RetrofitService.checkInternetConnection().toString())
        serverRepo.getTasks(this@TaskViewModel)
        _currentTask.value = null

        serverRepo.getTaskById("1", this@TaskViewModel)
        //Log.e(TaskViewModel::class.java.simpleName, serverRepo.getTaskById("1").toString())
    }

    fun completeTask(id:String){
        localRepo.completeTask(id)
        localRepo.getTasks(this)
    }
    fun addTask(task: TaskModel) {
        localRepo.addTask(task)
        localRepo.getTasks(this)
    }
    fun setCurrentTask(task: TaskModel?){
        _currentTask.value = task
    }

    fun updateTask(task: TaskModel) {
        task.id = _currentTask.value?.id.toString()
        task.timeCreated = _currentTask.value?.timeCreated.toString()
        _currentTask.value = null
        localRepo.updateTask(task)
        localRepo.getTasks(this@TaskViewModel)
    }

    fun deleteTask(id: String) {
        localRepo.deleteTask(id)
        localRepo.getTasks(this@TaskViewModel)
    }

    override fun onSuccess(model: TaskModel) {
        Log.e(TaskViewModel::class.java.simpleName, model.toString())
    }

    override fun onSuccess(listModel: List<TaskModel>) {
        _tasks.postValue(listModel.distinctBy { it.id })
    }

    override fun onFailure(exception: String) {
        Log.e(TaskViewModel::class.java.simpleName, exception)
    }
}