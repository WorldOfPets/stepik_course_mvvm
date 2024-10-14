package com.example.mvvm_paperdb_retrofit.retrofit

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
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
}