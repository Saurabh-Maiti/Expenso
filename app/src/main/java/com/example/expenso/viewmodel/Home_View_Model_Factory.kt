package com.example.expenso.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.expenso.data.dao.Expense_Dao

class Home_View_Model_Factory(private val dao: Expense_Dao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(Home_View_Model::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return Home_View_Model(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
