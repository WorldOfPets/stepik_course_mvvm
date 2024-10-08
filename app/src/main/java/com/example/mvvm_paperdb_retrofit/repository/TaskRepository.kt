package com.example.mvvm_paperdb_retrofit.repository

import com.example.mvvm_paperdb_retrofit.model.tasks.TaskInterface
import com.example.mvvm_paperdb_retrofit.model.tasks.TaskModel

class TaskRepository(private val taskInterface: TaskInterface) {
    fun getTaskById(id: String): TaskModel {
        return taskInterface.getTaskById(id)
    }

    fun getTasks(): List<TaskModel> {
        return taskInterface.getTasks()
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
}