package com.example.nyumbaonline.ui.theme.screens.dashboards

// ...existing imports...
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nyumbaonline.navigation.ROUTE_GIVE_REVIEW
import com.example.nyumbaonline.navigation.ROUTE_JOIN_CHATROOM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TenantDashboard(navController: NavController){
    val context = LocalContext.current
    // Nude color palette
    val nudeBackground = Color(0xFFF5EBDD)
    val nudeCard = Color(0xFFEADBC8)
    val nudeText = Color(0xFF7A5C3E)
    Scaffold  (
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .height(56.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                // Bottom bar with nude background and copyright text
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .height(56.dp)
                        .padding(bottom = 0.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "nyumbaonline@2025",
                        color = nudeText,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    )
    { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(nudeBackground)
        ) {
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 0.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TopAppBar(
                    title = { Text(text = "nyumba online", color = nudeText, fontWeight = FontWeight.Bold) },
                    actions = {
                        IconButton(onClick = { /* TODO: Search action */ }) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "Search",
                                tint = nudeText
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = nudeCard,
                        titleContentColor = nudeText,
                        actionIconContentColor = nudeText
                    )
                )
                // 5 Cards in 2 rows (3 + 2)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 32.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        // Open Chatroom
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .height(120.dp)
                                .clickable { navController.navigate(ROUTE_JOIN_CHATROOM) },
                            shape = RoundedCornerShape(20.dp),
                            elevation = CardDefaults.cardElevation(8.dp),
                            colors = CardDefaults.cardColors(nudeCard)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Text("Open Chatroom", color = nudeText, fontWeight = FontWeight.Medium)
                            }
                        }
                        // Management Details
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .height(120.dp)
                                .clickable { /* TODO: Navigate to management details */ },
                            shape = RoundedCornerShape(20.dp),
                            elevation = CardDefaults.cardElevation(8.dp),
                            colors = CardDefaults.cardColors(nudeCard)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Text("Management Details", color = nudeText, fontWeight = FontWeight.Medium)
                            }
                        }
                        // Review
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .height(120.dp)
                                .clickable { navController.navigate(ROUTE_GIVE_REVIEW) },
                            shape = RoundedCornerShape(20.dp),
                            elevation = CardDefaults.cardElevation(8.dp),
                            colors = CardDefaults.cardColors(nudeCard)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Text("Review", color = nudeText, fontWeight = FontWeight.Medium)
                            }
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        // Edit Profile
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .height(120.dp)
                                .clickable { /* TODO: Navigate to edit profile */ },
                            shape = RoundedCornerShape(20.dp),
                            elevation = CardDefaults.cardElevation(8.dp),
                            colors = CardDefaults.cardColors(nudeCard)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Text("Edit Profile", color = nudeText, fontWeight = FontWeight.Medium)
                            }
                        }
                        // Logout
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .height(120.dp)
                                .clickable { /* TODO: Logout action */ },
                            shape = RoundedCornerShape(20.dp),
                            elevation = CardDefaults.cardElevation(8.dp),
                            colors = CardDefaults.cardColors(nudeCard)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Text("Logout", color = nudeText, fontWeight = FontWeight.Medium)
                            }
                        }
                        // Empty space to balance the row
                        Box(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun Tenant_dash_view() {
    TenantDashboard(rememberNavController())
}
