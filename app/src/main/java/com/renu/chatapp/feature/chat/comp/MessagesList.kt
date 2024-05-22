package com.renu.chatapp.feature.chat.comp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.renu.chatapp.feature.chat.ChatViewModel

@Composable
fun MessagesList(data: ChatViewModel.Data) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)

    ) {
        items(data.chatListItems) { chatListItem ->

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = when (chatListItem) {
                    is ChatViewModel.ChatListItem.SentMessages -> Alignment.CenterEnd

                    is ChatViewModel.ChatListItem.ReceivedMessage -> Alignment.CenterStart

                    is ChatViewModel.ChatListItem.Date -> Alignment.Center

                }
            ) {
                when (chatListItem) {
                    is ChatViewModel.ChatListItem.Date -> {
                        Text(text = chatListItem.date)
                    }
                    is ChatViewModel.ChatListItem.ReceivedMessage ->
                        MessageCard(
                            message = chatListItem.message
                        )

                    is ChatViewModel.ChatListItem.SentMessages ->
                        MessageCard(
                            message = chatListItem.message
                        )
                }
            }
        }
    }
}

