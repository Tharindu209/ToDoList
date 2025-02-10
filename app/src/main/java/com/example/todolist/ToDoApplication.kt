package com.example.todolist

import android.app.Application
import com.example.todolist.model.TaskItemDatabase
import com.example.todolist.model.TaskItemRepository

class ToDoApplication : Application() {
    private val database by lazy { TaskItemDatabase.getDatabase(this) }
    val repository by lazy { TaskItemRepository(database.taskItemDao()) }

}