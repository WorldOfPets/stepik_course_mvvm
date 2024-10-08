package com.example.mvvm_paperdb_retrofit.model.tasks

import android.util.Log
import com.example.mvvm_paperdb_retrofit.retrofit.RetrofitService
import com.example.mvvm_paperdb_retrofit.retrofit.TaskServerInterface

class TaskServerApi :TaskInterface {
    private val service = RetrofitService.retrofit.create(TaskServerInterface::class.java)
    override fun getTaskById(id: String): TaskModel {
        try {
            var taskModel = TaskModel()
            var response = service.getTaskById(id).execute()
            taskModel = response.body() ?: TaskModel()
            Log.e(TaskServerApi::class.java.simpleName, response.toString())

//            service.getTaskById(id).enqueue(object : Callback<TaskModel>{
//                override fun onResponse(p0: Call<TaskModel>, p1: Response<TaskModel>) {
//                    Log.e(TaskServerApi::class.java.simpleName, "success")
//
//                    taskModel = p1.body() ?: TaskModel()
//                }
//
//                override fun onFailure(p0: Call<TaskModel>, p1: Throwable) {
//                    Log.e(TaskServerApi::class.java.simpleName, p1.toString())
//                }
//
//            })
            return taskModel
        }catch (ex:Exception){
            Log.e(TaskServerApi::class.java.simpleName, ex.toString())
            return TaskModel()
        }
    }

    override fun getTasks(): List<TaskModel> {
        TODO("Not yet implemented")
    }

    override fun addTask(task: TaskModel): Boolean {
        TODO("Not yet implemented")
    }

    override fun updateTask(task: TaskModel): Boolean {
        TODO("Not yet implemented")
    }

    override fun deleteTask(id: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun completeTask(id: String): Boolean {
        TODO("Not yet implemented")
    }
}