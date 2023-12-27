package com.ahmed.banaochattask.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ahmed.banaochattask.data.prefs.LocalPref
import com.ahmed.banaochattask.ui.features.auth.AuthScreen
import com.ahmed.banaochattask.ui.features.chat.ChatScreen
import com.ahmed.banaochattask.ui.features.home.HomeScreen

@Composable
fun AppNavHost(navHostController: NavHostController) {

    val context = LocalContext.current
    val start =
        if (LocalPref(context).getString("id") == "") Screen.AuthScreen.routeName else Screen.HomeScreen.routeName

    NavHost(
        navController = navHostController, startDestination = start
    ) {

        composable(route = Screen.AuthScreen.routeName) {
            AuthScreen(navHostController = navHostController)
        }

        composable(route = Screen.HomeScreen.routeName) {
            HomeScreen(navHostController)
        }
        composable(route = Screen.ChatScreen.routeName) {
            ChatScreen()
        }
    }
}