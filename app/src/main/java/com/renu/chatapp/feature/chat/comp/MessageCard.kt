package com.renu.chatapp.feature.chat.comp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.renu.chatapp.domain.model.Message
import com.renu.chatapp.ui.theme.Neutral50
import com.streamliners.utils.DateTimeUtils
import com.streamliners.utils.DateTimeUtils.formatTime

@Composable
fun MessageCard(
    message: Message
) {
    Card {
        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = message.message,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )

            val formattedTime = remember {
                derivedStateOf {
                    formatTime(
                        DateTimeUtils.Format.HOUR_MIN_12, message.time.toDate().time
                    )
                }
            }

            Text(
                text = formattedTime.value,
                style = MaterialTheme.typography.bodyMedium,
                color = Neutral50
            )
        }
    }
}
