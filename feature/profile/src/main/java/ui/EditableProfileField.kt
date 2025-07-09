package com.packt.profile

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.packt.framework.ui.theme.BlueButton
import com.packt.framework.ui.theme.RedBackground
import com.packt.framework.ui.theme.WhiteText
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun EditableProfileField(
    label: String,
    initialValue: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val textState = remember { mutableStateOf(TextFieldValue(initialValue)) }
    val colors = MaterialTheme.colorScheme

    OutlinedTextField(
        value = textState.value,
        onValueChange = {
            textState.value = it
            onValueChange(it.text)
        },
        label = {
            Text(text = label, color = colors.primary)
        },
        modifier = modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth(),
        singleLine = false,
        maxLines = 1,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = BlueButton,
            unfocusedTextColor = BlueButton,
            focusedBorderColor = RedBackground,
            unfocusedBorderColor = RedBackground
        )
    )
}