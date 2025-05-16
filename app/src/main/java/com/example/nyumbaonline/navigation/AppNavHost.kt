package com.example.nyumbaonline.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nyumbaonline.ui.theme.screens.SplashScreen
import com.example.nyumbaonline.ui.theme.screens.dashboards.ManagementDashboard
import com.example.nyumbaonline.ui.theme.screens.dashboards.TenantDashboard
import com.example.nyumbaonline.ui.theme.screens.login.Login
import com.example.nyumbaonline.ui.theme.screens.register.Register
import com.example.nyumbaonline.data.TenantViewModel
import com.example.nyumbaonline.models.TenantModel

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUTE_SPLASH,
    tenantViewModel: TenantViewModel
) {
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
        composable(ROUTE_TENANT_DASHBOARD) {
            val tenant = TenantModel() // Replace with actual tenant data retrieval logic
            TenantDashboard(navController, tenantViewModel, tenant)
        }
        composable(ROUTE_MANAGEMENT_DASHBOARD) { ManagementDashboard(navController) }
    }
}