package com.example.m_commerce.features.auth.register.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.m_commerce.R
import com.example.m_commerce.core.shared.components.CustomButton
import com.example.m_commerce.core.shared.components.CustomHeader
import com.example.m_commerce.core.shared.components.CustomOutlinedTextField

@Composable
fun RegisterScreen() {
    val scrollState = rememberScrollState()
    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Text(
            "Create Account", fontSize = 32.sp, fontWeight = FontWeight.Medium, color = Color.Black
        )
        Spacer(Modifier.height(16.dp))
        Text(
            "Fill your information below or register", color = Color.DarkGray, fontSize = 14.sp
        )
        Text(
            "with your social account.", color = Color.DarkGray, fontSize = 14.sp
        )

        Spacer(Modifier.height(32.dp))

        CustomHeader("Name", Modifier.align(Alignment.Start))
        CustomOutlinedTextField(name, "Ex. Husain Namir")
        Spacer(Modifier.height(16.dp))

        CustomHeader("Email", Modifier.align(Alignment.Start))
        CustomOutlinedTextField(email, "example@gmail.com")
        Spacer(Modifier.height(16.dp))

        CustomHeader("Password", Modifier.align(Alignment.Start))
        CustomOutlinedTextField(
            password, "**********", visualTransformation = PasswordVisualTransformation()
        )
        Spacer(Modifier.height(16.dp))

        CustomHeader("Confirm Password", Modifier.align(Alignment.Start))
        CustomOutlinedTextField(
            confirmPassword, "**********", visualTransformation = PasswordVisualTransformation()
        )
        Spacer(Modifier.height(16.dp))

        CustomButton(text = "Sign Up", backgroundColor = Color(0xFF008B86), textColor = Color.White)
        Spacer(Modifier.height(18.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(Modifier.width(100.dp))
            Text("Or sign up with", color = Color(0xFF797979))
            HorizontalDivider(Modifier.width(100.dp))
        }
        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {},
            modifier = Modifier
                .size(56.dp)
                .border(1.dp, Color.LightGray, CircleShape),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            contentPadding = PaddingValues(0.dp)
        ) {
            AsyncImage(
                model = R.drawable.ic_google,
                contentDescription = "Google Logo",
                modifier = Modifier.size(32.dp)
            )
        }
        Spacer(Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Already have an account?")
            Text(
                "Sign In",
                fontSize = 16.sp,
                color = Color(0xFF008B86),
                style = TextStyle(textDecoration = TextDecoration.Underline),
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true, name = "Register Screen Preview", showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen()
}