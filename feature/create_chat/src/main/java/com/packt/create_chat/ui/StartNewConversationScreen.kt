package com.packt.create_chat.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.packt.framework.ui.theme.BlueButton
import com.packt.framework.ui.theme.RedBackground
import com.packt.framework.ui.theme.WhiteText
import com.packt.whatspackt.R

@Composable
fun StartNewConversationScreen(
    onStartChat: (String) -> Unit
) {
    var username by remember { mutableStateOf("") }
    val isUsernameValid = username.trim().isNotEmpty()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(RedBackground)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.whatspacktsplash),
            contentDescription = "WhatsPackt Logo",
            tint = WhiteText,
            modifier = Modifier
                .size(300.dp)
                .padding(24.dp)
        )

        // 🏷️ Title
        Text(
            text = "Start New Conversation",
            style = MaterialTheme.typography.headlineSmall,
            color = WhiteText
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🔤 Username Field
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Enter Username") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (isUsernameValid) onStartChat(username.trim())
                }
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = WhiteText,
                unfocusedTextColor = WhiteText,
                focusedBorderColor = WhiteText,
                unfocusedBorderColor = WhiteText,
                focusedLabelColor = WhiteText,
                unfocusedLabelColor = WhiteText,
                cursorColor = WhiteText
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onStartChat(username.trim()) },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = BlueButton,   // ✅ Blue background
                contentColor = WhiteText       // ✅ White text
            )
        ) {
            Text("Start Chat")
        }
    }
}

