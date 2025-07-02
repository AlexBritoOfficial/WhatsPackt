package com.packt.create_chat.presentation.ui

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.packt.framework.ui.theme.RedBackground
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.currentCompositionErrors
import com.packt.create_chat.R
import com.packt.create_chat.presentation.state.CreateChatUiState
import com.packt.framework.ui.theme.BlueButton
import com.packt.framework.ui.theme.WhiteText



@Composable
fun CreateChatScreen(
    viewModel: CreateChatScreenViewModel = hiltViewModel(),
    onStartChat: (String) -> Unit
) {
    var username by remember { mutableStateOf("") }
    val isUsernameValid = username.trim().isNotEmpty()

    val uiState = viewModel.uiState.collectAsState().value
    val currentUser = viewModel.currentUserData.collectAsState().value

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
            modifier = Modifier.size(300.dp).padding(24.dp)
        )

        // Title
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
            enabled = uiState !is CreateChatUiState.Loading,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (isUsernameValid && uiState !is CreateChatUiState.Loading) {
                        viewModel.startNewChat(currentUserId = currentUser!!.id,
                            otherParticipantName = username )
                    }
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
            onClick = {
                if (isUsernameValid && uiState !is CreateChatUiState.Loading) {
                    viewModel.startNewChat(currentUserId = currentUser!!.id,
                        otherParticipantName = username )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            enabled = isUsernameValid && uiState !is CreateChatUiState.Loading,
            colors = ButtonDefaults.buttonColors(
                containerColor = BlueButton,
                contentColor = WhiteText
            )
        ) {
            when (uiState) {
                is CreateChatUiState.Loading -> {
                    CircularProgressIndicator(
                        color = WhiteText,
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )
                }
                else -> {
                    Text("Start Chat")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (uiState is CreateChatUiState.Error) {
            Text(
                text = uiState.message,
                color = RedBackground, // Or another error color, adjust as needed
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }

    // On success, trigger navigation callback and reset UI state
    if (uiState is CreateChatUiState.Success) {
        LaunchedEffect(uiState.chatId) {
            onStartChat(uiState.chatId)
            viewModel.resetState()
        }
    }
}
