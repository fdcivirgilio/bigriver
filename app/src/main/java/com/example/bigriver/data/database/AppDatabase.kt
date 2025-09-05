package com.example.bigriver.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bigriver.data.dao.UserDao
import com.example.bigriver.data.dao.PostDao
import com.example.bigriver.data.entity.User
import com.example.bigriver.data.entity.Post

@Database(entities = [User::class, Post::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    // Link your DAO
    abstract fun userDao(): UserDao
    abstract fun postDao(): PostDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Get a singleton instance of the database
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "big-river-db" // database name
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}