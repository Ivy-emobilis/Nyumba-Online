package com.example.nyumbaonline.ui.theme.screens.dashboards

import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseOutCirc
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.example.nyumbaonline.data.ManagementViewModel
import com.example.nyumbaonline.data.TenantViewModel
import com.example.nyumbaonline.models.ManagementData
import com.example.nyumbaonline.models.TenantModel
import com.example.nyumbaonline.navigation.ROUTE_CHAT_ROOM_LIST
import com.example.nyumbaonline.navigation.ROUTE_GIVE_REVIEW
import kotlinx.coroutines.delay

// Define color scheme
//val beigeBg = Color(0xFFF5F1EC) // Light beige background
//val dustyRoseLight = Color(0xFFF0D9DF) // Pinkish card background
//val darkBrown = Color(0xFF47281F) // Header and text color
//val mediumBrown = Color(0xFF7D5A50) // Secondary text color
//val lightGray = Color(0xFFF7F7F7) // Card container color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TenantDashboard(
    navController: NavController,
    tenantViewModel: TenantViewModel,
    managementViewModel: ManagementViewModel,
    tenant: TenantModel
) {
    val context = LocalContext.current
    val showManagementDetailsDialog = remember { mutableStateOf(false) }
//    val managementDetails = managementViewModel.getManagementDetails(tenant.propertyId).observeAsState()
    val showEditDialog = remember { mutableStateOf(false) }
    val selectedItem = remember { mutableStateOf(0) }

    // Animation states for staggered card entry
    var showFirstRow by remember { mutableStateOf(false) }
    var showSecondRow by remember { mutableStateOf(false) }

    // Trigger staggered animations
    LaunchedEffect(Unit) {
        showFirstRow = true
        delay(400)
        showSecondRow = true
    }

    // Background gradient
    val gradient = Brush.verticalGradient(
        colors = listOf(dustyRoseLight.copy(alpha = 0.2f), beigeBg),
        startY = 0f,
        endY = 1200f
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = "Home",
                            tint = darkBrown,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Nyumba Online",
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            color = darkBrown,
                            fontFamily = FontFamily.Serif
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Profile action */ }) {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Profile",
                            tint = darkBrown
                        )
                    }
                    IconButton(onClick = { /* Notifications action */ }) {
                        Icon(
                            imageVector = Icons.Filled.Notifications,
                            contentDescription = "Notifications",
                            tint = darkBrown
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = beigeBg
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 4.dp
            ) {
                NavigationBarItem(
                    selected = selectedItem.value == 0,
                    onClick = {
                        selectedItem.value = 0
                        val intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, "Download Nyumba Online: https://download.com")
                            type = "text/plain"
                        }
                        context.startActivity(Intent.createChooser(intent, null))
                    },
                    icon = {
                        Icon(
                            Icons.Filled.Share,
                            contentDescription = "Share",
                            tint = if (selectedItem.value == 0) darkBrown else mediumBrown.copy(alpha = 0.6f),
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = {
                        Text(
                            "Share",
                            color = if (selectedItem.value == 0) darkBrown else mediumBrown.copy(alpha = 0.6f),
                            fontSize = 12.sp,
                            fontFamily = FontFamily.Serif
                        )
                    }
                )
                NavigationBarItem(
                    selected = selectedItem.value == 1,
                    onClick = {
                        selectedItem.value = 1
                        val intent = Intent(Intent.ACTION_DIAL).apply {
                            data = "tel:0700000000".toUri()
                        }
                        context.startActivity(intent)
                    },
                    icon = {
                        Icon(
                            Icons.Filled.Phone,
                            contentDescription = "Phone",
                            tint = if (selectedItem.value == 1) darkBrown else mediumBrown.copy(alpha = 0.6f),
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = {
                        Text(
                            "Phone",
                            color = if (selectedItem.value == 1) darkBrown else mediumBrown.copy(alpha = 0.6f),
                            fontSize = 12.sp,
                            fontFamily = FontFamily.Serif
                        )
                    }
                )
                NavigationBarItem(
                    selected = selectedItem.value == 2,
                    onClick = {
                        selectedItem.value = 2
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = "mailto:info@nyumbaonline.com".toUri()
                        }
                        context.startActivity(intent)
                    },
                    icon = {
                        Icon(
                            Icons.Filled.Email,
                            contentDescription = "Email",
                            tint = if (selectedItem.value == 2) darkBrown else mediumBrown.copy(alpha = 0.6f),
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = {
                        Text(
                            "Email",
                            color = if (selectedItem.value == 2) darkBrown else mediumBrown.copy(alpha = 0.6f),
                            fontSize = 12.sp,
                            fontFamily = FontFamily.Serif
                        )
                    }
                )
            }
        },
        containerColor = beigeBg
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // First Row of Cards (3 cards)
                AnimatedVisibility(
                    visible = showFirstRow,
                    enter = fadeIn() + slideInVertically(
                        initialOffsetY = { 50 },
                        animationSpec = tween(durationMillis = 500, easing = EaseOutCirc)
                    )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        ActionCard(
                            title = "Open Chatroom",
                            icon = Icons.Outlined.Chat,
                            onClick = { navController.navigate("$ROUTE_CHAT_ROOM_LIST/${tenant.propertyId}") }
                        )
                        ActionCard(
                            title = "Management Details",
                            icon = Icons.Outlined.Info,
                            onClick = { showManagementDetailsDialog.value = true }
                        )
                        ActionCard(
                            title = "Review",
                            icon = Icons.Outlined.Star,
                            onClick = { navController.navigate(ROUTE_GIVE_REVIEW) }
                        )
                    }
                }

                // Second Row of Cards (2 cards)
                AnimatedVisibility(
                    visible = showSecondRow,
                    enter = fadeIn() + slideInVertically(
                        initialOffsetY = { 70 },
                        animationSpec = tween(durationMillis = 600, easing = EaseOutCirc)
                    )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        ActionCard(
                            title = "Edit Profile",
                            icon = Icons.Outlined.Edit,
                            onClick = { showEditDialog.value = true }
                        )
                        ActionCard(
                            title = "Logout",
                            icon = Icons.Outlined.ExitToApp,
                            onClick = { /* TODO: Logout action */ }
                        )
                    }
                }
            }
        }
    }

    // Management Details Dialog
//    if (showManagementDetailsDialog.value) {
//        Dialog(onDismissRequest = { showManagementDetailsDialog.value = false }) {
//            Surface(
//                shape = RoundedCornerShape(16.dp),
//                color = darkBrown, // Using darkBrown instead of undefined saddleBrown
//                modifier = Modifier.padding(16.dp)
//            ) {
//                Column(
//                    modifier = Modifier
//                        .padding(16.dp),
//                    verticalArrangement = Arrangement.spacedBy(8.dp),
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Text(
//                        text = "Management Details",
//                        color = Color.White,
//                        fontWeight = FontWeight.Bold,
//                        style = MaterialTheme.typography.titleLarge
//                    )
//                    if (managementDetails.value != null) {
////                        val details = managementDetails.value!!
////                        Text("Name: ${details.fullname}", color = Color.White)
////                        Text("Email: ${details.email}", color = Color.White)
////                        Text("Company: ${details.company}", color = Color.White)
////                        Text("Nationality: ${details.nationality}", color = Color.White)
////                        Text("ID No: ${details.idNo}", color = Color.White)
////                        Text("Home County: ${details.homeCounty}", color = Color.White)
//                    } else {
//                        Text("Loading...", color = Color.White)
//                    }
//                    Spacer(modifier = Modifier.height(16.dp))
//                    Button(
//                        onClick = { showManagementDetailsDialog.value = false },
//                        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
//                    ) {
//                        Text("Close", color = darkBrown)
//                    }
//                }
//            }
//        }
//    }

    // Edit Profile Dialog
    if (showEditDialog.value) {
        var firstName by remember { mutableStateOf(tenant.firstName) }
        var lastName by remember { mutableStateOf(tenant.lastName) }
        var phoneNumber by remember { mutableStateOf(tenant.phoneNumber) }
        var idNumber by remember { mutableStateOf(tenant.idNumber) }
        var county by remember { mutableStateOf(tenant.county) }
        var estate by remember { mutableStateOf(tenant.estate) }
        var houseNumber by remember { mutableStateOf(tenant.houseNumber) }
        var password by remember { mutableStateOf(tenant.password) }

        AlertDialog(
            onDismissRequest = { showEditDialog.value = false },
            title = {
                Text(
                    text = "Edit Profile",
                    color = darkBrown,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Serif
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    TextField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        label = { Text("First Name", color = mediumBrown, fontSize = 14.sp, fontFamily = FontFamily.Serif) },
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = darkBrown,
                            unfocusedTextColor = darkBrown,
                            focusedLabelColor = darkBrown,
                            unfocusedLabelColor = mediumBrown,
                            focusedContainerColor = lightGray,
                            unfocusedContainerColor = lightGray
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = lastName,
                        onValueChange = { lastName = it },
                        label = { Text("Last Name", color = mediumBrown, fontSize = 14.sp, fontFamily = FontFamily.Serif) },
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = darkBrown,
                            unfocusedTextColor = darkBrown,
                            focusedLabelColor = darkBrown,
                            unfocusedLabelColor = mediumBrown,
                            focusedContainerColor = lightGray,
                            unfocusedContainerColor = lightGray
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = phoneNumber,
                        onValueChange = { phoneNumber = it },
                        label = { Text("Phone Number", color = mediumBrown, fontSize = 14.sp, fontFamily = FontFamily.Serif) },
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = darkBrown,
                            unfocusedTextColor = darkBrown,
                            focusedLabelColor = darkBrown,
                            unfocusedLabelColor = mediumBrown,
                            focusedContainerColor = lightGray,
                            unfocusedContainerColor = lightGray
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = idNumber,
                        onValueChange = { idNumber = it },
                        label = { Text("ID Number", color = mediumBrown, fontSize = 14.sp, fontFamily = FontFamily.Serif) },
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = darkBrown,
                            unfocusedTextColor = darkBrown,
                            focusedLabelColor = darkBrown,
                            unfocusedLabelColor = mediumBrown,
                            focusedContainerColor = lightGray,
                            unfocusedContainerColor = lightGray
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = county,
                        onValueChange = { county = it },
                        label = { Text("County", color = mediumBrown, fontSize = 14.sp, fontFamily = FontFamily.Serif) },
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = darkBrown,
                            unfocusedTextColor = darkBrown,
                            focusedLabelColor = darkBrown,
                            unfocusedLabelColor = mediumBrown,
                            focusedContainerColor = lightGray,
                            unfocusedContainerColor = lightGray
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = estate,
                        onValueChange = { estate = it },
                        label = { Text("Estate", color = mediumBrown, fontSize = 14.sp, fontFamily = FontFamily.Serif) },
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = darkBrown,
                            unfocusedTextColor = darkBrown,
                            focusedLabelColor = darkBrown,
                            unfocusedLabelColor = mediumBrown,
                            focusedContainerColor = lightGray,
                            unfocusedContainerColor = lightGray
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = houseNumber,
                        onValueChange = { houseNumber = it },
                        label = { Text("House Number", color = mediumBrown, fontSize = 14.sp, fontFamily = FontFamily.Serif) },
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = darkBrown,
                            unfocusedTextColor = darkBrown,
                            focusedLabelColor = darkBrown,
                            unfocusedLabelColor = mediumBrown,
                            focusedContainerColor = lightGray,
                            unfocusedContainerColor = lightGray
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password", color = mediumBrown, fontSize = 14.sp, fontFamily = FontFamily.Serif) },
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = darkBrown,
                            unfocusedTextColor = darkBrown,
                            focusedLabelColor = darkBrown,
                            unfocusedLabelColor = mediumBrown,
                            focusedContainerColor = lightGray,
                            unfocusedContainerColor = lightGray
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val updatedTenant = tenant.copy(
                            firstName = firstName,
                            lastName = lastName,
                            phoneNumber = phoneNumber,
                            idNumber = idNumber,
                            county = county,
                            estate = estate,
                            houseNumber = houseNumber,
                            password = password
                        )
                        tenantViewModel.updateTenantDetails(
                            tenantId = tenant.id.toString(),
                            updatedTenant = updatedTenant,
                            onSuccess = {
                                Toast.makeText(context, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                                showEditDialog.value = false
                            },
                            onFailure = {
                                Toast.makeText(context, "Failed to update profile: ${it.message}", Toast.LENGTH_SHORT).show()
                            }
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = darkBrown),
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text("Save", color = Color.White, fontSize = 16.sp, fontFamily = FontFamily.Serif)
                }
            },
            dismissButton = {
                Button(
                    onClick = { showEditDialog.value = false },
                    colors = ButtonDefaults.buttonColors(containerColor = mediumBrown),
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text("Cancel", color = Color.White, fontSize = 16.sp, fontFamily = FontFamily.Serif)
                }
            },
            containerColor = lightGray,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun ActionCard(title: String, icon: ImageVector, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .height(140.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = lightGray)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(dustyRoseLight, RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = darkBrown,
                        modifier = Modifier.size(28.dp)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = title,
                    color = darkBrown,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Serif,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
    }
}