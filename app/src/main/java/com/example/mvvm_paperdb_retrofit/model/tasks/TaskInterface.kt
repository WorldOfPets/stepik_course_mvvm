package com.example.mvvm_paperdb_retrofit.model.tasks

interface TaskInterface {
    suspend fun getTaskById(id:String): TaskModel
    fun getTasks():List<TaskModel>
    fun addTask(task:TaskModel):Boolean
    fun updateTask(task: TaskModel):Boolean
    fun deleteTask(id:String):Boolean
    fun completeTask(id: String):Boolean
}