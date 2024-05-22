package com.renu.chatapp.feature.chat.comp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.renu.chatapp.domain.model.Message
import com.renu.chatapp.ui.theme.Neutral50

@Composable
fun MessageCard(
    message: Message
) {
    Card {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
        ) {
            Text(
                text = message.message,
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black
            )

            Text(
                text = message.time.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = Neutral50
            )
        }
    }
}
