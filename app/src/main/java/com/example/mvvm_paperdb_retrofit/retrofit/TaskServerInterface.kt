package com.example.mvvm_paperdb_retrofit.retrofit

import com.example.mvvm_paperdb_retrofit.model.tasks.TaskModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface TaskServerInterface {
    @GET("/task/{id}")
    fun getTaskById(@Path("id") id: String): Call<TaskModel>

    @GET("/task")
    fun getTasks():Call<List<TaskModel>>

    fun addTask(task: TaskModel): Boolean

    fun updateTask(task: TaskModel):Boolean

    fun deleteTask(id: String): Boolean

    fun completeTask(id: String): Boolean
}