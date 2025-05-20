package com.example.nyumbaonline.navigation

import TenantModel
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.nyumbaonline.data.ChatViewModel
import com.example.nyumbaonline.ui.theme.screens.SplashScreen
import com.example.nyumbaonline.ui.theme.screens.dashboards.ManagementDashboard
import com.example.nyumbaonline.ui.theme.screens.dashboards.TenantDashboard
import com.example.nyumbaonline.ui.theme.screens.login.Login
import com.example.nyumbaonline.ui.theme.screens.register.Register
import com.example.nyumbaonline.data.TenantViewModel
import com.example.nyumbaonline.models.ManagementData
import com.example.nyumbaonline.ui.theme.screens.Chatroom.ChatRoom
import com.example.nyumbaonline.ui.theme.screens.Chatroom.ChatRoomListScreen
import com.example.nyumbaonline.ui.theme.screens.Chatroom.ChatScreen
import com.example.nyumbaonline.ui.theme.screens.PropertyScreen
import com.example.nyumbaonline.ui.theme.screens.dashboards.PropertyDashboard
import com.example.nyumbaonline.ui.theme.screens.Tenants.ViewTenants
import kotlin.text.get

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
        // In your AppNavHost
// Add this to your NavHost where your other composables are defined


// Add this to your existing NavHost
        composable(ROUTE_CHAT_ROOM_LIST) {
            val chatViewModel: ChatViewModel = viewModel()
            ChatRoomListScreen(
                onNavigateToChat = { chatId ->
                    // Navigate to chat screen with the chatId
                    navController.navigate("chat/$chatId")
                },
                viewModel = chatViewModel
            )
        }

// If you need to pass data to the ChatRoomListScreen, you can use savedStateHandle
// For example:
        /*
        composable(ROUTE_CHAT_ROOM_LIST) {
            val chatViewModel: ChatViewModel = viewModel()
            val userData = navController.previousBackStackEntry?.savedStateHandle?.get<UserData>("user_data")
            if (userData != null) {
                ChatRoomListScreen(
                    onNavigateToChat = { chatId ->
                        navController.navigate("chat_details/$chatId")
                    },
                    viewModel = chatViewModel,
                    userData = userData
                )
            }
        }
        */

// Chat screen composable for individual chat rooms

        composable(
            route = ROUTE_CHAT_SCREEN,
            arguments = listOf(
                navArgument("roomId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val roomId = backStackEntry.arguments?.getString("roomId") ?: ""
            val chatViewModel: ChatViewModel = viewModel()

            ChatScreen(
                roomId = roomId,
                onNavigateBack = {
                    navController.popBackStack()
                },
                viewModel = chatViewModel,
                modifier = Modifier.fillMaxSize()
            )
        }

//        composable (ROUTE_CHAT_ROOM){
//            ChatRoom()
//        }
        composable(ROUTE_REGISTER) { Register(navController) }
        composable(ROUTE_LOGIN) { Login(navController) }
        composable(ROUTE_TENANT_DASHBOARD) {
            val tenant =
                navController.previousBackStackEntry?.savedStateHandle?.get<TenantModel>("tenant")
            if (tenant != null) {
                TenantDashboard(navController, tenantViewModel, tenant)
            }
        }
        composable(
            route = "${ROUTE_VIEW_PROPERTY}/{managementId}"
        ) { backStackEntry ->
            val managementId = backStackEntry.arguments?.getString("managementId") ?: ""
            PropertyScreen(navController = navController, managementId = managementId)
        }
        composable(ROUTE_MANAGEMENT_DASHBOARD) {
            val management =
                navController.previousBackStackEntry?.savedStateHandle?.get<ManagementData>("management")
            if (management != null) {
                ManagementDashboard(navController, management)
            }
        }
        composable("PropertyDashboard/{propertyId}") { backStackEntry ->
            val propertyId = backStackEntry.arguments?.getString("propertyId") ?: ""
            PropertyDashboard(navController, propertyId)
        }
        composable("ViewTenants/{propertyId}") { backStackEntry ->
            val propertyId = backStackEntry.arguments?.getString("propertyId") ?: ""
            ViewTenants(
                navController = navController,
                tenantViewModel = tenantViewModel,
                propertyId = propertyId // Pass propertyId here
            )
        }
    }
}
