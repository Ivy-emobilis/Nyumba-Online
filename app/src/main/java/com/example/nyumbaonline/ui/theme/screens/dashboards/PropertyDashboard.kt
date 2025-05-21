package com.example.nyumbaonline.ui.theme.screens.dashboards

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nyumbaonline.data.PropertyViewModel
import com.example.nyumbaonline.models.PropertyData
import com.example.nyumbaonline.navigation.ROUTE_CHAT_ROOM_LIST
import kotlinx.coroutines.delay

// Color scheme inspired by the image and existing app
val lightBg = Color(0xFFF5F5F5)  // Light background
//val darkBrown = Color(0xFF47281F) // Text and icon color
//val mediumBrown = Color(0xFF7D5A50) // Secondary text color
//val lightGray = Color(0xFFF7F7F7) // Card container color

@Composable
fun PropertyDashboard(
    navController: NavController,
    propertyId: String,
    propertyViewModel: PropertyViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val property = remember { mutableStateOf<PropertyData?>(null) }
    val showEditDialog = remember { mutableStateOf(false) }

    // Animation states for staggered entry
    var showCard by remember { mutableStateOf(false) }
    var showActions by remember { mutableStateOf(false) }

    // Trigger staggered animations
    LaunchedEffect(Unit) {
        showCard = true
        delay(400)
        showActions = true
    }

    // Fetch property details
    LaunchedEffect(propertyId) {
        propertyViewModel.fetchPropertyById(propertyId) { fetchedProperty ->
            property.value = fetchedProperty
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(lightBg)
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Logo Placeholder
            Card(
                modifier = Modifier
                    .size(100.dp)
                    .padding(top = 16.dp),
                colors = CardDefaults.cardColors(containerColor = lightGray),
                shape = RoundedCornerShape(8.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Filled.Home,
                        contentDescription = "Nyumba Online Logo",
                        tint = darkBrown,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }

            // Property Profile Section
            Text(
                text = "Property Profile",
                fontSize = 20.sp,
                color = darkBrown,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )

            // Property Profile Card
            AnimatedVisibility(
                visible = showCard,
                enter = fadeIn() + slideInVertically(
                    initialOffsetY = { 50 },
                    animationSpec = tween(durationMillis = 500)
                )
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = lightGray),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.Home,
                                contentDescription = "Name",
                                tint = darkBrown,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Name: ${property.value?.name ?: ""}", color = darkBrown, fontSize = 16.sp)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.LocationOn,
                                contentDescription = "Address",
                                tint = darkBrown,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Address: ${property.value?.address ?: ""}", color = darkBrown, fontSize = 16.sp)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.Apartment,
                                contentDescription = "Units",
                                tint = darkBrown,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Units: ${property.value?.numberOfUnits ?: 0}", color = darkBrown, fontSize = 16.sp)
                        }
                    }
                }
            }

            // Actions Section
            Text(
                text = "Actions",
                fontSize = 20.sp,
                color = darkBrown,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )

            // Action Buttons
            AnimatedVisibility(
                visible = showActions,
                enter = fadeIn() + slideInVertically(
                    initialOffsetY = { 70 },
                    animationSpec = tween(durationMillis = 600)
                )
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ActionButton(
                        title = "View Tenants",
                        icon = Icons.Filled.Group,
                        onClick = { navController.navigate("ViewTenants/${property.value?.id}") }
                    )
                    ActionButton(
                        title = "Edit Property",
                        icon = Icons.Filled.Edit,
                        onClick = { showEditDialog.value = true }
                    )
                    ActionButton(
                        title = "Delete Property",
                        icon = Icons.Filled.Delete,
                        onClick = {
                            property.value?.id?.let { id ->
                                propertyViewModel.deleteProperty(id) {
                                    navController.navigate("managementdashboard")
                                }
                            }
                        }
                    )
                    ActionButton(
                        title = "View Reviews",
                        icon = Icons.Filled.Star,
                        onClick = { /* Placeholder for View Reviews */ }
                    )
                    ActionButton(
                        title = "Open Chatroom",
                        icon = Icons.Filled.Chat,
                        onClick = { navController.navigate(ROUTE_CHAT_ROOM_LIST) })

                }
            }
        }
    }

    // Edit Property Dialog
    if (showEditDialog.value) {
        EditPropertyDialog(
            property = property.value!!,
            onDismiss = { showEditDialog.value = false },
            onSave = { updatedProperty ->
                propertyViewModel.updateProperty(updatedProperty) {
                    property.value = updatedProperty
                    showEditDialog.value = false
                }
            }
        )
    }
}

@Composable
fun ActionButton(title: String, icon: ImageVector, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(containerColor = mediumBrown),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                color = Color.White,
                fontSize = 16.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun EditPropertyDialog(
    property: PropertyData,
    onDismiss: () -> Unit,
    onSave: (PropertyData) -> Unit
) {
    var name by remember { mutableStateOf(property.name) }
    var address by remember { mutableStateOf(property.address) }
    var units by remember { mutableStateOf(property.numberOfUnits.toString()) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = "Edit Property",
                color = darkBrown,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                fontFamily = FontFamily.Cursive
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name", color = mediumBrown, fontSize = 14.sp, fontFamily = FontFamily.Serif) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = "Name",
                            tint = darkBrown
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = darkBrown,
                        unfocusedBorderColor = mediumBrown,
                        focusedTextColor = darkBrown,
                        unfocusedTextColor = darkBrown,
                        focusedLabelColor = darkBrown,
                        unfocusedLabelColor = mediumBrown,
                        focusedContainerColor = lightGray,
                        unfocusedContainerColor = lightGray
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Address", color = mediumBrown, fontSize = 14.sp, fontFamily = FontFamily.Serif) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.LocationOn,
                            contentDescription = "Address",
                            tint = darkBrown
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = darkBrown,
                        unfocusedBorderColor = mediumBrown,
                        focusedTextColor = darkBrown,
                        unfocusedTextColor = darkBrown,
                        focusedLabelColor = darkBrown,
                        unfocusedLabelColor = mediumBrown,
                        focusedContainerColor = lightGray,
                        unfocusedContainerColor = lightGray
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = units,
                    onValueChange = { units = it },
                    label = { Text("Units", color = mediumBrown, fontSize = 14.sp, fontFamily = FontFamily.Serif) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Apartment,
                            contentDescription = "Units",
                            tint = darkBrown
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = darkBrown,
                        unfocusedBorderColor = mediumBrown,
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
                    onSave(property.copy(name = name, address = address, numberOfUnits = units.toInt()))
                },
                colors = ButtonDefaults.buttonColors(containerColor = darkBrown),
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text("Save", color = Color.White, fontSize = 16.sp, fontFamily = FontFamily.Serif)
            }
        },
        dismissButton = {
            Button(
                onClick = { onDismiss() },
                colors = ButtonDefaults.buttonColors(containerColor = mediumBrown),
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Cancel", color = Color.White, fontSize = 16.sp, fontFamily = FontFamily.Serif)
            }
        },
        containerColor = lightGray,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.padding(16.dp)
    )
}