package com.example.nyumbaonline.ui.theme.screens.Chatroom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nyumbaonline.data.ChatViewModel
import com.example.nyumbaonline.models.ChatRoom
import java.text.SimpleDateFormat
import java.util.*

// Define color palette to match the image
val BrownAccent = Color(0xFF8B5A2B)
val LightBeige = Color(0xFFF5F0E8)
val PurpleAccent = Color(0xFFB14DB1)
val TextBrown = Color(0xFF6D4C41)
val CardBackground = Color.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatRoomListScreen(
    onNavigateToChat: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel = viewModel()
) {
    val chatRooms by viewModel.chatRooms.collectAsState()
    var showNewChatDialog by remember { mutableStateOf(false) }

    // Custom theme colors
    val customColorScheme = lightColorScheme(
        primary = BrownAccent,
        onPrimary = Color.White,
        primaryContainer = CardBackground,
        onPrimaryContainer = BrownAccent,
        surface = LightBeige,
        onSurface = TextBrown,
        onSurfaceVariant = TextBrown.copy(alpha = 0.7f),
        background = LightBeige
    )

    MaterialTheme(colorScheme = customColorScheme) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            LightBeige,
                            LightBeige.copy(alpha = 0.9f)
                        )
                    )
                )
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Custom app bar
                TopBar()

                // Welcome Card
                WelcomeCard(modifier = Modifier.padding(16.dp))

                // Title
                Text(
                    text = "Chatrooms",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = BrownAccent
                    ),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )

                Text(
                    text = "Communicate with tenants",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextBrown.copy(alpha = 0.7f),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )

                // Chat rooms list
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(chatRooms) { room ->
                        ChatRoomItem(
                            chatRoom = room,
                            onClick = {
                                viewModel.selectChatRoom(room.id)
                                onNavigateToChat(room.id)
                            }
                        )
                    }
                }
            }

            // Bottom navigation bar
            BottomNavBar(
                modifier = Modifier.align(Alignment.BottomCenter)
            )

            // Floating action button with custom design
            FloatingActionButton(
                onClick = { showNewChatDialog = true },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 80.dp, end = 16.dp)
                    .shadow(4.dp, CircleShape),
                containerColor = PurpleAccent,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Create New Chat Room",
                    tint = Color.White
                )
            }
        }

        // New Chat Room Dialog
        if (showNewChatDialog) {
            NewChatRoomDialog(
                onDismiss = { showNewChatDialog = false },
                onCreateRoom = { name, description ->
                    viewModel.createChatRoom(
                        name = name,
                        description = description,
                        participants = listOf("agent1") // Default agent for demo
                    )
                    showNewChatDialog = false
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        color = LightBeige,
        shadowElevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Home icon in a circle
            Surface(
                modifier = Modifier
                    .size(40.dp),
                shape = CircleShape,
                color = LightBeige,
                border = ButtonDefaults.outlinedButtonBorder
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "🏠",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            // App title
            Text(
                text = "Nyumba Online",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = BrownAccent
                )
            )

            // Right side icons
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // User profile icon
                Surface(
                    modifier = Modifier
                        .size(36.dp),
                    shape = CircleShape,
                    color = LightBeige,
                    border = ButtonDefaults.outlinedButtonBorder
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "👤",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

                // Search icon
                Surface(
                    modifier = Modifier
                        .size(36.dp),
                    shape = CircleShape,
                    color = LightBeige,
                    border = ButtonDefaults.outlinedButtonBorder
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "🔍",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

                // Close/menu icon
                Surface(
                    modifier = Modifier
                        .size(36.dp),
                    shape = CircleShape,
                    color = LightBeige,
                    border = ButtonDefaults.outlinedButtonBorder
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "✕",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun WelcomeCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBackground
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // User avatar
            Surface(
                modifier = Modifier
                    .size(60.dp),
                shape = CircleShape,
                color = Color(0xFFFFDADA),
                border = ButtonDefaults.outlinedButtonBorder
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "👤",
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = "Welcome,",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = BrownAccent
                    )
                )
            }
        }
    }
}

@Composable
fun ChatRoomItem(
    chatRoom: ChatRoom,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth(),
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = CardBackground,
        shadowElevation = 1.dp
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Chat room avatar with branding color
            Surface(
                modifier = Modifier
                    .size(50.dp),
                shape = CircleShape,
                color = PurpleAccent.copy(alpha = 0.2f)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "💬",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Chat room details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = chatRoom.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = TextBrown
                    )

                    Text(
                        text = formatChatTime(chatRoom.lastMessageTimestamp),
                        style = MaterialTheme.typography.bodySmall,
                        color = TextBrown.copy(alpha = 0.6f)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = chatRoom.lastMessage,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f),
                        color = TextBrown.copy(alpha = 0.7f)
                    )

                    if (chatRoom.unreadCount > 0) {
                        Surface(
                            shape = CircleShape,
                            color = PurpleAccent,
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Text(
                                text = chatRoom.unreadCount.toString(),
                                color = Color.White,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NewChatRoomDialog(
    onDismiss: () -> Unit,
    onCreateRoom: (name: String, description: String) -> Unit
) {
    var roomName by remember { mutableStateOf("") }
    var roomDescription by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = CardBackground,
        title = {
            Text(
                "Create New Chatroom",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = BrownAccent
                )
            )
        },
        text = {
            Column {
                OutlinedTextField(
                    value = roomName,
                    onValueChange = {
                        roomName = it
                        isError = it.isBlank()
                    },
                    label = { Text("Room Name") },
                    isError = isError,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PurpleAccent,
                        unfocusedBorderColor = TextBrown.copy(alpha = 0.5f)
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = roomDescription,
                    onValueChange = { roomDescription = it },
                    label = { Text("Description (Optional)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PurpleAccent,
                        unfocusedBorderColor = TextBrown.copy(alpha = 0.5f)
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (roomName.isNotBlank()) {
                        onCreateRoom(roomName, roomDescription)
                    } else {
                        isError = true
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = PurpleAccent,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Create")
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = TextBrown
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun BottomNavBar(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp),
        color = CardBackground,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Share Button
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF546E7A)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "↗",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Text(
                    text = "Share",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextBrown
                )
            }

            // Phone Button
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "📞",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Phone",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextBrown
                )
            }

            // Email Button
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "✉️",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Email",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextBrown
                )
            }
        }
    }
}

// Helper function to format chat time (unchanged)
fun formatChatTime(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp

    return when {
        diff < 60000 -> "Just now" // Less than a minute
        diff < 3600000 -> "${diff / 60000} min" // Less than an hour
        diff < 86400000 -> { // Less than a day
            val sdf = SimpleDateFormat("h:mm a", Locale.getDefault())
            sdf.format(Date(timestamp))
        }
        diff < 604800000 -> { // Less than a week
            val sdf = SimpleDateFormat("EEE", Locale.getDefault())
            sdf.format(Date(timestamp))
        }
        else -> { // More than a week
            val sdf = SimpleDateFormat("MMM d", Locale.getDefault())
            sdf.format(Date(timestamp))
        }
    }
}