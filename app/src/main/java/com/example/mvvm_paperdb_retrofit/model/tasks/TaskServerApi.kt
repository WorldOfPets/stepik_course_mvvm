package com.example.mvvm_paperdb_retrofit.model.tasks

class TaskServerApi:TaskInterface {
    override fun getTaskById(id: Int): TaskModel {
        TODO("Not yet implemented")
    }

    override fun getTasks(): List<TaskModel> {
        TODO("Not yet implemented")
    }

    override fun addTask(task: TaskModel): Boolean {
        TODO("Not yet implemented")
    }

    override fun updateTask(task: TaskModel):Boolean {
        TODO("Not yet implemented")
    }

    override fun deleteTask(id: Int): Boolean {
        TODO("Not yet implemented")
    }
}