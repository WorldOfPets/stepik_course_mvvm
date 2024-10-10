package com.example.mvvm_paperdb_retrofit.model.tasks

import com.example.mvvm_paperdb_retrofit.model.MyCustomCallback

interface TaskInterface {
    fun getTaskById(id:String, callback: MyCustomCallback<TaskModel>)
    fun getTasks(callback: MyCustomCallback<TaskModel>)
    fun addTask(task:TaskModel):Boolean
    fun updateTask(task: TaskModel):Boolean
    fun deleteTask(id:String):Boolean
    fun completeTask(id: String):Boolean
    fun syncData(list:List<TaskModel>)
}