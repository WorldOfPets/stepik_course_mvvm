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

    private var _notifyMsg = MutableLiveData<String>()
    val notifyMsg:LiveData<String> = _notifyMsg

    private var isConnected:Boolean = false
        get(){
            return RetrofitService.checkInternetConnection()
        }
    var showActive:Boolean = true
        set(value){
            field = value
            _tasks.value = _tasks.value
        }
    init {
        if (isConnected){
            serverRepo.getTasks(this@TaskViewModel)
        }else{
            localRepo.getTasks(this@TaskViewModel)
        }
        _currentTask.value = null
    }

    fun completeTask(id:String){
        if (isConnected){
            serverRepo.completeTask(id, this)
            localRepo.completeTask(id, this)
        }else{
            localRepo.completeTask(id, this)
        }

    }
    fun addTask(task: TaskModel) {
        if (isConnected){
            serverRepo.addTask(task, this)
            localRepo.addTask(task, this)
        }else{
            _notifyMsg.value = "NO INTERNET CONNECTION"
        }

    }
    fun setCurrentTask(task: TaskModel?){
        _currentTask.value = task
    }

    fun updateTask(task: TaskModel) {
        task.id = _currentTask.value?.id.toString()
        task.timeCreated = _currentTask.value?.timeCreated.toString()
        _currentTask.value = null
        if (isConnected){
            serverRepo.updateTask(task, this)
            localRepo.updateTask(task, this)
        }else{
            _notifyMsg.value = "NO INTERNET CONNECTION"
        }
    }

    fun deleteTask(id: String) {
        if (isConnected){
            serverRepo.deleteTask(id, this)
            localRepo.deleteTask(id, this)
        }else{
            _notifyMsg.value = "NO INTERNET CONNECTION"
        }
    }
    fun syncData(){
        if (isConnected){
            localRepo.syncData( this)
        }
    }

    override fun onSuccess(model: TaskModel) {
        if (isConnected){
            serverRepo.getTasks(this)
        }else{
            localRepo.getTasks(this)
        }
    }

    override fun onSuccess(listModel: List<TaskModel>) {
        _tasks.value = listModel
    }

    override fun onFailure(exception: String) {
        Log.e(TaskViewModel::class.java.simpleName, exception)
    }

    override fun notify(msg: String, update:Boolean) {
        _notifyMsg.value = msg
    }
}