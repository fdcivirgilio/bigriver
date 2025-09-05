package com.example.bigriver.repository

import com.example.bigriver.data.dao.UserDao
import com.example.bigriver.data.entity.User

class UserRepository(private val userDao: UserDao) {

    suspend fun insertUser(user: User) {
        userDao.insert(user)
    }

    suspend fun getAllUsers(): List<User> {
        return userDao.getAll()
    }

    suspend fun deleteAllUsers() {
        userDao.deleteAll()
    }
}