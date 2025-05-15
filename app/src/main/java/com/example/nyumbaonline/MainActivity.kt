package com.example.nyumbaonline

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.nyumbaonline.navigation.AppNavHost
import com.example.nyumbaonline.ui.theme.NyumbaOnlineTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NyumbaOnlineTheme {
                AppNavHost()
                }
            }
        }
    }


