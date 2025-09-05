package com.example.bigriver.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.bigriver.data.database.AppDatabase
import com.example.bigriver.data.entity.User
import com.example.bigriver.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val userDao = AppDatabase.getDatabase(application).userDao()
    private val repository = UserRepository(userDao)

    fun insertUser(name: String, age: Int) {
        viewModelScope.launch {
            repository.insertUser(User(name = name, age = age))
        }
    }

    fun printAllUsers() {
        viewModelScope.launch {
            val users = repository.getAllUsers()
            users.forEach { println(it) }
        }
    }
}