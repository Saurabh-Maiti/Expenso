package com.example.expenso.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expenso.data.dao.Expense_Dao
import com.example.expenso.data.model.Expense_Entity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.collections.filter

class Home_View_Model(private val dao: Expense_Dao) : ViewModel() {

    val expenses: Flow<List<Expense_Entity>> = dao.get_all_expense()

    suspend fun insertExpense(expense: Expense_Entity) {
        dao.insert_expense(expense)
    }

    suspend fun deleteExpense(expense: Expense_Entity) {
        dao.delete_expense(expense)
    }

    suspend fun updateExpense(expense: Expense_Entity) {
        dao.update_expense(expense)
    }

    fun get_balance(list: List<Expense_Entity>): String {
        val income = list.filter { it.type.equals("Income", ignoreCase = true) }
            .sumOf { it.amount }
        val expense = list.filter { it.type.equals("Expense", ignoreCase = true) }
            .sumOf { it.amount }
        return "₹%.2f".format(income - expense)
    }

    fun total_income(list: List<Expense_Entity>): String {
        val income = list.filter { it.type.equals("Income", ignoreCase = true) }
            .sumOf { it.amount }
        return "₹%.2f".format(income)
    }

    fun total_expense(list: List<Expense_Entity>): String {
        val expense = list.filter { it.type.equals("Expense", ignoreCase = true) }
            .sumOf { it.amount }
        return "₹%.2f".format(expense)
    }

    fun clearAllTransactions() {
        viewModelScope.launch {
            try {
                dao.deleteAllExpenses()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

