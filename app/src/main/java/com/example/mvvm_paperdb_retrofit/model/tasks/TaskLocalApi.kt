package com.example.mvvm_paperdb_retrofit.model.tasks

import android.util.Log
import io.paperdb.Paper
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.UUID

class TaskLocalApi:TaskInterface {
    private val TASK_TABLE = "tasks"

    override fun getTaskById(id: String): TaskModel {
        val tasks = Paper.book().read<List<TaskModel>>(TASK_TABLE)
        var findTask = TaskModel()
        if(!tasks.isNullOrEmpty()){
            tasks.forEach {
                if(it.id == id){
                    findTask = it
                }
            }
        }
        return findTask
    }

    override fun getTasks(): List<TaskModel> {
        return Paper.book().read<List<TaskModel>>(TASK_TABLE) ?: arrayListOf()
    }

    override fun addTask(task: TaskModel): Boolean {
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
            return true
        }catch (ex:Exception){
            Log.e(TaskLocalApi::class.java.simpleName, ex.toString())
            return false
        }
    }

    override fun updateTask(task: TaskModel):Boolean {
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
                return true
            }else{
                return false
            }

        }catch (ex:Exception){
            Log.e(TaskLocalApi::class.java.simpleName, ex.toString())
            return false
        }
    }

    override fun deleteTask(id: String): Boolean {
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
                return true
            }else{
                return false
            }
        }catch (ex:Exception){
            Log.e(TaskLocalApi::class.java.simpleName, ex.toString())
            return false
        }
    }

    override fun completeTask(id:String): Boolean {
        try {
            var tasks = Paper.book().read<List<TaskModel>>(TASK_TABLE)
            if(!tasks.isNullOrEmpty()){
                tasks.forEach {
                    if(it.id == id){
                        it.isCompleted = true
                    }
                }
                Paper.book().write(TASK_TABLE, tasks)
                return true
            }else{
                return false
            }

        }catch (ex:Exception){
            Log.e(TaskLocalApi::class.java.simpleName, ex.toString())
            return false
        }
    }
}