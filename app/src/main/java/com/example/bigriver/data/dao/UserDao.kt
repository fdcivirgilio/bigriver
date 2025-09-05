package com.example.bigriver.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.bigriver.data.entity.User

@Dao
interface UserDao {

        // Insert a new user
        @Insert
        suspend fun insert(user: User)

        // Get all users
        @Query("SELECT * FROM user_table")
        suspend fun getAll(): List<User>

        // Delete all users
        @Query("DELETE FROM user_table")
        suspend fun deleteAll()
}