package com.example.bigriver.repository

import com.example.bigriver.data.dao.UserDao
import com.example.bigriver.data.entity.User

class UserRepository(private val userDao: UserDao) {

    suspend fun insertUser(user: User) {
        userDao.insert(user)
    }

    suspend fun updateUser(user: User) {
        userDao.update(user)
    }

    suspend fun updateUserToken(userId: Int, path: String) = userDao.updateUserToken(userId, path)

    suspend fun updateUserImage(userId: Int, path: String) = userDao.updateUserImage(userId, path)
    suspend fun getCurrentUser(userId: Int) = userDao.getCurrent(userId)
    suspend fun getCurrentUserByToken(userToken: String) = userDao.getCurrentByToken(userToken)

    suspend fun getAllUsers(): List<User> {
        return userDao.getAll()
    }

    suspend fun deleteAllUsers() {
        userDao.deleteAll()
    }
}