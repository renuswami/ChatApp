package com.renu.chatapp.feature.editProfile.comp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.renu.chatapp.ui.general.AsyncImage
import com.streamliners.pickers.media.PickedMedia

@Composable
fun ProfileImage(
    modifier: Modifier,
    pickedMedia: PickedMedia,
    onClick: () -> Unit
){
    AsyncImage(
       uri = pickedMedia.uri,
        modifier = modifier
            .size(100.dp)
            .clip(CircleShape)
            .clickable { onClick() }
    )
}