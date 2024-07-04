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
import com.renu.chatapp.feature.chat.ChatViewModel.ChatListItem.Date
import com.renu.chatapp.feature.chat.ChatViewModel.ChatListItem.ReceivedMessage
import com.renu.chatapp.feature.chat.ChatViewModel.ChatListItem.SentMessages

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
                    is SentMessages -> Alignment.CenterEnd
                    is ReceivedMessage -> Alignment.CenterStart
                    is Date -> Alignment.Center

                }
            ) {
                when (chatListItem) {
                    is Date -> {
                        Text(text = chatListItem.date)
                    }

                    is ReceivedMessage -> {
                        MessageCard(
                            message = chatListItem.message,
                            time = chatListItem.time,
                            senderName = chatListItem.senderName
                        )
                    }

                    is SentMessages -> {
                        MessageCard(
                            message = chatListItem.message,
                            time = chatListItem.time
                        )
                    }
                }
            }
        }
    }
}

