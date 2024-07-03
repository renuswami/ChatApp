package com.renu.chatapp.feature.newGroupChat.comp

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.renu.chatapp.domain.model.User
import com.renu.chatapp.domain.model.ext.id
import com.renu.chatapp.ui.comp.UserCard

fun LazyListScope.MembersInput(usersList: List<User>, members: SnapshotStateList<String>) {
    item { 
        Text(
            modifier = Modifier.padding(start = 16.dp)
                .padding(vertical = 10.dp)
                .padding(bottom = 8.dp),
            text = "Members",
            style = MaterialTheme.typography.titleMedium
        )
    }

    items(usersList) {user->
        UserCard(
            modifier = Modifier.padding(horizontal = 16.dp)
                .padding(bottom = 12.dp),
            user = user,
            checked = members.contains(user.id()),
            onCheckedChange = { checked ->
                if(checked) members.add(user.id()) else members.remove(user.id())
            },
            onClick = {
                if(members.contains(user.id())){
                    members.remove(user.id())
                }else{
                    members.add(user.id())
                }
            }
        )
    }
}