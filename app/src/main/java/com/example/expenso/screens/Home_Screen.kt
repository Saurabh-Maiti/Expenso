package com.example.expenso.screens

import androidx.compose.foundation.background
import com.example.expenso.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.expenso.data.model.Expense_Entity
import com.example.expenso.viewmodel.Home_View_Model
import java.time.LocalTime

@Composable
fun Home_Screen(navController: NavController, viewModel: Home_View_Model) {
    val expenseList by viewModel.expenses.collectAsState(initial = emptyList())
    var showDialog by remember { mutableStateOf(false) }

    val income = viewModel.total_income(expenseList)
    val expense = viewModel.total_expense(expenseList)
    val balance = viewModel.get_balance(expenseList)
    val greeting = when (LocalTime.now().hour) {
        in 5..11 -> "Good Morning"
        in 12..16 -> "Good Afternoon"
        else -> "Good Evening"
    }
    // Confirmation Dialog
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(
                    text = "Clear All Transactions",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            },
            containerColor = Color.White,
            text = {
                Text("Are you sure you want to delete all transactions? This action cannot be undone.", color = Color.Black)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.clearAllTransactions()
                        showDialog = false
                    }
                ) {
                    Text(
                        "Delete All",
                        color = Color.Red,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDialog = false }
                ) {
                    Text("Cancel", color = Color(0xFF2A7D68))
                }
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Green Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(bottomStart = 42.dp, bottomEnd = 42.dp))
                .background(Color(0xFF2A7D68))
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(top=30.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text(greeting, color = Color.White, fontSize = 16.sp)
                    Text("MoneyBee \uD83D\uDC1D\t", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                }
                IconButton(onClick = {
                    showDialog = true
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.menu),
                        contentDescription = "Menu",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }

        BalanceCard(income = income, expenses = expense, totalBalance = balance)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 240.dp)
                .background(Color.White)
        ) {
            Spacer(modifier = Modifier.height(80.dp))
            Transaction_List(expenseList = expenseList)
        }

        // FAB
        FloatingActionButton(
            onClick = { navController.navigate("add_expense_screen") },
            containerColor = Color(0xFF2A7D68),
            contentColor = Color.White,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }
    }
}

@Composable
fun BalanceCard(income: String, expenses: String, totalBalance: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .wrapContentHeight()
            .offset(y = 150.dp)
            .zIndex(1f),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2F7E79))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text("Total Balance", color = Color.White, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(totalBalance, color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(horizontalAlignment = Alignment.Start) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_down),
                            contentDescription = "Arrow Down",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Income", color = Color.White, fontSize = 14.sp)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(income, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                }

                Column(horizontalAlignment = Alignment.End) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_up),
                            contentDescription = "Arrow Up",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Expenses", color = Color.White, fontSize = 14.sp)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(expenses, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

@Composable
fun Transaction_List(expenseList: List<Expense_Entity>) {
    Column(modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "All Transactions",
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${expenseList.size} transactions",
                fontSize = 14.sp,
                color = Color(0xFF8F8F8F),
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Show message when no transactions
        if (expenseList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No transactions yet",
                        fontSize = 16.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Tap the + button to add your first transaction",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
        } else {
            // Transaction List - Shows ALL transactions with scroll
            LazyColumn(
                modifier = Modifier.fillMaxHeight(),
                contentPadding = PaddingValues(bottom = 80.dp) // Add padding to avoid FAB overlap
            ) {
                items(expenseList) { expense ->
                    TransactionItem(expense = expense)
                }
            }
        }
    }
}

@Composable
fun TransactionItem(expense: Expense_Entity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = expense.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Text(
                    text = expense.date,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = if (expense.type == "Income") "+₹${expense.amount}" else "-₹${expense.amount}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (expense.type == "Income") Color(0xFF2A7D68) else Color(0xFFE74C3C)
                )
                Text(
                    text = expense.type,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}