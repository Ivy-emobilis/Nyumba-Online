package com.example.nyumbaonline.ui.theme.screens.dashboards

import android.content.Intent
import android.net.Uri


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.nyumbaonline.R
import com.example.nyumbaonline.data.PropertyViewModel
import com.example.nyumbaonline.models.ManagementData
import com.example.nyumbaonline.models.PropertyData
import com.example.nyumbaonline.navigation.ROUTE_CHAT_ROOM_LIST
import com.example.nyumbaonline.navigation.ROUTE_VIEW_PROPERTY
import com.example.nyumbaonline.ui.theme.brown
import com.example.nyumbaonline.ui.theme.saddleBrown
import kotlinx.coroutines.launch
import android.widget.Toast
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Visibility

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManagementDashboard(navController: NavController, management: ManagementData) {
    val selectedItem = remember { mutableStateOf(0) }
    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }
    val propertyViewModel: PropertyViewModel = viewModel()
    val scope = rememberCoroutineScope()

    // Define theme colors
    val primaryColor = saddleBrown
    val secondaryColor = brown
    val accentColor = Color(0xFF9C27B0) // Purple accent
    val backgroundColor = Color(0xFFF5F5F5)
    val cardBackgroundColor = Color.White

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Nyumba Online",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {},
                        modifier = Modifier
                            .padding(8.dp)
                            .size(40.dp)
                            .background(primaryColor.copy(alpha = 0.1f), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = "Home",
                            tint = primaryColor
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {},
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .size(40.dp)
                            .background(primaryColor.copy(alpha = 0.1f), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Profile",
                            tint = primaryColor
                        )
                    }
                    IconButton(
                        onClick = {},
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .size(40.dp)
                            .background(primaryColor.copy(alpha = 0.1f), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search",
                            tint = primaryColor
                        )
                    }
                    IconButton(
                        onClick = {},
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .size(40.dp)
                            .background(primaryColor.copy(alpha = 0.1f), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Logout",
                            tint = primaryColor
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = cardBackgroundColor,
                    titleContentColor = primaryColor
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = cardBackgroundColor,
                contentColor = primaryColor,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = selectedItem.value == 0,
                    onClick = {
                        selectedItem.value = 0
                        val sendIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, "Download Nyumba Online: https://www.download.com")
                            type = "text/plain"
                        }
                        val shareIntent = Intent.createChooser(sendIntent, null)
                        context.startActivity(shareIntent)
                    },
                    icon = {
                        Icon(
                            Icons.Filled.Share,
                            contentDescription = "Share",
                            tint = if (selectedItem.value == 0) primaryColor else Color.Gray
                        )
                    },
                    label = {
                        Text(
                            text = "Share",
                            color = if (selectedItem.value == 0) primaryColor else Color.Gray,
                            fontWeight = if (selectedItem.value == 0) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
                NavigationBarItem(
                    selected = selectedItem.value == 1,
                    onClick = {
                        selectedItem.value = 1
                        val intent = Intent(Intent.ACTION_DIAL).apply {
                            data = "tel: 0700000000".toUri()
                        }
                        context.startActivity(intent)
                    },
                    icon = {
                        Icon(
                            Icons.Filled.Phone,
                            contentDescription = "Phone",
                            tint = if (selectedItem.value == 1) primaryColor else Color.Gray
                        )
                    },
                    label = {
                        Text(
                            text = "Phone",
                            color = if (selectedItem.value == 1) primaryColor else Color.Gray,
                            fontWeight = if (selectedItem.value == 1) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
                NavigationBarItem(
                    selected = selectedItem.value == 2,
                    onClick = {
                        selectedItem.value = 2
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = "mailto:info@nyumbaonline.com".toUri()
                            putExtra(Intent.EXTRA_SUBJECT, "Inquiry")
                            putExtra(Intent.EXTRA_TEXT, "Hello, I would like to meet you. Please help")
                        }
                        context.startActivity(intent)
                    },
                    icon = {
                        Icon(
                            Icons.Filled.Email,
                            contentDescription = "Email",
                            tint = if (selectedItem.value == 2) primaryColor else Color.Gray
                        )
                    },
                    label = {
                        Text(
                            text = "Email",
                            color = if (selectedItem.value == 2) primaryColor else Color.Gray,
                            fontWeight = if (selectedItem.value == 2) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            // Background Image
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = "background image",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize(),
                alpha = 0.4f // Make the background more subtle
            )

            // Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // User profile card
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(6.dp),
                    colors = CardDefaults.cardColors(containerColor = cardBackgroundColor)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Profile image placeholder
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .background(secondaryColor.copy(alpha = 0.2f))
                                .border(2.dp, secondaryColor, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = null,
                                tint = secondaryColor,
                                modifier = Modifier.size(30.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        // User details
                        Column {
                            Text(
                                text = "Welcome, ${management.fullname}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = primaryColor
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = management.company,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.DarkGray
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = management.email,
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }

                // Action cards section
                Text(
                    text = "Management Dashboard",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = primaryColor,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    textAlign = TextAlign.Start
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Add Property Card
                    ElevatedCard(
                        modifier = Modifier
                            .weight(1f)
                            .height(120.dp)
                            .clickable { showDialog.value = true },
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = cardBackgroundColor
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(accentColor.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Add,
                                    contentDescription = null,
                                    tint = accentColor
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Add Property",
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp,
                                color = accentColor
                            )
                        }
                    }

                    // View Properties Card
                    ElevatedCard(
                        modifier = Modifier
                            .weight(1f)
                            .height(120.dp)
                            .clickable {
                                val managementId = management.id
                                if (!managementId.isNullOrEmpty()) {
                                    navController.navigate("${ROUTE_VIEW_PROPERTY}/$managementId")
                                } else {
                                    Toast.makeText(context, "Management ID not found. Cannot view properties.", Toast.LENGTH_SHORT).show()
                                }
                            },
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = cardBackgroundColor
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(accentColor.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                   imageVector = Icons.Filled.Visibility,
                                    contentDescription = null,
                                    tint = accentColor
                               )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "View Properties",
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp,
                                color = accentColor
                            )
                        }
                    }
                }

                // View Chatrooms Card
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .clickable {
                            navController.navigate(ROUTE_CHAT_ROOM_LIST)
                        },
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = cardBackgroundColor
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(accentColor.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Chat,
                                contentDescription = null,
                                tint = accentColor,
                                modifier = Modifier.size(26.dp)  // Correct syntax
                            )
                        }

                        Column {
                            Text(
                                text = "View Chatrooms",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = accentColor
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Communicate with tenants",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }

    if (showDialog.value) {
        AddPropertyDialog(
            managementId = management.id ?: "",
            onDismiss = { showDialog.value = false },
            onSave = { propertyData ->
                scope.launch {
                    propertyViewModel.addProperty(
                        propertyData,
                        onSuccess = {
                            showDialog.value = false
                            Toast.makeText(context, "Property added successfully", Toast.LENGTH_SHORT).show()
                        },
                        onFailure = {
                            Toast.makeText(context, "Failed to add property", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            },
            primaryColor = primaryColor,
            secondaryColor = secondaryColor
        )
    }
}

@Composable
fun AddPropertyDialog(
    managementId: String,
    onDismiss: () -> Unit,
    onSave: (PropertyData) -> Unit,
    primaryColor: Color,
    secondaryColor: Color
) {
    val pictureUri = remember { mutableStateOf("") }
    val name = remember { mutableStateOf("") }
    val numberOfUnits = remember { mutableStateOf("") }
    val address = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }

    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Dialog Header
                Text(
                    text = "Add New Property",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = primaryColor
                )

                Divider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = primaryColor.copy(alpha = 0.2f)
                )

                // Form Fields
                OutlinedTextField(
                    value = pictureUri.value,
                    onValueChange = { pictureUri.value = it },
                    label = { Text("Picture URI") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = primaryColor,
                        focusedLabelColor = primaryColor,
                        cursorColor = primaryColor,
                        unfocusedIndicatorColor = primaryColor.copy(alpha = 0.3f),
                        unfocusedLabelColor = primaryColor.copy(alpha = 0.7f)
                    )

//                    colors = TextFieldDefaults.outlinedTextFieldColors(
//                        focusedBorderColor = primaryColor,
//                        focusedLabelColor = primaryColor,
//                        cursorColor = primaryColor
//                    )
                )

                OutlinedTextField(
                    value = name.value,
                    onValueChange = { name.value = it },
                    label = { Text("Property Name") },
                    modifier = Modifier.fillMaxWidth(),
//                    colors = TextFieldDefaults.outlinedTextFieldColors(
//                        focusedBorderColor = primaryColor,
//                        focusedLabelColor = primaryColor,
//                        cursorColor = primaryColor
//                    )
                )

                OutlinedTextField(
                    value = numberOfUnits.value,
                    onValueChange = { numberOfUnits.value = it },
                    label = { Text("Number of Units") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = primaryColor,
                        focusedLabelColor = primaryColor,
                        cursorColor = primaryColor,
                        unfocusedIndicatorColor = primaryColor.copy(alpha = 0.3f),
                        unfocusedLabelColor = primaryColor.copy(alpha = 0.7f)
                    )

                )

                OutlinedTextField(
                    value = address.value,
                    onValueChange = { address.value = it },
                    label = { Text("Property Address") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = primaryColor,
                        focusedLabelColor = primaryColor,
                        cursorColor = primaryColor,
                        unfocusedIndicatorColor = primaryColor.copy(alpha = 0.3f),
                        unfocusedLabelColor = primaryColor.copy(alpha = 0.7f)
                    )

//                    colors = TextFieldDefaults.outlinedTextFieldColors(
//                        focusedBorderColor = primaryColor,
//                        focusedLabelColor = primaryColor,
//                        cursorColor = primaryColor
//                    )
                )

                OutlinedTextField(
                    value = description.value,
                    onValueChange = { description.value = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = primaryColor,
                        focusedLabelColor = primaryColor,
                        cursorColor = primaryColor,
                        unfocusedIndicatorColor = primaryColor.copy(alpha = 0.3f),
                        unfocusedLabelColor = primaryColor.copy(alpha = 0.7f)
                    )

//                    colors = TextFieldDefaults.outlinedTextFieldColors(
//                        focusedBorderColor = primaryColor,
//                        focusedLabelColor = primaryColor,
//                        cursorColor = primaryColor
//                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { onDismiss() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.LightGray,
                            contentColor = Color.Black
                        ),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text("Cancel")
                    }

                    Button(
                        onClick = {
                            onSave(
                                PropertyData(
                                    pictureUri = pictureUri.value,
                                    name = name.value,
                                    numberOfUnits = numberOfUnits.value.toIntOrNull() ?: 0,
                                    address = address.value,
                                    description = description.value,
                                    managementId = managementId
                                )
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = primaryColor,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Save Property")
                    }
                }
            }
        }
    }
}