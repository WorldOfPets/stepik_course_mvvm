package com.example.mvvm_paperdb_retrofit.model.tasks

import android.util.Log
import io.paperdb.Paper

class TaskLocalApi:TaskInterface {
    private val TASK_TABLE = "tasks"

    override fun getTaskById(id: Int): TaskModel {
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
            val tasks = Paper.book().read<List<TaskModel>>(TASK_TABLE)
            if(tasks.isNullOrEmpty()){
                Paper.book().write(TASK_TABLE, arrayListOf(task))
            }else{
                Paper.book().write(TASK_TABLE, tasks + task)
            }
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

    override fun deleteTask(id: Int): Boolean {
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
}