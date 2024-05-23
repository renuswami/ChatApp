package com.renu.chatapp.feature.chat.comp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.renu.chatapp.domain.model.Message
import com.renu.chatapp.ui.general.AsyncImage
import com.renu.chatapp.ui.theme.Neutral50

@Composable
fun MessageCard(
    message: Message,
    time: String
) {
    Card {
        Column(
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .padding(horizontal = 12.dp, vertical = 8.dp),
        ) {
            message.mediaUrl?.let {
                AsyncImage(
                    uri = it,
                    modifier = Modifier
                        .padding(top = 4.dp, bottom = 8.dp)
                        .widthIn(min = 150.dp, max = 220.dp),
                    contentScale = ContentScale.FillWidth
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = message.message,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )

                Text(
                    text = time,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Neutral50
                )
            }
        }
    }
}
