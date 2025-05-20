package  com.example.nyumbaonline.data
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nyumbaonline.models.ChatRoom
import com.example.nyumbaonline.models.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    // States
    private val _chatRooms = MutableStateFlow<List<ChatRoom>>(emptyList())
    val chatRooms: StateFlow<List<ChatRoom>> = _chatRooms.asStateFlow()

    private val _messages = MutableStateFlow<Map<String, List<Message>>>(emptyMap())
    val messages: StateFlow<Map<String, List<Message>>> = _messages.asStateFlow()

    private val _selectedRoomId = MutableStateFlow<String?>(null)
    val selectedRoomId: StateFlow<String?> = _selectedRoomId.asStateFlow()

    // Mock user data - in a real app, get this from user authentication
    private val currentUserId = "current_user"
    private val currentUserName = "Current User"

    init {
        // Initialize with some mock data
        initializeMockData()
    }

    private fun initializeMockData() {
        // Create some mock chat rooms
        val rooms = listOf(
            ChatRoom(
                id = "room1",
                name = "Apartment Listings",
                description = "Discuss available apartments",
                participants = listOf(currentUserId, "agent1", "agent2"),
                lastMessage = "Are there any 2-bedroom apartments available?",
                lastMessageTimestamp = System.currentTimeMillis() - 3600000, // 1 hour ago
                unreadCount = 0
            ),
            ChatRoom(
                id = "room2",
                name = "House Viewings",
                description = "Schedule property viewings",
                participants = listOf(currentUserId, "agent3"),
                lastMessage = "I'd like to view the property on Saturday",
                lastMessageTimestamp = System.currentTimeMillis() - 86400000, // 1 day ago
                unreadCount = 2
            ),
            ChatRoom(
                id = "room3",
                name = "Rental Inquiries",
                description = "Information about rentals",
                participants = listOf(currentUserId, "agent4", "agent5"),
                lastMessage = "What's the minimum lease period?",
                lastMessageTimestamp = System.currentTimeMillis() - 172800000, // 2 days ago
                unreadCount = 0
            )
        )
        _chatRooms.value = rooms

        // Create some mock messages for each room
        val roomMessages = mutableMapOf<String, List<Message>>()

        // Messages for Room 1
        roomMessages["room1"] = listOf(
            Message(
                content = "Welcome to Apartment Listings!",
                sender = "Agent Smith",
                senderId = "agent1",
                isFromCurrentUser = false,
                roomId = "room1"
            ),
            Message(
                content = "We have several new listings this week.",
                sender = "Agent Smith",
                senderId = "agent1",
                isFromCurrentUser = false,
                roomId = "room1"
            ),
            Message(
                content = "Are there any 2-bedroom apartments available?",
                sender = currentUserName,
                senderId = currentUserId,
                isFromCurrentUser = true,
                roomId = "room1"
            )
        )

        // Messages for Room 2
        roomMessages["room2"] = listOf(
            Message(
                content = "Hello! I can help schedule viewings",
                sender = "Agent Johnson",
                senderId = "agent3",
                isFromCurrentUser = false,
                roomId = "room2"
            ),
            Message(
                content = "I'd like to view the property on Saturday",
                sender = currentUserName,
                senderId = currentUserId,
                isFromCurrentUser = true,
                roomId = "room2"
            ),
            Message(
                content = "Sure, what time works for you?",
                sender = "Agent Johnson",
                senderId = "agent3",
                isFromCurrentUser = false,
                roomId = "room2"
            ),
            Message(
                content = "We have slots at 10 AM and 2 PM",
                sender = "Agent Johnson",
                senderId = "agent3",
                isFromCurrentUser = false,
                roomId = "room2"
            )
        )

        // Messages for Room 3
        roomMessages["room3"] = listOf(
            Message(
                content = "Welcome to Rental Inquiries",
                sender = "Agent Davis",
                senderId = "agent4",
                isFromCurrentUser = false,
                roomId = "room3"
            ),
            Message(
                content = "What's the minimum lease period?",
                sender = currentUserName,
                senderId = currentUserId,
                isFromCurrentUser = true,
                roomId = "room3"
            )
        )

        _messages.value = roomMessages

        // Select the first room by default
        if (rooms.isNotEmpty()) {
            _selectedRoomId.value = rooms.first().id
        }
    }

    fun selectChatRoom(roomId: String) {
        _selectedRoomId.value = roomId

        // Mark messages as read when selecting a room
        _chatRooms.update { rooms ->
            rooms.map {
                if (it.id == roomId) it.copy(unreadCount = 0)
                else it
            }
        }
    }

    fun sendMessage(content: String, roomId: String) {
        if (content.isBlank() || roomId.isBlank()) return

        val newMessage = Message(
            content = content.trim(),
            sender = currentUserName,
            senderId = currentUserId,
            isFromCurrentUser = true,
            roomId = roomId
        )

        // Add message to the room's message list
        _messages.update { currentMessages ->
            val roomMessages = currentMessages[roomId] ?: emptyList()
            currentMessages + (roomId to roomMessages + newMessage)
        }

        // Update the last message in the chat room list
        _chatRooms.update { rooms ->
            rooms.map { room ->
                if (room.id == roomId) {
                    room.copy(
                        lastMessage = content.trim(),
                        lastMessageTimestamp = System.currentTimeMillis()
                    )
                } else {
                    room
                }
            }
        }

        // Simulate response for demo purposes
        simulateResponse(roomId)
    }

    private fun simulateResponse(roomId: String) {
        viewModelScope.launch {
            kotlinx.coroutines.delay(1500) // Simulate network delay

            val room = _chatRooms.value.find { it.id == roomId } ?: return@launch
            val agentId = room.participants.find { it != currentUserId } ?: "agent1"
            val agentName = when (agentId) {
                "agent1" -> "Agent Smith"
                "agent2" -> "Agent Brown"
                "agent3" -> "Agent Johnson"
                "agent4" -> "Agent Davis"
                "agent5" -> "Agent Wilson"
                else -> "Agent"
            }

            val responseMessage = Message(
                content = "Thanks for your message! How else can I help you with your property search today?",
                sender = agentName,
                senderId = agentId,
                isFromCurrentUser = false,
                roomId = roomId
            )

            // Add response to messages
            _messages.update { currentMessages ->
                val roomMessages = currentMessages[roomId] ?: emptyList()
                currentMessages + (roomId to roomMessages + responseMessage)
            }

            // Update chat room with last message
            _chatRooms.update { rooms ->
                rooms.map { room ->
                    if (room.id == roomId) {
                        room.copy(
                            lastMessage = responseMessage.content,
                            lastMessageTimestamp = responseMessage.timestamp
                        )
                    } else {
                        room
                    }
                }
            }
        }
    }

    // Create a new chat room
    fun createChatRoom(name: String, description: String, participants: List<String>) {
        val newRoomId = "room${_chatRooms.value.size + 1}"

        val newRoom = ChatRoom(
            id = newRoomId,
            name = name,
            description = description,
            participants = participants + currentUserId,
            lastMessage = "Chat room created",
            lastMessageTimestamp = System.currentTimeMillis()
        )

        // Add new room to the list
        _chatRooms.update { it + newRoom }

        // Initialize empty message list for the room
        _messages.update { it + (newRoomId to emptyList()) }

        // Add system message to the new room
        val systemMessage = Message(
            content = "Welcome to $name! This room was just created.",
            sender = "System",
            senderId = "system",
            isFromCurrentUser = false,
            roomId = newRoomId
        )

        _messages.update { currentMessages ->
            currentMessages + (newRoomId to listOf(systemMessage))
        }
    }
}