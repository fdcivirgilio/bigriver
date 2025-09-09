package com.example.bigriver.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.bigriver.data.dao.UserDao
import com.example.bigriver.data.dao.PostDao
import com.example.bigriver.data.entity.User
import com.example.bigriver.data.entity.Post

@Database(entities = [User::class, Post::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    // Link your DAO
    abstract fun userDao(): UserDao
    abstract fun postDao(): PostDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        private val MIGRATION_1_2 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL("ALTER TABLE user_table ADD COLUMN username TEXT NOT NULL DEFAULT ''")
//                database.execSQL("ALTER TABLE user_table ADD COLUMN emailAddress TEXT NOT NULL DEFAULT ''")
//                database.execSQL("ALTER TABLE user_table ADD COLUMN password TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE user_table ADD COLUMN userImageURL TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE user_table ADD COLUMN userToken TEXT NOT NULL DEFAULT ''")

            }
        }


        // Get a singleton instance of the database
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "big-river-db" // database name
                )
                    .addMigrations(MIGRATION_1_2) // ⬅ add migration
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}