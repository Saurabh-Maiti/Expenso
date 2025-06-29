package com.example.expenso

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.expenso.screens.Add_Expense
import com.example.expenso.screens.Home_Screen
import com.example.expenso.screens.Splash_Screen
import com.example.expenso.viewmodel.Home_View_Model

@Composable
fun My_App_Navigation(viewModel: Home_View_Model) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash_screen") {
        composable("home_screen") {
            Home_Screen(navController, viewModel)
        }
        composable("add_expense_screen") {
            Add_Expense(navController, viewModel)
        }
        composable (route="splash_screen"){
            Splash_Screen(navController)
        }
    }
}
