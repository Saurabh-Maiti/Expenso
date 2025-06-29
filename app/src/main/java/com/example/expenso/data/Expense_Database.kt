package com.example.expenso.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.expenso.data.dao.Expense_Dao
import com.example.expenso.data.model.Expense_Entity

@Database(entities = [Expense_Entity::class], version = 1, exportSchema = false)
abstract class Expense_Database : RoomDatabase() {

    abstract fun expensedao(): Expense_Dao

    companion object {
        private const val DATABASE_NAME = "expense_database"

        @Volatile
        private var INSTANCE: Expense_Database? = null

        fun getDatabase(context: Context): Expense_Database {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    Expense_Database::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
