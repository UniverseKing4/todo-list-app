package com.todolist.app

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class TaskViewModel(app: Application) : AndroidViewModel(app) {
    private val dao = AppDatabase.get(app).taskDao()
    val tasks: LiveData<List<Task>> = dao.getAll().asLiveData()

    fun add(title: String) = viewModelScope.launch { dao.insert(Task(title = title)) }
    fun toggle(task: Task) = viewModelScope.launch { dao.update(task.copy(isDone = !task.isDone)) }
    fun delete(task: Task) = viewModelScope.launch { dao.delete(task) }
}
