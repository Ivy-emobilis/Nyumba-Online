package com.example.nyumbaonline.navigation

import android.window.SplashScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nyumbaonline.ui.theme.screens.dashboards.ManagementDashboard
import com.example.nyumbaonline.ui.theme.screens.dashboards.TenantDashboard
import com.example.nyumbaonline.ui.theme.screens.login.Login
import com.example.nyumbaonline.ui.theme.screens.register.Register

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController(), startDestination:String= ROUTE_SPLASH) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(ROUTE_SPLASH) {
            SplashScreen {
                navController.navigate(ROUTE_REGISTER) {
                    popUpTo(ROUTE_SPLASH) { inclusive = true }
                }
            }
        }
        composable(ROUTE_REGISTER) { Register(navController) }
        composable(ROUTE_LOGIN) { Login(navController) }
        composable(ROUTE_TENANT_DASHBOARD) { TenantDashboard(navController) }
        composable(ROUTE_MANAGEMENT_DASHBOARD) { ManagementDashboard(navController) }
    }
}