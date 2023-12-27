package com.ahmed.banaochattask.ui.features.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.navArgument
import com.ahmed.banaochattask.core.navigation.Screen
import com.ahmed.banaochattask.data.prefs.LocalPref
import com.ahmed.banaochattask.ui.features.auth.composables.AuthItem

@Composable
fun AuthScreen(
    authViewModel: AuthViewModel = hiltViewModel(), navHostController: NavHostController
) {

    var isLoading by remember {
        mutableStateOf(false)
    }
    var id by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        authViewModel.uiState.collect {
            when (it) {
                AuthUiState.Error -> isLoading = false
                AuthUiState.Idle -> isLoading = false
                AuthUiState.Loading -> isLoading = true
                AuthUiState.Success -> {
                    isLoading = false
                    LocalPref(context).setString("id", id)
                    navHostController.navigate(Screen.HomeScreen.routeName)

                }
            }
        }
    }
    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    } else Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        AuthItem(i = 1) {
            authViewModel.signIn("user1@gmail.com", "12345678")
            id = "1"
        }
        Spacer(modifier = Modifier.height(10.dp))
        AuthItem(i = 2) {
            authViewModel.signIn("user2@gmail.com", "12345678")
            id = "2"
        }
        Spacer(modifier = Modifier.height(10.dp))
        AuthItem(i = 3) {
            authViewModel.signIn("user3@gmail.com", "12345678")
            id = "3"
        }
    }


}