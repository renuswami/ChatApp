package com.renu.chatapp.feature.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.renu.chatapp.feature.chat.comp.MessagesList
import com.streamliners.base.taskState.comp.whenLoaded
import com.streamliners.compose.android.comp.appBar.TitleBarScaffold
import com.streamliners.compose.comp.textInput.TextInputLayout
import com.streamliners.compose.comp.textInput.state.TextInputState
import com.streamliners.compose.comp.textInput.state.ifValidInput
import com.streamliners.compose.comp.textInput.state.update
import com.streamliners.pickers.media.FromGalleryType
import com.streamliners.pickers.media.MediaPickerDialog
import com.streamliners.pickers.media.MediaPickerDialogState
import com.streamliners.pickers.media.MediaType
import com.streamliners.pickers.media.rememberMediaPickerDialogState
import kotlinx.coroutines.launch

@Composable
fun ChatScreen(
    channelId: String, navController: NavHostController, viewModel: ChatViewModel
) {

    val mediaPickerDialogState = rememberMediaPickerDialogState()

    LaunchedEffect(key1 = Unit) { viewModel.start(channelId) }

    val messageInput = remember {
        mutableStateOf(
            TextInputState("Message")
        )
    }
    TitleBarScaffold(
        title = "Chat",
        navigateUp = { navController.navigateUp() }
    ) { paddingValue ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValue)
        ) {
            Column(
                Modifier.weight(1f)
            ) {
                viewModel.data.whenLoaded { data ->
                    MessagesList(data)
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val scope = rememberCoroutineScope()

                IconButton(
                    modifier = Modifier.padding(8.dp),
                    onClick = {
                    mediaPickerDialogState.value = MediaPickerDialogState.Visible(
                        type = MediaType.Image,
                        allowMultiple = false,
                        fromGalleryType = FromGalleryType.VisualMediaPicker
                    ) { getList ->
                        scope.launch {
                            val list = getList()
                            list.firstOrNull()?.let {
                                viewModel.sendImage(it.uri)
                            }
                        }
                    }
                }) {
                    Icon(imageVector = Icons.Default.Image, contentDescription = "Add Image ")

                }

                TextInputLayout(
                    modifier = Modifier.weight(1f),
                    state = messageInput,
                    trailingIconButton = {
                        IconButton(
                            onClick = {
                                messageInput.ifValidInput { message ->
                                    viewModel.sendMessage(message) {
                                        messageInput.update("")
                                    }
                                }
                            }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = "Send"
                            )
                        }
                    }
                )
            }
        }
    }
    MediaPickerDialog(
        state = mediaPickerDialogState,
        authority = "com.renu.chatapp.fileprovider"
    )
}


