package com.example.m_commerce.features.auth.presentation.components

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import coil3.compose.AsyncImage
import com.example.m_commerce.R
import com.example.m_commerce.features.auth.presentation.login.LoginViewModel
import com.example.m_commerce.features.auth.presentation.register.RegisterViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

@Composable
fun AuthSocialSection(viewModel: ViewModel) {

    val context = LocalContext.current

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account: GoogleSignInAccount = task.result
                if (viewModel is RegisterViewModel) {
                    viewModel.signUpWithGoogle(account)
                } else if (viewModel is LoginViewModel) {
                    viewModel.loginWithGoogle(account)
                }
            } catch (e: Exception) {
                Log.e("GoogleSignup", "Google sign-up failed", e)
            }
        }

    val googleSignInClient = remember {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        GoogleSignIn.getClient(context, gso)
    }
    Button(
        onClick = {
            googleSignInClient.signOut().addOnCompleteListener {
                val intent = googleSignInClient.signInIntent
                launcher.launch(intent)
            }
        },
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
    Spacer(Modifier.height(24.dp))
}