import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun StartNewConversationScreen(
    viewModel: StartNewConversationViewModel = hiltViewModel(),
   // onConversationCreated: (String) -> Unit // conversationId
) {
    var userName by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(modifier = Modifier.padding(24.dp)) {
        OutlinedTextField(
            value = userName,
            onValueChange = { userName = it },
            label = { Text("Recipient Username") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (userName.isNotBlank()) {
//                viewModel.startConversation(userName.trim(),
//                    onSuccess = { conversationId ->
//                        onConversationCreated(conversationId)
//                    },
//                    onError = { error ->
//                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
//                    }
//                )
            }
        }) {
            Text("Create Chat")
        }
    }
}
