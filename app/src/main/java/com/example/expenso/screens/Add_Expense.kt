package com.example.expenso.screens

import android.app.DatePickerDialog
import com.example.expenso.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.expenso.data.model.Expense_Entity
import com.example.expenso.viewmodel.Home_View_Model
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun Add_Expense(navController: NavController, viewModel: Home_View_Model) {
    var name by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    val typeOptions = listOf("Expense", "Income")
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope() // Added missing scope

    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            date = "$selectedDay/${selectedMonth + 1}/$selectedYear"
        },
        year,
        month,
        day
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null, // Remove ripple effect
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus() // Clear focus to hide keyboard
            }
    ) {

        // Green Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(bottomStart = 42.dp, bottomEnd = 42.dp))
                .background(Color(0xFF2A7D68))
                .padding(horizontal = 16.dp, vertical = 38.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                IconButton(
                    onClick = { navController.navigate("home_screen") },
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = "Back",
                        modifier = Modifier.size(28.dp),
                        tint = Color.White
                    )
                }

                Text(
                    text = "Add Expense",
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        // Expense Card (Overlapping)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .wrapContentHeight()
                .offset(y = 180.dp)
                .zIndex(1f),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(10.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    "Type",
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                )

                // Dropdown Menu for Type
                Box {
                    OutlinedTextField(
                        value = type,
                        onValueChange = { },
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Select Type") },
                        trailingIcon = {
                            Icon(
                                painter = painterResource(id = if (expanded) R.drawable.triangle_up else R.drawable.triangle_down),
                                contentDescription = "Dropdown Arrow",
                                tint = Color.Gray,
                                modifier = Modifier.size(18.dp)
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = Color(0xFF2A7D68),
                            unfocusedBorderColor = Color(0xFF2A7D68),
                            cursorColor = Color(0xFF2A7D68),
                        )
                    )

                    // Invisible clickable overlay
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .clickable { expanded = !expanded }
                    )

                    // Dropdown Menu
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.White)
                    ) {
                        typeOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option, color = Color.Black) },
                                onClick = {
                                    type = option
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "Name",
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                )
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    placeholder = { Text("Enter expense name") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = Color(0xFF2A7D68),
                        unfocusedBorderColor = Color(0xFF2A7D68),
                        cursorColor = Color(0xFF2A7D68),
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "Amount",
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                )
                OutlinedTextField(
                    value = amount,
                    onValueChange = { newValue ->
                        // Only allow digits and decimal point
                        if (newValue.isEmpty() || newValue.matches(Regex("^\\d*\\.?\\d*$"))) {
                            amount = newValue
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true,
                    placeholder = { Text("Enter amount") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = Color(0xFF2A7D68),
                        unfocusedBorderColor = Color(0xFF2A7D68),
                        cursorColor = Color(0xFF2A7D68),
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "Date",
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                )

                // Fixed Date Picker Implementation
                Box {
                    OutlinedTextField(
                        value = date,
                        onValueChange = { }, // Empty since it's read-only
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true,
                        placeholder = { Text("Select Date") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = Color(0xFF2A7D68),
                            unfocusedBorderColor = Color(0xFF2A7D68),
                            cursorColor = Color(0xFF2A7D68),
                        )
                    )

                    // Invisible clickable overlay to show date picker
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .clickable { datePickerDialog.show() }
                    )
                }

                Spacer(modifier = Modifier.height(55.dp))

                Button(
                    onClick = {
                        if (name.isNotEmpty() && amount.isNotEmpty() && date.isNotEmpty() && type.isNotEmpty()) {
                            val expense = Expense_Entity(
                                id = 0,
                                title = name,
                                amount = amount.toDoubleOrNull() ?: 0.0,
                                date = date,
                                type = type,
                                category = "General"
                            )
                            scope.launch {
                                viewModel.insertExpense(expense)
                                navController.popBackStack()
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2A7D68)
                    )
                ) {
                    Text("Add ${if (type == "Income") "Income" else "Expense"}", color = Color.White)
                }
            }
        }
    }
}


