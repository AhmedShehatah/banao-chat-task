package com.ahmed.banaochattask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.ahmed.banaochattask.core.navigation.AppNavHost
import com.ahmed.banaochattask.ui.theme.BanaoChatTaskTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BanaoChatTaskTheme {
                // A surface container using the 'background' color from the theme
                AppNavHost(navHostController = rememberNavController())
            }
        }
    }
}
