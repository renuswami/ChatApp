package com.renu.chatapp.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.navigation.NavHostController
import com.renu.chatapp.domain.model.User
import com.renu.chatapp.domain.model.ext.id
import com.renu.chatapp.feature.home.comp.ChannelCard
import com.renu.chatapp.helper.navigate
import com.renu.chatapp.ui.Screen
import com.streamliners.base.taskState.comp.whenLoaded
import com.streamliners.compose.comp.CenterText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel,
    user: User
) {


    LaunchedEffect(key1 = Unit) { viewModel.start() }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = "ChatApp Home") }, colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = Color.White
            ),
            actions = {
                IconButton(
                    onClick = {
                        navController.navigate(Screen.EditProfile("renuswami2001@gmail.com"))
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile"
                    )
                }
            }
        )
    }, floatingActionButton = {
        FloatingActionButton(onClick = {
            navController.navigate(Screen.NewChat.route)
        }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "New oneToOneChat")
        }
    }

    ) { paddingValues ->

        viewModel.channelsState.whenLoaded { channels ->

            LazyColumn(
                modifier = Modifier.padding(paddingValues),
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (channels.isEmpty()) {
                    item {
                        CenterText(text = "No Chat found")
                    }
                } else {
                    items(channels) { channel ->
                        ChannelCard(
                            channel = channel,
                            onClick = {
                                navController.navigate(
                                    Screen.Chat(channel.id()).route
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}