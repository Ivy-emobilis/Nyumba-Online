package com.example.nyumbaonline

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.example.nyumbaonline.data.TenantViewModel
import com.example.nyumbaonline.navigation.AppNavHost
import com.example.nyumbaonline.ui.theme.NyumbaOnlineTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize TenantViewModel
        val tenantViewModel = ViewModelProvider(this)[TenantViewModel::class.java]

        setContent {
            NyumbaOnlineTheme {
                AppNavHost(tenantViewModel = tenantViewModel)
            }
        }
    }
}
