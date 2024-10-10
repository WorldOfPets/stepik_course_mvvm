package com.example.mvvm_paperdb_retrofit.model.tasks

import android.util.Log
import com.example.mvvm_paperdb_retrofit.model.MyCustomCallback
import io.paperdb.Paper
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.UUID

class TaskLocalApi:TaskInterface {
    private val TASK_TABLE = "tasks"
    override fun getTaskById(id: String, callback: MyCustomCallback<TaskModel>) {
        val tasks = Paper.book().read<List<TaskModel>>(TASK_TABLE)
        var findTask = TaskModel()
        if(!tasks.isNullOrEmpty()){
            tasks.forEach {
                if(it.id == id){
                    findTask = it
                }
            }
        }
    }

    override fun getTasks(callback: MyCustomCallback<TaskModel>) {
        try {
            callback.onSuccess(Paper.book().read<List<TaskModel>>(TASK_TABLE) ?: arrayListOf())
        }catch (ex : Exception){
            callback.onFailure(ex.toString())
        }
    }

    override fun addTask(task: TaskModel, callback: MyCustomCallback<TaskModel>) {
        try {
            val tasks = Paper.book().read<List<TaskModel>>(TASK_TABLE) ?: arrayListOf()
            var checkID = true
            val cal = Calendar.getInstance()
            val date = Date(cal.timeInMillis) // 2077-02-22T07:46:53.082Z
            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale("RU"))
            val formattedDate = formatter.format(date)
            task.timeCreated = formattedDate
            task.isCompleted = false
            while (checkID){
                task.id = UUID.randomUUID().toString()
                checkID = tasks.filter {
                    it.id == task.id
                }.isNotEmpty()
            }
            Paper.book().write(TASK_TABLE, tasks + task)
            callback.onSuccess(Paper.book().read<List<TaskModel>>(TASK_TABLE)?: arrayListOf())
        }catch (ex:Exception){
            Log.e(TaskLocalApi::class.java.simpleName, ex.toString())
            callback.onFailure("TASK IS NOT DEFINE")
        }
    }

    override fun updateTask(task: TaskModel, callback: MyCustomCallback<TaskModel>) {
        try {
            var tasks = Paper.book().read<List<TaskModel>>(TASK_TABLE)
            if(!tasks.isNullOrEmpty()){
                tasks.forEach {
                    if(it.id == task.id){
                        it.name = task.name ?: it.name
                        it.description = task.description ?: it.description
                        it.isCompleted = task.isCompleted ?: it.isCompleted
                        it.timeCreated = task.timeCreated ?: it.timeCreated
                        it.timeDeadLine = task.timeDeadLine ?: it.timeDeadLine
                    }
                }
                Paper.book().write(TASK_TABLE, tasks)
                callback.onSuccess(Paper.book().read<List<TaskModel>>(TASK_TABLE)?: arrayListOf())
            }else{
                callback.onFailure("TASK IS NOT DEFINE")
            }

        }catch (ex:Exception){
            Log.e(TaskLocalApi::class.java.simpleName, ex.toString())
            callback.onFailure(ex.toString())
        }
    }

    override fun deleteTask(id: String, callback: MyCustomCallback<TaskModel>) {
        try {
            var newTasks = arrayListOf<TaskModel>()
            var tasks = Paper.book().read<List<TaskModel>>(TASK_TABLE)
            if (!tasks.isNullOrEmpty()){
                tasks.forEach {
                    if (it.id != id){
                        newTasks.add(it)
                    }
                }
                Paper.book().write(TASK_TABLE, newTasks)
                callback.onSuccess(Paper.book().read<List<TaskModel>>(TASK_TABLE)?: arrayListOf())
            }else{
                callback.onFailure("TASK IS NOT DEFINE")
            }
        }catch (ex:Exception){
            Log.e(TaskLocalApi::class.java.simpleName, ex.toString())
            callback.onFailure(ex.toString())
        }
    }

    override fun completeTask(id: String, callback: MyCustomCallback<TaskModel>) {
        try {
            var tasks = Paper.book().read<List<TaskModel>>(TASK_TABLE)
            if(!tasks.isNullOrEmpty()){
                tasks.forEach {
                    if(it.id == id){
                        it.isCompleted = true
                    }
                }
                Paper.book().write(TASK_TABLE, tasks)
                callback.onSuccess(Paper.book().read<List<TaskModel>>(TASK_TABLE) ?: arrayListOf())
            }else{
                callback.onFailure("TASK IS NOT DEFINE")
            }

        }catch (ex:Exception){
            Log.e(TaskLocalApi::class.java.simpleName, ex.toString())
            callback.onFailure(ex.toString())
        }
    }

    override fun syncData(list: List<TaskModel>, callback: MyCustomCallback<TaskModel>) {
        try {
            val oldList = Paper.book().read<List<TaskModel>>(TASK_TABLE) ?: arrayListOf()
            val newList = (oldList + list).distinctBy { it.id }
            Paper.book().write(TASK_TABLE, newList)
            callback.onSuccess(Paper.book().read<List<TaskModel>>(TASK_TABLE)?: arrayListOf())
        }catch (ex:Exception){
            callback.onFailure(ex.toString())
        }

    }
}