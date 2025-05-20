package com.example.nyumbaonline.ui.theme.screens.Chatroom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nyumbaonline.data.ChatViewModel
import com.example.nyumbaonline.models.ChatRoom
import com.example.nyumbaonline.models.Message
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    roomId: String? = null, // Made nullable to handle chatroom list screen
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel = viewModel(),
    onRoomClick: (String) -> Unit = {} // Callback for room selection
) {
    val allMessages by viewModel.messages.collectAsState()
    val messages = if (roomId != null) allMessages[roomId] ?: emptyList() else emptyList()
    val rooms by viewModel.chatRooms.collectAsState()
    val room = if (roomId != null) {
        rooms.find { it.id == roomId } ?: ChatRoom(id = roomId, name = "Chat Room")
    } else {
        null
    }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var messageText by remember { mutableStateOf("") }

    // Auto-scroll to bottom when new messages arrive
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Scaffold(
        topBar = {
            if (roomId == null) {
                // Chatroom list top bar
                TopAppBar(
                    title = {
                        Text(
                            text = "Nyumba Online",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF8B4513) // Brown color from screenshot
                        )
                    },
                    actions = {
                        IconButton(onClick = { /* Search action */ }) {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = "Search",
                                tint = Color(0xFF8B4513)
                            )
                        }
                        IconButton(onClick = { /* Profile action */ }) {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = "Profile",
                                tint = Color(0xFF8B4513)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFFF5E9D8), // Light beige background
                        titleContentColor = Color(0xFF8B4513),
                        actionIconContentColor = Color(0xFF8B4513)
                    )
                )
            } else {
                // Individual chat top bar
                TopAppBar(
                    title = {
                        Column {
                            Text(
                                text = room?.name ?: "Chat Room",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color(0xFF8B4513)
                            )
                            if (room?.description?.isNotBlank() == true) {
                                Text(
                                    text = room.description,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF8B4513).copy(alpha = 0.7f)
                                )
                            }
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color(0xFF8B4513)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFFF5E9D8),
                        titleContentColor = Color(0xFF8B4513),
                        navigationIconContentColor = Color(0xFF8B4513)
                    )
                )
            }
        },
        bottomBar = {
            if (roomId == null) {
                // Bottom navigation bar for chatroom list
                NavigationBar(
                    containerColor = Color(0xFF4A5E6D), // Dark gray from screenshot
                    contentColor = Color.White
                ) {
                    NavigationBarItem(
                        selected = true,
                        onClick = {},
                        icon = {
                            Icon(
                                Icons.Default.ChatBubble,
                                contentDescription = "Chat",
                                tint = Color.White
                            )
                        },
                        label = { Text("Chat", color = Color.White) }
                    )
                    NavigationBarItem(
                        selected = false,
                        onClick = {},
                        icon = {
                            Icon(
                                Icons.Default.Phone,
                                contentDescription = "Phone",
                                tint = Color.White
                            )
                        },
                        label = { Text("Phone", color = Color.White) }
                    )
                    NavigationBarItem(
                        selected = false,
                        onClick = {},
                        icon = {
                            Icon(
                                Icons.Default.Email,
                                contentDescription = "Email",
                                tint = Color.White
                            )
                        },
                        label = { Text("Email", color = Color.White) }
                    )
                    NavigationBarItem(
                        selected = false,
                        onClick = {},
                        icon = {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = "Profile",
                                tint = Color.White
                            )
                        },
                        label = { Text("Profile", color = Color.White) }
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF5E9D8)) // Light beige background
        ) {
            if (roomId == null) {
                // Chatroom list UI
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    // Welcome message
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "User Icon",
                            tint = Color(0xFF8B4513),
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE6D5C3)) // Light pink circle
                                .padding(8.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Welcome,",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF8B4513)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Chatrooms header
                    Text(
                        text = "Chatrooms",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF8B4513)
                    )
                    Text(
                        text = "Communicate with tenants",
                        fontSize = 14.sp,
                        color = Color(0xFF8B4513).copy(alpha = 0.7f)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Chatroom list
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(rooms) { chatRoom ->
                            ChatRoomItem(
                                chatRoom = chatRoom,
                                onClick = { onRoomClick(chatRoom.id) }
                            )
                        }
                    }
                }

                // Floating action button
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    FloatingActionButton(
                        onClick = { /* Add new chatroom */ },
                        containerColor = Color(0xFF8B4513), // Brown FAB
                        contentColor = Color.White,
                        shape = CircleShape
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Add Chatroom"
                        )
                    }
                }
            } else {
                // Individual chat UI
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFF5E9D8))
                ) {
                    // Message list
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        state = listState,
                        contentPadding = PaddingValues(vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(messages) { message ->
                            MessageItem(message = message)
                        }
                    }

                    // Message input field
                    ChatInputField(
                        value = messageText,
                        onValueChange = { messageText = it },
                        onSendClick = {
                            if (messageText.isNotBlank()) {
                                viewModel.sendMessage(messageText, roomId)
                                messageText = ""
                                coroutineScope.launch {
                                    if (messages.isNotEmpty()) {
                                        listState.animateScrollToItem(messages.size)
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ChatRoomItem(
    chatRoom: ChatRoom,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ChatBubble,
                contentDescription = "Chat Icon",
                tint = Color(0xFF8B4513),
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE6D5C3)) // Light pink circle
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = chatRoom.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF8B4513)
                )
                Text(
                    text = chatRoom.description,
                    fontSize = 14.sp,
                    color = Color(0xFF8B4513).copy(alpha = 0.7f),
                    maxLines = 1
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = formatChatRoomTime(System.currentTimeMillis()), // Placeholder time
                    fontSize = 12.sp,
                    color = Color(0xFF8B4513).copy(alpha = 0.7f)
                )
                if (chatRoom.unreadCount > 0) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF8B4513)), // Brown unread count
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = chatRoom.unreadCount.toString(),
                            fontSize = 12.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MessageItem(message: Message) {
    val isFromCurrentUser = message.isFromCurrentUser
    val backgroundColor = if (isFromCurrentUser) {
        Color(0xFF8B4513) // Brown for current user
    } else {
        Color.White
    }
    val contentColor = if (isFromCurrentUser) {
        Color.White
    } else {
        Color(0xFF8B4513)
    }
    val alignment = if (isFromCurrentUser) {
        Alignment.End
    } else {
        Alignment.Start
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = alignment
    ) {
        if (!isFromCurrentUser) {
            Text(
                text = message.sender,
                style = MaterialTheme.typography.labelMedium,
                color = Color(0xFF8B4513),
                modifier = Modifier.padding(start = 4.dp, bottom = 2.dp)
            )
        }

        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (isFromCurrentUser) 16.dp else 0.dp,
                        bottomEnd = if (isFromCurrentUser) 0.dp else 16.dp
                    )
                )
                .background(backgroundColor)
                .padding(12.dp)
                .widthIn(max = 280.dp)
        ) {
            Text(
                text = message.content,
                color = contentColor,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = formatMessageTime(message.timestamp),
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF8B4513).copy(alpha = 0.7f),
            modifier = Modifier.padding(horizontal = 4.dp)
        )
    }
}

@Composable
fun ChatInputField(
    value: String,
    onValueChange: (String) -> Unit,
    onSendClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        color = Color.White,
//        elevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp),
                placeholder = { Text("Type a message", color = Color(0xFF8B4513).copy(alpha = 0.7f)) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color(0xFF8B4513),
                    unfocusedTextColor = Color(0xFF8B4513)
                ),
                maxLines = 4
            )

            Spacer(modifier = Modifier.width(4.dp))

            IconButton(
                onClick = onSendClick,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color(0xFF8B4513))
                    .size(48.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send",
                    tint = Color.White
                )
            }
        }
    }
}

fun formatMessageTime(timestamp: Long): String {
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

fun formatChatRoomTime(timestamp: Long): String {
    val sdf = SimpleDateFormat("EEE", Locale.getDefault()) // e.g., "Mon", "Sun"
    return sdf.format(Date(timestamp))
}