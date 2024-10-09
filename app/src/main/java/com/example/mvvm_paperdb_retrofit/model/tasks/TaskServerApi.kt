package com.example.mvvm_paperdb_retrofit.model.tasks

import android.util.Log
import com.example.mvvm_paperdb_retrofit.retrofit.RetrofitService
import com.example.mvvm_paperdb_retrofit.retrofit.TaskServerInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class TaskServerApi :TaskInterface {
    private val service = RetrofitService.retrofit.create(TaskServerInterface::class.java)
    override suspend fun getTaskById(id: String): TaskModel {
        try {
            return withContext(Dispatchers.IO){
                delay(10000)
                async { service.getTaskById(id).execute().body() }.await() ?: TaskModel()
            }
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