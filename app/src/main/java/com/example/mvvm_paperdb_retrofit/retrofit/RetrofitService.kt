package com.example.mvvm_paperdb_retrofit.retrofit

import android.util.Log
import com.example.mvvm_paperdb_retrofit.model.MyCustomCallback
import com.example.mvvm_paperdb_retrofit.model.tasks.TaskModel
import io.paperdb.Paper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import java.net.URL

object RetrofitService {
    private const val URL_MOCKAPI:String = "https://6704d32eab8a8f892734f73d.mockapi.io/"
    var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(URL_MOCKAPI)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    fun checkInternetConnection():Boolean {
        try{
            return runBlocking {
                CoroutineScope(Dispatchers.IO).async {
                    val url = URL(URL_MOCKAPI)
                    val connection = url.openConnection() as HttpURLConnection
                    connection.requestMethod = "HEAD"
                    connection.connectTimeout = 1000
                    connection.connect()
                    val responseCode = connection.responseCode
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        Log.e("InternetConnection", "Internet connection is available")
                        true
                    } else {
                        Log.e("InternetConnection", "Internet connection is not available")
                        false
                    }
                }.await()

            }
        } catch (ex : Exception) {
            Log.e("InternetConnection", ex.toString())
            Log.d("InternetConnection", "No internet connection")
            return false
        }
    }
    fun syncData(callback: MyCustomCallback<TaskModel>){
        val service = retrofit.create(TaskServerInterface::class.java)
        val serverList = runBlocking {
            CoroutineScope(Dispatchers.IO).async {
                service.getTasks().execute().body() ?: arrayListOf()
            }.await()
        }
        val localList = Paper.book().read<List<TaskModel>>("task") ?: arrayListOf()
        if (localList != serverList){
            localList.filter { it !in serverList }.forEach {
                service.addTask(it).enqueue(object : Callback<TaskModel> {
                    override fun onResponse(p0: Call<TaskModel>, p1: Response<TaskModel>) {

                    }

                    override fun onFailure(p0: Call<TaskModel>, p1: Throwable) {
                        callback.onFailure(p1.toString())
                    }
                })
            }
            Paper.book().write("task", (localList + serverList).distinctBy { it.id })
            callback.notify("DATA SYNCHRONIZED")
        }
    }
}