package com.packt.conversations.ui

import androidx.annotation.StringRes

data class ConversationsLitstTab(@StringRes val title: Int)

fun generateTabs(): List<ConversationsLitstTab>{
    return listOf(
        ConversationsLitstTab(title = com.packt.whatspackt.common.framework.R.string.converations_tab_status_title),
        ConversationsLitstTab(title = com.packt.whatspackt.common.framework.R.string.conversations_tab_chats_title),
        ConversationsLitstTab(title = com.packt.whatspackt.common.framework.R.string.conversations_tab_calls_title)
    )
}


