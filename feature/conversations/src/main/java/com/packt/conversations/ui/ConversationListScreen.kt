package com.packt.conversations.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.packt.conversations.ui.state.UserDataState
import com.packt.framework.navigation.LastRouteDataStore
import com.packt.framework.navigation.NavRoutes
import com.packt.framework.ui.Avatar
import com.packt.whatspackt.feature.conversations.R
import kotlinx.coroutines.launch
import model.Conversation

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ConversationListScreen(
    viewModel: ConversationsViewModel = hiltViewModel(),
    onNewConversationClick: () -> Unit,
    onConversationClick: (chatId: String) -> Unit,
    onProfileClick: () -> Unit
) {

    val conversations by viewModel.conversations.collectAsStateWithLifecycle()
    val user by viewModel.currentUser.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        LastRouteDataStore.saveLastRoute(context, NavRoutes.ConversationsList)
        viewModel.getConversations()
    }
    val tabs = generateTabs()
    val selectedIndex by remember { mutableStateOf(1) }
    val pagerState =
        rememberPagerState(initialPage = 1, initialPageOffsetFraction = 0f, pageCount = { 3 })
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                when (val state = user) {
                    is UserDataState.Loading -> {
                        Text("Loading user...")
                    }
                    is UserDataState.Error -> {
                        Text("Error loading user: ${state.message}")
                    }
                    is UserDataState.Success -> {
                        DrawerUserHeader(userData = state.data)

                        HorizontalDivider()

                        DrawerProfileOption(onClick = {
                            onProfileClick()
                        })
                    }

                    is UserDataState.Loaded -> TODO()
                }

            }
        }) {
        Scaffold(
            topBar = {

                TopAppBar(title = {
                    Text(text = stringResource(com.packt.whatspackt.common.framework.R.string.conversation_list_title))
                },
                    actions = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }) {
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
                                conversations = conversations,
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
}
