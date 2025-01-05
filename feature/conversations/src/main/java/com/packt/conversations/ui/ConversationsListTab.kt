package com.packt.conversations.ui

import androidx.annotation.StringRes

data class ConversationsListTab(@StringRes val title: Int)

fun generateTabs(): List<ConversationsListTab>{
    return listOf(
        ConversationsListTab(title = com.packt.whatspackt.common.framework.R.string.converations_tab_status_title),
        ConversationsListTab(title = com.packt.whatspackt.common.framework.R.string.conversations_tab_chats_title),
        ConversationsListTab(title = com.packt.whatspackt.common.framework.R.string.conversations_tab_calls_title)
    )
}


