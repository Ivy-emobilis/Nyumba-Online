package com.example.nyumbaonline.ui.theme.screens.dashboards

import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.example.nyumbaonline.models.ManagementData
import com.example.nyumbaonline.navigation.ROUTE_CHAT_ROOM_LIST
import com.example.nyumbaonline.navigation.ROUTE_VIEW_PROPERTY
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Using the beige and dusty rose color scheme from registration screen
val beigeBg = Color(0xFFF5F1EC)  // Light beige background
val dustyRose = Color(0xFF8D6E63) // Primary accent color
val dustyRoseDark = Color(0xFFC38694) // Darker accent for gradients
val dustyRoseLight = Color(0xFFF0D9DF) // Lighter accent for backgrounds
val darkBrown = Color(0xFF47281F)  // For headers and important text
val mediumBrown = Color(0xFF7D5A50) // For regular text
val lightBrown = Color(0xFFBDA99F) // For subdued text and icons
val lightGray = Color(0xFFF7F7F7) // For card backgrounds

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManagementDashboard(navController: NavController, management: ManagementData) {
    val selectedItem = remember { mutableStateOf(0) }
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    // Animation states
    var isLoaded by remember { mutableStateOf(false) }
    var showStatCards by remember { mutableStateOf(false) }
    var showActionCards by remember { mutableStateOf(false) }

    // Staggered animations
    LaunchedEffect(Unit) {
        isLoaded = true
        delay(300)
        showStatCards = true
        delay(200)
        showActionCards = true
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
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp, bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Animated welcome profile card
                AnimatedVisibility(
                    visible = isLoaded,
                    enter = fadeIn(
                        initialAlpha = 0.3f,
                        animationSpec = tween(durationMillis = 500)
                    ) + slideInVertically(
                        initialOffsetY = { -40 },
                        animationSpec = tween(durationMillis = 500, easing = EaseOutCirc)
                    )
                ) {
                    WelcomeCard(management)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Stats section
                AnimatedVisibility(
                    visible = showStatCards,
                    enter = fadeIn() + slideInVertically(
                        initialOffsetY = { 40 },
                        animationSpec = tween(durationMillis = 400)
                    )
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(
                            text = "Your Dashboard",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = darkBrown,
                            fontFamily = FontFamily.Serif,
                            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                        )

                        // Stats row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            StatCard(
                                title = "Properties",
                                value = "5",
                                icon = Icons.Outlined.Home,
                                color = dustyRose,
                                modifier = Modifier.weight(1f)
                            )
                            StatCard(
                                title = "Tenants",
                                value = "12",
                                icon = Icons.Outlined.Group,
                                color = mediumBrown,
                                modifier = Modifier.weight(1f)
                            )
                            StatCard(
                                title = "Inquiries",
                                value = "3",
                                icon = Icons.Outlined.Email,
                                color = dustyRoseDark,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }

                // Action Cards Section
                AnimatedVisibility(
                    visible = showActionCards,
                    enter = fadeIn() + slideInVertically(
                        initialOffsetY = { 60 },
                        animationSpec = tween(durationMillis = 500)
                    )
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(
                            text = "Quick Actions",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = darkBrown,
                            fontFamily = FontFamily.Serif,
                            modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
                        )

                        DashboardActionCard(
                            title = "Add New Property",
                            description = "List a new property for rent or sale",
                            icon = Icons.Outlined.Add,
                            backgroundColor = dustyRose
                        ) {
                            Toast.makeText(context, "Add Property Clicked", Toast.LENGTH_SHORT).show()
                        }

                        DashboardActionCard(
                            title = "View Properties",
                            description = "Manage your listed properties",
                            icon = Icons.Outlined.Visibility,
                            backgroundColor = mediumBrown
                        ) {
                            val id = management.id
                            if (!id.isNullOrEmpty()) navController.navigate("$ROUTE_VIEW_PROPERTY/$id")
                        }

                        DashboardActionCard(
                            title = "Chat with Tenants",
                            description = "Message your tenants directly",
                            icon = Icons.Outlined.Chat,
                            backgroundColor = dustyRoseDark
                        ) {
                            navController.navigate(ROUTE_CHAT_ROOM_LIST)
                        }
                    }
                }

                // Recent activity section
                AnimatedVisibility(
                    visible = showActionCards,
                    enter = fadeIn(
                        initialAlpha = 0.4f,
                        animationSpec = tween(durationMillis = 800, delayMillis = 300)
                    )
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(
                            text = "Recent Activity",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = darkBrown,
                            fontFamily = FontFamily.Serif,
                            modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
                        )

                        RecentActivityItem(
                            title = "New tenant inquiry",
                            description = "For 2BR Apartment in Westlands",
                            time = "2 hours ago",
                            icon = Icons.Outlined.Person
                        )

                        RecentActivityItem(
                            title = "Rent payment received",
                            description = "KSh 45,000 from John Doe",
                            time = "Yesterday",
                            icon = Icons.Outlined.Payments
                        )

                        RecentActivityItem(
                            title = "Maintenance request",
                            description = "Plumbing issue in Unit 4B",
                            time = "2 days ago",
                            icon = Icons.Outlined.Build
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun WelcomeCard(management: ManagementData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile icon
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            colors = listOf(dustyRose, dustyRoseDark)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = management.fullname.take(1).uppercase(),
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Welcome back,",
                    fontSize = 14.sp,
                    color = mediumBrown,
                    fontFamily = FontFamily.Serif
                )
                Text(
                    text = management.fullname,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = darkBrown,
                    fontFamily = FontFamily.Serif
                )
                Text(
                    text = management.company,
                    fontSize = 14.sp,
                    color = dustyRose,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily.Serif
                )
            }

            IconButton(
                onClick = { /* Profile settings */ },
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(beigeBg)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = "Settings",
                    tint = dustyRose
                )
            }
        }
    }
}

@Composable
fun StatCard(title: String, value: String, icon: ImageVector, color: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Icon
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Value
            Text(
                text = value,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = darkBrown,
                fontFamily = FontFamily.Serif
            )

            // Title
            Text(
                text = title,
                fontSize = 12.sp,
                color = mediumBrown,
                fontFamily = FontFamily.Serif
            )
        }
    }
}

@Composable
fun DashboardActionCard(
    title: String,
    description: String,
    icon: ImageVector,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
            pressedElevation = 4.dp
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(backgroundColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Text content
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = darkBrown,
                    fontFamily = FontFamily.Serif
                )
                Text(
                    text = description,
                    fontSize = 13.sp,
                    color = mediumBrown,
                    fontFamily = FontFamily.Serif,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Arrow
            Icon(
                imageVector = Icons.Outlined.KeyboardArrowRight,
                contentDescription = null,
                tint = dustyRose,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun RecentActivityItem(title: String, description: String, time: String, icon: ImageVector) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(dustyRoseLight),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = dustyRose,
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Text content
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = darkBrown,
                    fontFamily = FontFamily.Serif
                )
                Text(
                    text = description,
                    fontSize = 12.sp,
                    color = mediumBrown,
                    fontFamily = FontFamily.Serif
                )
            }

            // Time
            Text(
                text = time,
                fontSize = 11.sp,
                color = mediumBrown,
                fontFamily = FontFamily.Serif
            )
        }
    }
}