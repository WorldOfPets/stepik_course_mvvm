package com.example.mvvm_paperdb_retrofit.model.tasks

data class TaskModel(
    var id:Int? = null,
    var name:String? = null,
    var description:String? = null,
    var isCompleted:Boolean? = null,
    var timeCreated:String? = null,
    var timeDeadLine:String? = null
    )
