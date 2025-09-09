package com.example.bigriver.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Query
import com.example.bigriver.data.entity.User

@Dao
interface UserDao {

        // Insert a new user
        @Insert
        suspend fun insert(user: User)

        // Update user
        @Update
        suspend fun update(user: User)

        @Query("UPDATE user_table SET userImageURL = :path WHERE id = :userId")
        suspend fun updateUserImage(userId: Int, path: String)

        @Query("UPDATE user_table SET userImageURL = :path WHERE id = :userId")
        suspend fun updateUserToken(userId: Int, path: String)

        // Get all users
        @Query("SELECT * FROM user_table")
        suspend fun getAll(): List<User>

        // Get current user
        @Query("SELECT * FROM user_table WHERE id = :userId LIMIT 1")
        suspend fun getCurrent(userId: Int): User?

        // Get current user by token
        @Query("SELECT * FROM user_table WHERE id = :userToken LIMIT 1")
        suspend fun getCurrentByToken(userToken: String): User?

        // Delete all users
        @Query("DELETE FROM user_table")
        suspend fun deleteAll()
}