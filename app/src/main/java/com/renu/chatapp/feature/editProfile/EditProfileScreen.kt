package com.renu.chatapp.feature.editProfile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import javax.inject.Scope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen() {

    val snackbarHostState = remember { SnackbarHostState() }
    
    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = "Profile")}
            )
        },
        snackbarHost = {
            //TODO : SnackBar not visible when keyboard is open
            SnackbarHost (
                hostState = snackbarHostState
            )
        }
    ){ paddingValues ->

        var name by remember { mutableStateOf("") }

        Column (
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(16.dp)
        ){
            OutlinedTextField(
                modifier = Modifier.fillMaxSize(),
                value = name,
                onValueChange = { name = it},
                label = { Text(text = "Name") }
            )

            val scope = rememberCoroutineScope()
            Button(
                modifier = Modifier.padding(top = 24.dp)
                    .align(Alignment.CenterHorizontally),
                onClick = {
                if(name.isNotEmpty()){
                    scope.launch {
                        snackbarHostState.showSnackbar("Your name is $name")
                    }
                }else{
                    scope.launch {
                        snackbarHostState.showSnackbar("Please input your name")
                    }
                }
            }) {
                Text(text = "SAVE")
            }
        }
    }
}