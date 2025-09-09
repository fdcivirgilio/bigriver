package com.example.bigriver.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.bigriver.data.database.AppDatabase
import com.example.bigriver.data.entity.Post
import com.example.bigriver.data.entity.User
import com.example.bigriver.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val userDao = AppDatabase.getDatabase(application).userDao()
    private val repository = UserRepository(userDao)
    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> get() = _currentUser
    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users
    fun loadUser(userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = repository.getCurrentUser(userId)
            _currentUser.postValue(user)
        }
    }

    fun loadUserByTokenId(userToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = repository.getCurrentUserByToken(userToken)
            _currentUser.postValue(user)

            Log.d("HomeFragment", "Current User - userToken: ${user?.userToken}")
        }
    }

    fun insertUser(name: String, age: Int, username: String, emailAddress: String, password: String, userImageURL: String, userToken: String) {
        viewModelScope.launch {
            repository.insertUser(User(
                name = name,
                age = age,
                username = username,
                emailAddress = emailAddress,
                password = password,
                userImageURL = userImageURL,
                userToken = userToken,
            ))
        }
    }

    fun updateUser(id: Int,name: String, age: Int, username: String, emailAddress: String, password: String, userImageURL: String, userToken: String){
        viewModelScope.launch {
            repository.updateUser(User(
                id = id,
                name = name,
                age = age,
                username = username,
                emailAddress = emailAddress,
                password = password,
                userImageURL = userImageURL,
                userToken = userToken
            ))
        }
    }

    fun updateUserImage(userId: Int, path: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUserImage(userId, path)
        }
    }

    fun updateUserToken(userId: Int, path: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUserToken(userId, path)
            Log.d("HomeFragment", "Current User Updated - userId: $userId, path: $path")
        }
    }

    fun getCurrentUser(userId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCurrentUser(userId)
        }
    }

    fun printAllUsers() {
        viewModelScope.launch {
            val users = repository.getAllUsers()
            users.forEach { println(it) }
        }
    }

    fun loadUsers() {
        viewModelScope.launch {
            val data = repository.getAllUsers()
            _users.postValue(data)
        }
    }
}