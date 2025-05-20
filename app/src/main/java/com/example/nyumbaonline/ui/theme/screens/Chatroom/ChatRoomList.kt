package com.example.nyumbaonline.ui.theme.screens.Chatroom

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nyumbaonline.data.ChatViewModel
import com.example.nyumbaonline.models.ChatRoom
import java.text.SimpleDateFormat
import java.util.*

// Updated color palette to match the image
val BackgroundColor = Color(0xFFF5F5F5) // Light beige/white
val HeadingTextColor = Color(0xFF6D4C41) // Brown for headings
val SubTextColor = Color(0xFFA9A9A9) // Gray for subtext
val CardBackground = Color.White
val BorderColor = Color(0xFFE0E0E0) // Light border for input fields
val IconColor = Color(0xFF6D4C41) // Brown for icons
val PurpleAccent = Color(0xFFB14DB1) // Retain for FAB consistency

// Custom font family for the handwritten style (you may need to add this to your project resources)
//val HandwrittenFontFamily = FontFamily(
//    Font(/* Add your handwritten font resource here, e.g., R.font.snell_roundhand */)
//)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatRoomListScreen(
    onNavigateToChat: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel = viewModel()
) {
    val chatRooms by viewModel.chatRooms.collectAsState()
    var showNewChatDialog by remember { mutableStateOf(false) }

    // Updated color scheme to match the image
    val customColorScheme = lightColorScheme(
        primary = HeadingTextColor,
        onPrimary = Color.White,
        primaryContainer = CardBackground,
        onPrimaryContainer = HeadingTextColor,
        surface = BackgroundColor,
        onSurface = HeadingTextColor,
        onSurfaceVariant = SubTextColor,
        background = BackgroundColor
    )

    MaterialTheme(colorScheme = customColorScheme) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(BackgroundColor) // Flat background color to match the image
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Custom app bar with logo
                TopBar()

                // Title section
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Chatrooms",
//                        fontFamily = HandwrittenFontFamily, // Handwritten font for heading
                        fontSize = 32.sp, // Larger font size to match the image
                        color = HeadingTextColor,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = "Communicate with tenants",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 14.sp,
                            color = SubTextColor
                        ),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

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

            // Floating action button
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
                        participants = listOf("agent1")
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
            .height(100.dp), // Increased height to accommodate the logo
        color = BackgroundColor,
        shadowElevation = 0.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo placeholder (as in the image)
            Surface(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                color = CardBackground,
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    width = 1.dp,
//                    brush = Brush.solidColor(BorderColor)
                )
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "🏠",
                        style = MaterialTheme.typography.headlineMedium,
                        color = HeadingTextColor
                    )
                }
            }
        }
    }
}

@Composable
fun WelcomeCard(modifier: Modifier = Modifier) {
    // Remove the welcome card as it's not present in the image
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
        shape = RoundedCornerShape(8.dp), // Match the rounded corners of the input fields
        color = CardBackground,
        border = ButtonDefaults.outlinedButtonBorder.copy(
            width = 1.dp,
//            brush = Brush.solidColor(BorderColor)
        ),
        shadowElevation = 0.dp // No shadow to match the flat design
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Chat room avatar
            Surface(
                modifier = Modifier
                    .size(40.dp),
                shape = CircleShape,
                color = Color.Transparent
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "💬",
                        style = MaterialTheme.typography.titleMedium,
                        color = IconColor // Brown icon color
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
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 16.sp,
                            color = HeadingTextColor
                        ),
                        fontWeight = FontWeight.Medium
                    )

                    Text(
                        text = formatChatTime(chatRoom.lastMessageTimestamp),
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 12.sp,
                            color = SubTextColor
                        )
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
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 14.sp,
                            color = SubTextColor
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
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
                text = "Create New Chatroom",
//                fontFamily = HandwrittenFontFamily,
                fontSize = 24.sp,
                color = HeadingTextColor
            )
        },
        text = {
            Column {
                // Room Name Field
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(CardBackground, RoundedCornerShape(8.dp))
                        .border(1.dp, BorderColor, RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "🏠",
                        style = MaterialTheme.typography.bodyLarge,
                        color = IconColor,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    OutlinedTextField(
                        value = roomName,
                        onValueChange = {
                            roomName = it
                            isError = it.isBlank()
                        },
                        label = { Text("Room Name", color = SubTextColor) },
                        isError = isError,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp),
                        shape = RoundedCornerShape(0.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            focusedLabelColor = SubTextColor,
                            unfocusedLabelColor = SubTextColor
                        )
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Description Field
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(CardBackground, RoundedCornerShape(8.dp))
                        .border(1.dp, BorderColor, RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "📝",
                        style = MaterialTheme.typography.bodyLarge,
                        color = IconColor,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    OutlinedTextField(
                        value = roomDescription,
                        onValueChange = { roomDescription = it },
                        label = { Text("Description (Optional)", color = SubTextColor) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp),
                        shape = RoundedCornerShape(0.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            focusedLabelColor = SubTextColor,
                            unfocusedLabelColor = SubTextColor
                        )
                    )
                }
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
                    contentColor = HeadingTextColor
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
                Text(
                    text = "↗",
                    style = MaterialTheme.typography.titleLarge,
                    color = IconColor
                )
                Text(
                    text = "Share",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 12.sp,
                        color = SubTextColor
                    )
                )
            }

            // Phone Button
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "📞",
                    style = MaterialTheme.typography.titleLarge,
                    color = IconColor
                )
                Text(
                    text = "Phone",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 12.sp,
                        color = SubTextColor
                    )
                )
            }

            // Email Button
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "✉️",
                    style = MaterialTheme.typography.titleLarge,
                    color = IconColor
                )
                Text(
                    text = "Email",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 12.sp,
                        color = SubTextColor
                    )
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