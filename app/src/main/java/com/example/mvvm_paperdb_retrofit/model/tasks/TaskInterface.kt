package com.example.mvvm_paperdb_retrofit.model.tasks

interface TaskInterface {
    fun getTaskById(id:Int):TaskModel
    fun getTasks():List<TaskModel>
    fun addTask(task:TaskModel):Boolean
    fun updateTask(task: TaskModel):Boolean
    fun deleteTask(id:Int):Boolean
}