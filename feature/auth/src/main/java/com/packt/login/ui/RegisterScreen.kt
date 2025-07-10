package com.packt.login.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.packt.framework.navigation.LastRouteDataStore
import com.packt.framework.navigation.NavRoutes
import com.packt.framework.ui.theme.BlueButton
import com.packt.framework.ui.theme.RedBackground
import com.packt.framework.ui.theme.WhiteText
import com.packt.login.R
import com.packt.login.ui.viewmodel.RegisterViewModel
import domain.model.RegisterState

@Composable
fun RegisterScreen(viewModel: RegisterViewModel = hiltViewModel()) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val registerState by viewModel.registerState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        LastRouteDataStore.saveLastRoute(context, NavRoutes.RegisterScreen)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = RedBackground
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Logo
            Icon(
                painter = painterResource(id = R.drawable.whatspacktsplash), // Add to res/drawable
                contentDescription = "WhatsPackt Logo",
                tint = WhiteText,
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("WhatsPackt", fontSize = 20.sp, color = WhiteText)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Register",
                style = MaterialTheme.typography.headlineMedium,
                color = WhiteText
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = {
                    Text(
                        "Username",
                        color = WhiteText
                    )
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = WhiteText,
                    unfocusedTextColor = WhiteText,
                    focusedBorderColor = WhiteText,
                    unfocusedBorderColor = WhiteText
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = {
                    Text(
                        "Email",
                        color = WhiteText
                    )
                },
                singleLine = true,
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
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = WhiteText,
                    unfocusedTextColor = WhiteText,
                    focusedBorderColor = WhiteText,
                    unfocusedBorderColor = WhiteText
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel.registerWithEmail(email = email, password = password, username = username) },
                colors = ButtonDefaults.buttonColors(contentColor = BlueButton),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = "Register",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            when (registerState) {
                is RegisterState.Loading -> {
                    Spacer(modifier = Modifier.height(24.dp))
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }

                is RegisterState.Error -> {
                    Toast.makeText(
                        LocalContext.current,
                        "Error: ${(registerState as RegisterState.Error).message}",
                        Toast.LENGTH_LONG).show()
                }

                is RegisterState.Success -> {
                    Toast.makeText(
                        LocalContext.current,
                        "Successful Registration",
                        Toast.LENGTH_LONG).show()
                }

                else -> {}
            }
        }
    }
}
