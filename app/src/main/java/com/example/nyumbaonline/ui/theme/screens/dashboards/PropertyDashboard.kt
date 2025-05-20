package com.example.nyumbaonline.ui.theme.screens.dashboards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nyumbaonline.data.PropertyViewModel
import com.example.nyumbaonline.models.PropertyData
import com.example.nyumbaonline.ui.theme.brown
import com.example.nyumbaonline.ui.theme.saddleBrown

@Composable
fun PropertyDashboard(
    navController: NavController,
    propertyId: String,
    propertyViewModel: PropertyViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val property = remember { mutableStateOf<PropertyData?>(null) }
    val showEditDialog = remember { mutableStateOf(false) }

    // Fetch property details
    LaunchedEffect(propertyId) {
        propertyViewModel.fetchPropertyById(propertyId) { fetchedProperty ->
            property.value = fetchedProperty
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brown)
            .padding(16.dp)
    ) {
        property.value?.let { prop ->
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Property Profile
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = saddleBrown),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Name: ${prop.name}", color = Color.White)
                        Text("Address: ${prop.address}", color = Color.White)
                        Text("Units: ${prop.numberOfUnits}", color = Color.White)
                    }
                }

                // Action Buttons
                Button(onClick = { navController.navigate("ViewTenants/${prop.id}") }) {
                    Text("View Tenants")
                }
                Button(onClick = { showEditDialog.value = true }) {
                    Text("Edit Property")
                }
                Button(onClick = {
                    propertyViewModel.deleteProperty(prop.id) {
                        navController.navigate("managementdashboard")
                    }
                }) {
                    Text("Delete Property")
                }
                Button(onClick = { /* Placeholder for View Reviews */ }) {
                    Text("View Reviews")
                }
                Button(onClick = { /* Placeholder for Open Chatroom */ }) {
                    Text("Open Chatroom")
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
        title = { Text("Edit Property") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                TextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
                TextField(value = address, onValueChange = { address = it }, label = { Text("Address") })
                TextField(value = units, onValueChange = { units = it }, label = { Text("Units") })
            }
        },
        confirmButton = {
            Button(onClick = {
                onSave(property.copy(name = name, address = address, numberOfUnits = units.toInt()))
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        }
    )
}

