package com.example.mvvm_paperdb_retrofit.model.tasks

import com.example.mvvm_paperdb_retrofit.model.MyCustomCallback

interface TaskInterface {
    fun getTaskById(id:String, callback: MyCustomCallback<TaskModel>)
    fun getTasks(callback: MyCustomCallback<TaskModel>)
    fun addTask(task:TaskModel, callback: MyCustomCallback<TaskModel>)
    fun updateTask(task: TaskModel, callback: MyCustomCallback<TaskModel>)
    fun deleteTask(id:String, callback: MyCustomCallback<TaskModel>)
    fun completeTask(id: String, callback: MyCustomCallback<TaskModel>)
    fun syncData(list:List<TaskModel>, callback: MyCustomCallback<TaskModel>)
}