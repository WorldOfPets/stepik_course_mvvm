package com.example.mvvm_paperdb_retrofit

import android.app.Application
import com.example.mvvm_paperdb_retrofit.viewModel.TaskViewModel
import io.paperdb.Paper

class TaskApplication : Application() {
    val taskViewModel = TaskViewModel()

    override fun onCreate() {
        super.onCreate()
        Paper.init(this)
    }
}