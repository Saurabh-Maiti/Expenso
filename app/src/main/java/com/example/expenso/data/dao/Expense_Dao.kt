package com.example.expenso.data.dao

import androidx.room.*
import com.example.expenso.data.model.Expense_Entity
import kotlinx.coroutines.flow.Flow

@Dao
interface Expense_Dao {
    @Query("SELECT * FROM expense_table")
    fun get_all_expense(): Flow<List<Expense_Entity>>

    @Insert
    suspend fun insert_expense(expenseEntity: Expense_Entity)

    @Delete
    suspend fun delete_expense(expenseEntity: Expense_Entity)

    @Update
    suspend fun update_expense(expenseEntity: Expense_Entity)
    @Query("DELETE FROM expense_table")
    suspend fun deleteAllExpenses()
}
