package com.example.nyumbaonline.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.nyumbaonline.data.PropertyViewModel
import com.example.nyumbaonline.ui.theme.brown
import com.example.nyumbaonline.ui.theme.saddleBrown
import com.example.nyumbaonline.ui.theme.burlywood

@Composable
fun PropertyScreen(
    navController: NavController,
    managementId: String // Pass the managementId to filter properties
) {
    val propertyViewModel: PropertyViewModel = viewModel()
    val properties = propertyViewModel.properties

    // Fetch properties when entering the screen
    LaunchedEffect(managementId) {
        propertyViewModel.fetchPropertiesForManagement(managementId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(burlywood)
            .padding(16.dp)
    ) {
        if (properties.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No properties found.", color = saddleBrown)
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
                                // Handle property click, e.g., navigate to detail
                            },
                        colors = CardDefaults.cardColors(containerColor = brown),
                        elevation = CardDefaults.cardElevation(6.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(property.name, color = Color.White)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Units: ${property.numberOfUnits}", color = Color.White)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(property.address, color = Color.White)
                        }
                    }
                }
            }
        }
    }
}
