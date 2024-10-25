package com.packt.conversations.ui

import androidx.annotation.StringRes
import com.packt.whatspackt.R

data class ConversationsLitstTab(@StringRes val title: Int)

fun generateTabs(): List<ConversationsLitstTab>{
    return listOf(
        ConversationsLitstTab(title = R.string.converations_tab_status_title),
        ConversationsLitstTab(title = R.string.conversations_tab_chats_title),
        ConversationsLitstTab(title = R.string.conversations_tab_calls_title)
    )
}


