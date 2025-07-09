package com.packt.profile

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import ui.ProfileViewModel
import ui.state.UserDataState


@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state by viewModel.currentUser.collectAsState()
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val headerHeight = screenHeight / 4
    val context = LocalContext.current

    // React to state changes
    LaunchedEffect(state) {
        when (state) {
            is UserDataState.Success -> {
                Toast.makeText(context, "Profile updated successfully", Toast.LENGTH_SHORT).show()
            }
            is UserDataState.Error -> {
                val message = (state as UserDataState.Error).message
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
            else -> {
                // No toast needed
            }
        }
    }

    when (state) {
        is UserDataState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Loading profile...")
            }
        }

        is UserDataState.Error -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Error: ${(state as UserDataState.Error).message}")
            }
        }

        is UserDataState.Success -> {
            val userData = (state as UserDataState.Success).data
            val scrollState = rememberScrollState()

            // Editable states for input fields
            val bioState = remember { mutableStateOf(userData.bio) }
            val statusState = remember { mutableStateOf(userData.status) }
            val emailState = remember { mutableStateOf(userData.email) }
            val phoneState = remember { mutableStateOf(userData.phone) }
            val keywordsState = remember {
                mutableStateOf(
                    if (userData.searchKeywords.isNotEmpty())
                        userData.searchKeywords.joinToString(", ")
                    else ""
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(headerHeight),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clickable {
                                // TODO: Handle avatar click
                            }
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(
                                model = if (userData.avatarUrl.isNotBlank()) userData.avatarUrl
                                else "https://i.pravatar.cc/150?u=${userData.id}"
                            ),
                            contentDescription = "User Avatar",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                        )
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .offset(x = 4.dp, y = 4.dp)
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add/Change Avatar",
                                tint = Color.White,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            text = userData.displayName,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "@${userData.username}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                HorizontalDivider()

                // Scrollable editable fields
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(scrollState)
                ) {
                    EditableProfileField(
                        label = "Bio",
                        initialValue = bioState.value,
                        onValueChange = { bioState.value = it }
                    )

                    // Status dropdown
                    StatusDropdownField(
                        options = listOf(
                            "Available",
                            "Busy",
                            "Do Not Disturb",
                            "Away",
                            "Offline",
                            "On Vacation",
                            "Gaming",
                            "Listening to Music",
                            "In a Meeting"
                        ),
                        selectedStatus = statusState.value,
                        onStatusSelected = { statusState.value = it }
                    )

                    EditableProfileField(
                        label = "Email",
                        initialValue = emailState.value,
                        onValueChange = { emailState.value = it }
                    )

                    EditableProfileField(
                        label = "Phone",
                        initialValue = phoneState.value,
                        onValueChange = { phoneState.value = it }
                    )

                    EditableProfileField(
                        label = "Search Keywords",
                        initialValue = keywordsState.value,
                        onValueChange = { keywordsState.value = it }
                    )

                    ReadOnlyProfileField(
                        label = "Created",
                        value = userData.createdAt.toDateString()
                    )

                    ReadOnlyProfileField(
                        label = "Last Active",
                        value = userData.lastActive.toDateString()
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        viewModel.updateProfile(
                            bio = bioState.value,
                            status = statusState.value,
                            email = emailState.value,
                            phone = phoneState.value,
                            keywords = keywordsState.value
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text("Update Profile")
                }
            }
        }

        is UserDataState.Loaded -> TODO()
    }
}