package com.example.expenso

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.example.expenso.data.Expense_Database
import com.example.expenso.ui.theme.Expenso
import com.example.expenso.viewmodel.Home_View_Model
import com.example.expenso.viewmodel.Home_View_Model_Factory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val dao = Expense_Database.getDatabase(applicationContext).expensedao()
        val viewModelFactory = Home_View_Model_Factory(dao)
        val viewModel = ViewModelProvider(this, viewModelFactory)[Home_View_Model::class.java]

        setContent {
            Expenso {
                My_App_Navigation(viewModel)
            }
        }
    }

}
