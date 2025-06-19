//
package com.example.m_commerce.features.payment.prsentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.m_commerce.config.theme.Teal
import com.example.m_commerce.config.theme.White
import com.example.m_commerce.core.shared.components.CustomButton

@Composable
fun CreditCardDetailsUi() {
    var name by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var checked by remember { mutableStateOf(true) }
    var selectedMonth by remember { mutableStateOf("") }
    var selectedYear by remember { mutableStateOf("") }

    val monthOptions = (1..12).map { it.toString().padStart(2, '0') }
    val yearOptions = (25..55).map { it.toString() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Teal)
        ) {
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                Text(
                    text = if (cardNumber.isBlank()) "0000 0000 0000 0000"
                    else cardNumber.chunked(4).joinToString(" "),
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Text(
                            "Card holder name",
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 12.sp
                        )
                        Text(
                            if (name.isBlank()) "UserName" else name,
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            "Expiry date",
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 12.sp
                        )
                        Text(
                            if (selectedMonth.isBlank() || selectedYear.isBlank()) "MM/YY"
                            else "$selectedMonth/$selectedYear",
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Card Holder Name") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Teal,
                focusedLabelColor = Teal
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = cardNumber,
            onValueChange = { input ->
                cardNumber = input.filter { it.isDigit() }
                    .chunked(4).joinToString(" ")
                    .take(19)
            },
            label = { Text("Card Number") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Teal,
                focusedLabelColor = Teal
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            // Expiration Month
            Column(modifier = Modifier.weight(1f)) {
                Text("Exp Month", fontSize = 12.sp, color = Color.Gray)
                DropdownMenuBox(
                    options = monthOptions,
                    selectedValue = selectedMonth,
                    onValueSelected = { selectedMonth = it }
                )
            }

            // Expiration Year
            Column(modifier = Modifier.weight(1f)) {
                Text("Exp Year", fontSize = 12.sp, color = Color.Gray)
                DropdownMenuBox(
                    options = yearOptions,
                    selectedValue = selectedYear,
                    onValueSelected = { selectedYear = it }
                )
            }

            // CVV Field
            Column(modifier = Modifier.weight(1f)) {
                Text("CVV", fontSize = 12.sp, color = Color.Gray)
                OutlinedTextField(
                    value = cvv,
                    onValueChange = { cvv = it.filter { it.isDigit() }.take(3) },
                    modifier = Modifier
                        .fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Teal,
                        focusedLabelColor = Teal
                    ),
                    placeholder = { Text("CVV", fontSize = 12.sp, color = Color.Gray) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = checked,
                onCheckedChange = { checked = it },
                colors = CheckboxDefaults.colors(checkedColor = Teal)
            )
            Text("Save Card")
        }

        Spacer(modifier = Modifier.weight(1f))

        CustomButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            text = "Add Card",
            backgroundColor = Teal,
            textColor = Color.White,
            height = 50,
            cornerRadius = 12,
            onClick = {
                val expiry = "$selectedMonth/$selectedYear"
            }
        )
    }
}

