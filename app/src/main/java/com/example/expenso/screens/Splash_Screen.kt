package com.example.expenso.screens

import androidx.compose.foundation.background
import com.example.expenso.R
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun Splash_Screen(navController: NavController)
{
    LaunchedEffect(Unit) {
        delay(1500)
        navController.navigate(route = "home_screen")
    }
    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF2A7D68)), contentAlignment = Alignment.Center)
    {
        Text("Expenso", fontSize = 50.sp, color = Color.White)
    }
}

