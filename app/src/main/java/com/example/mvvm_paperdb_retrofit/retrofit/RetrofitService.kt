package com.example.mvvm_paperdb_retrofit.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://6704d32eab8a8f892734f73d.mockapi.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}