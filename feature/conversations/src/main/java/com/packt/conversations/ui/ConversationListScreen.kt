package com.packt.conversations.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import model.Conversation

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ConversationListScreen(
    onNewConversationClick: () -> Unit,
    onConversationClick: (chatId: String) -> Unit
) {

    val tabs = generateTabs()
    val selectedIndex by remember { mutableStateOf(1) }
    val pagerState =
        rememberPagerState(initialPage = 1, initialPageOffsetFraction = 0f, pageCount = { 3 })

    Scaffold(
        topBar = {

            TopAppBar(title = {
                Text(text = stringResource(com.packt.whatspackt.common.framework.R.string.conversation_list_title))
            },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                })
        },
        bottomBar = {
            TabRow(selectedTabIndex = 1) {
                tabs.forEachIndexed { index, tab ->
                    Tab(text = { Text(text = stringResource(tabs[index].title)) },
                        selected = index == 1,
                        onClick = {})
                }
            }
        },
        content = { innerPadding ->
            HorizontalPager(
                modifier = Modifier.padding(innerPadding),
                state = pagerState
            ) { index ->
                when (index) {

                    0 -> {

                    }

                    1 -> {
                        ConversationList(
                            conversations = generateFakeConversations(),
                            onConversationClick = onConversationClick
                        )
                    }

                    2 -> {

                    }
                }
            }

            LaunchedEffect(selectedIndex) {
                pagerState.animateScrollToPage(selectedIndex)
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onNewConversationClick()
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        })
}

fun generateFakeConversations(): List<Conversation> {
    return listOf(
        Conversation(
            id = "chatId1",
            name = "John Doe",
            message = "Hey, how are you?",
            timestamp = "10:30",
            avatar = "https://i.pravatar.cc/150?u=1",
            unreadCount = 2
        ),
        Conversation(
            id = "2",
            name = "Jane Smith",
            message = "Looking forward to the party!",
            timestamp = "11:15",
            avatar = "https://i.pravatar.cc/150?u=2"
        ),
        // Add more conversations here
        Conversation(
            id = "3",
            name = "Michael Johnson",
            message = "Did you finish the project?",
            timestamp = "9:45",
            avatar = "https://i.pravatar.cc/150?u=3",
            unreadCount = 3
        ),
        Conversation(
            id = "4",
            name = "Emma Brown",
            message = "Great job on the presentation!",
            timestamp = "12:20",
            avatar = "https://i.pravatar.cc/150?u=4"
        ),
        Conversation(
            id = "5",
            name = "Lucas Smith",
            message = "See you at the game later.",
            timestamp = "14:10",
            avatar = "https://i.pravatar.cc/150?u=5",
            unreadCount = 1
        ),
        Conversation(
            id = "6",
            name = "Sophia Johnson",
            message = "Let's meet for lunch tomorrow.",
            timestamp = "16:00",
            avatar = "https://i.pravatar.cc/150?u=6"
        ),
        Conversation(
            id = "7",
            name = "Olivia Brown",
            message = "Can you help me with the assignment?",
            timestamp = "18:30",
            avatar = "https://i.pravatar.cc/150?u=7",
            unreadCount = 5
        ),
        Conversation(
            id = "8",
            name = "Liam Williams",
            message = "I'll call you later.",
            timestamp = "19:15",
            avatar = "https://i.pravatar.cc/150?u=8"
        ),
        Conversation(
            id = "9",
            name = "Charlotte Johnson",
            message = "Don't forget the meeting tomorrow.",
            timestamp = "21:45",
            avatar = "https://i.pravatar.cc/150?u=9",
            unreadCount = 1
        ),
        Conversation(
            id = "10",
            name = "James Brown",
            message = "The movie was awesome!",
            timestamp = "23:00",
            avatar = "https://i.pravatar.cc/150?u=10"
        ),
        Conversation(
            id = "11",
            name = "Jake Smith",
            message = "Did you get the tickets?",
            timestamp = "23:50",
            avatar = "https://i.pravatar.cc/150?u=11"
        )
    )
}

@Preview(showBackground = true)
@Composable
fun ConversationListScreenPreview() {
    ConversationListScreen(onNewConversationClick = {},
        onConversationClick = { string -> })
}
