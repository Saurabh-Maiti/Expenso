package com.example.expenso.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expense_table")
data class Expense_Entity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: String,
    val title: String,
    val amount: Double,
    val date: String,
    val category: String
)

