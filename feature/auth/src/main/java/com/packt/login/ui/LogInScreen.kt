package com.packt.login.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.packt.framework.ui.theme.RedBackground
import com.packt.framework.ui.theme.WhiteText
import com.packt.login.R
import domain.model.LoginState
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextButton
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.hilt.navigation.compose.hiltViewModel
import com.packt.framework.ui.theme.BlueButton
import com.packt.login.ui.viewmodel.LogInViewModel


@Composable
fun LoginScreen(
    logInViewModel: LogInViewModel = hiltViewModel(), // From ViewModel state
    onLoginSuccess: () -> Unit = {},
    onSignUp: () -> Unit,
    onLogin: (username: String, password: String) -> Unit
) {
    val loginState by logInViewModel.loginState.collectAsState()
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    // Trigger navigation on successful login
    if (loginState is LoginState.Success) {
        LaunchedEffect(Unit) {
            Toast.makeText(context, "Login Successful", Toast.LENGTH_LONG).show()
            onLoginSuccess()
        }
    }

    // Show Toast on Error once
    LaunchedEffect(loginState) {
        if (loginState is LoginState.Error) {
            Toast.makeText(context, "Error: ${loginState}", Toast.LENGTH_LONG).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(RedBackground)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Icon(painter = painterResource(id = R.drawable.whatspacktsplash),
            contentDescription = "WhatsPackt Logo",
            tint = WhiteText,
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("WP", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = WhiteText)
        Text("WhatsPackt", fontSize = 20.sp, color = WhiteText)

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username", color = WhiteText) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = WhiteText,
                unfocusedTextColor = WhiteText,
                focusedBorderColor = WhiteText,
                unfocusedBorderColor = WhiteText
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password", color = WhiteText) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = WhiteText,
                unfocusedTextColor = WhiteText,
                focusedBorderColor = WhiteText,
                unfocusedBorderColor = WhiteText
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onLogin(username, password) },
            colors = ButtonDefaults.buttonColors(contentColor = BlueButton),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            enabled = loginState !is LoginState.Loading
        ) {
            if (loginState is LoginState.Loading) {
                CircularProgressIndicator(
                    color = WhiteText,
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Text("Log In", color = WhiteText)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onSignUp) {
            Text("Don't have an account? Sign Up", color = WhiteText)
        }
    }
}