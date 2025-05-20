package com.example.nyumbaonline.ui.theme.screens

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.nyumbaonline.data.PropertyViewModel
import kotlinx.coroutines.delay

// Color scheme from ManagementDashboard
val beigeBg = Color(0xFFF5F1EC)  // Light beige background
val dustyRose = Color(0xFF8D6E63) // Primary accent color
val dustyRoseDark = Color(0xFFC38694) // Darker accent for gradients
val dustyRoseLight = Color(0xFFF0D9DF) // Lighter accent for backgrounds
val darkBrown = Color(0xFF47281F)  // For headers and important text
val mediumBrown = Color(0xFF7D5A50) // For regular text
val lightGray = Color(0xFFF7F7F7) // For card backgrounds

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PropertyScreen(
    navController: NavController,
    managementId: String
) {
    val propertyViewModel: PropertyViewModel = viewModel()
    val properties = propertyViewModel.properties
    val context = LocalContext.current
    val selectedItem = remember { mutableStateOf(0) }

    // Fetch properties when entering the screen
    LaunchedEffect(managementId) {
        propertyViewModel.fetchPropertiesForManagement(managementId)
    }

    // Animation states
    var isLoaded by remember { mutableStateOf(false) }
    var showProperties by remember { mutableStateOf(false) }

    // Staggered animations
    LaunchedEffect(Unit) {
        isLoaded = true
        delay(300)
        showProperties = true
    }

    // Background gradient
    val gradient = Brush.verticalGradient(
        colors = listOf(dustyRoseLight.copy(alpha = 0.3f), beigeBg),
        startY = 0f,
        endY = 1000f
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = Icons.Filled.Home,
                            contentDescription = null,
                            tint = dustyRose
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Nyumba Online",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = darkBrown,
                            fontFamily = FontFamily.Serif
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* To be implemented later */ }) {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Profile",
                            tint = dustyRose
                        )
                    }
                    IconButton(onClick = { /* To be implemented later */ }) {
                        Icon(
                            imageVector = Icons.Filled.Notifications,
                            contentDescription = "Notifications",
                            tint = dustyRose
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
                            tint = if (selectedItem.value == 0) dustyRose else mediumBrown.copy(alpha = 0.6f)
                        )
                    },
                    label = {
                        Text(
                            "Share",
                            color = if (selectedItem.value == 0) dustyRose else mediumBrown.copy(alpha = 0.6f),
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
                            tint = if (selectedItem.value == 1) dustyRose else mediumBrown.copy(alpha = 0.6f)
                        )
                    },
                    label = {
                        Text(
                            "Phone",
                            color = if (selectedItem.value == 1) dustyRose else mediumBrown.copy(alpha = 0.6f),
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
                            tint = if (selectedItem.value == 2) dustyRose else mediumBrown.copy(alpha = 0.6f)
                        )
                    },
                    label = {
                        Text(
                            "Email",
                            color = if (selectedItem.value == 2) dustyRose else mediumBrown.copy(alpha = 0.6f),
                            fontSize = 12.sp,
                            fontFamily = FontFamily.Serif
                        )
                    }
                )
            }
        },
        containerColor = beigeBg
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Welcome Header
                AnimatedVisibility(
                    visible = isLoaded,
                    enter = fadeIn(
                        initialAlpha = 0.3f,
                        animationSpec = tween(durationMillis = 500)
                    ) + slideInVertically(
                        initialOffsetY = { -40 },
                        animationSpec = tween(durationMillis = 500, easing = androidx.compose.animation.core.EaseOutCirc)
                    )
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = lightGray),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(dustyRoseLight, RoundedCornerShape(12.dp))
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Welcome,",
                                fontSize = 18.sp,
                                color = mediumBrown,
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                // Properties Section
                AnimatedVisibility(
                    visible = showProperties,
                    enter = fadeIn() + slideInVertically(
                        initialOffsetY = { 40 },
                        animationSpec = tween(durationMillis = 400)
                    )
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Properties",
                            fontSize = 18.sp,
                            color = darkBrown,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Text(
                            text = "View your properties",
                            fontSize = 13.sp,
                            color = mediumBrown,
                            fontFamily = FontFamily.Serif,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        if (properties.isEmpty()) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No properties found.",
                                    color = mediumBrown,
                                    fontFamily = FontFamily.Serif
                                )
                            }
                        } else {
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(properties) { property ->
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                navController.navigate("PropertyDashboard/${property.id}")
                                            },
                                        colors = CardDefaults.cardColors(containerColor = lightGray),
                                        shape = RoundedCornerShape(16.dp),
                                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .padding(16.dp)
                                                .fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(40.dp)
                                                    .background(dustyRoseLight, RoundedCornerShape(12.dp))
                                            )
                                            Spacer(modifier = Modifier.width(12.dp))
                                            Column(modifier = Modifier.weight(1f)) {
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween
                                                ) {
                                                    Text(
                                                        text = property.name,
                                                        fontSize = 16.sp,
                                                        color = darkBrown,
                                                        fontFamily = FontFamily.Serif,
                                                        fontWeight = FontWeight.SemiBold
                                                    )
                                                    Text(
                                                        text = "Today",
                                                        fontSize = 12.sp,
                                                        color = mediumBrown,
                                                        fontFamily = FontFamily.Serif
                                                    )
                                                }
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                    text = "Units: ${property.numberOfUnits} • ${property.address}",
                                                    fontSize = 13.sp,
                                                    color = mediumBrown,
                                                    fontFamily = FontFamily.Serif
                                                )
                                            }
                                            Icon(
                                                imageVector = Icons.Outlined.KeyboardArrowRight,
                                                contentDescription = null,
                                                tint = dustyRose,
                                                modifier = Modifier.size(24.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}