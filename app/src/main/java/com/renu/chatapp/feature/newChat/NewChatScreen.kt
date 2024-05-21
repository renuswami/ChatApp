package com.renu.chatapp.feature.newChat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.renu.chatapp.domain.model.ext.id
import com.renu.chatapp.ui.Screen
import com.renu.chatapp.ui.comp.UserCard
import com.streamliners.base.taskState.comp.whenLoaded
import com.streamliners.compose.android.comp.appBar.TitleBarScaffold

@Composable
fun NewChatScreen(
    viewModel: NewChatViewModel,
    navController: NavController
) {
    LaunchedEffect(key1 = null){ viewModel.start() }

    TitleBarScaffold(
        title = "New Chat",
        navigateUp = { navController.navigateUp()}
    ) { paddingValues ->

        viewModel.usersListTask.whenLoaded {usersList ->

            LazyColumn(
                modifier = Modifier.padding(paddingValues),
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(usersList) {user->
                    UserCard(
                        user = user,
                        onClick = {
                            viewModel.onUserSelected(
                                otherUserId = user.id(),
                                onChannelReady = {channelId ->
                                    navController.navigate(
                                        Screen.Chat(channelId).route
                                    )
                                }
                            )
                        }
                    )
                }
            }
        }
    }
}