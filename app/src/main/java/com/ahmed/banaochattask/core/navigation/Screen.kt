package com.ahmed.banaochattask.core.navigation

sealed class Screen(val routeName: String) {
    data object AuthScreen : Screen("/auth-screen")
    data object HomeScreen : Screen("/home")
    data object ChatScreen : Screen("/chat")
}