package com.example.mvvm_paperdb_retrofit.repository

import com.example.mvvm_paperdb_retrofit.model.MyCustomCallback
import com.example.mvvm_paperdb_retrofit.model.tasks.TaskInterface
import com.example.mvvm_paperdb_retrofit.model.tasks.TaskModel

class TaskRepository(private val taskInterface: TaskInterface) {
    fun getTaskById(id: String, callback: MyCustomCallback<TaskModel>) {
        taskInterface.getTaskById(id, callback)
    }

    fun getTasks(callback: MyCustomCallback<TaskModel>){
        taskInterface.getTasks(callback)
    }

    fun addTask(task: TaskModel): Boolean {
        return  taskInterface.addTask(task)
    }

    fun updateTask(task: TaskModel):Boolean {
        return taskInterface.updateTask(task)
    }

    fun deleteTask(id: String): Boolean {
        return taskInterface.deleteTask(id)
    }

    fun completeTask(id:String):Boolean{
        return taskInterface.completeTask(id)
    }
    fun syncData(list: List<TaskModel>){
        taskInterface.syncData(list)
    }
}