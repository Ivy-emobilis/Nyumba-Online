package com.example.nyumbaonline.ui.theme.screens.dashboards


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.nyumbaonline.data.ManagementViewModel
import com.example.nyumbaonline.data.TenantViewModel
import com.example.nyumbaonline.models.ManagementData
import com.example.nyumbaonline.models.TenantModel
import com.example.nyumbaonline.ui.theme.brown
import com.example.nyumbaonline.ui.theme.saddleBrown

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TenantDashboard(
    navController: NavController,
    tenantViewModel: TenantViewModel,
    managementViewModel: ManagementViewModel,
    tenant: TenantModel
) {
    val context = LocalContext.current
    val showManagementDetailsDialog = remember { mutableStateOf(false) }
    val managementDetails = managementViewModel.getManagementDetails(tenant.propertyId).observeAsState()

    val showEditDialog = remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(saddleBrown),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "nyumbaonline@2025",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(brown)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 0.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TopAppBar(
                    title = { Text(text = "nyumba online", color = Color.White, fontWeight = FontWeight.Bold) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = saddleBrown,
                        titleContentColor = Color.White
                    )
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 32.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        // Open Chatroom
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .height(120.dp)
                                .clickable {
                                    navController.navigate("chat/${tenant.propertyId}")
                                },
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(saddleBrown)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Text("Open Chatroom", color = Color.White, fontWeight = FontWeight.Medium)
                            }
                        }
                        // Management Details
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .height(120.dp)
                                .clickable { showManagementDetailsDialog.value = true },
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(saddleBrown)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Text("Management Details", color = Color.White, fontWeight = FontWeight.Medium)
                            }
                        }
                        // Review
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .height(120.dp)
                                .clickable { navController.navigate("give_review") },
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(saddleBrown)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Text("Review", color = Color.White, fontWeight = FontWeight.Medium)
                            }
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        // Edit Profile
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .height(120.dp)
                                .clickable { showEditDialog.value = true },
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(saddleBrown)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Text("Edit Profile", color = Color.White, fontWeight = FontWeight.Medium)
                            }
                        }
                        // Logout
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .height(120.dp)
                                .clickable { /* TODO: Logout action */ },
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(saddleBrown)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Text("Logout", color = Color.White, fontWeight = FontWeight.Medium)
                            }
                        }
                        // Empty space to balance the row
                        Box(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }

    // Management Details Dialog
    if (showManagementDetailsDialog.value) {
        Dialog(onDismissRequest = { showManagementDetailsDialog.value = false }) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = saddleBrown,
                modifier = Modifier.padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .background(brown),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Management Details",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge
                    )
                    if (managementDetails.value != null) {
                        val details = managementDetails.value!!
                        Text("Name: ${details.fullname}", color = Color.White)
                        Text("Email: ${details.email}", color = Color.White)
                        Text("Company: ${details.company}", color = Color.White)
                        Text("Nationality: ${details.nationality}", color = Color.White)
                        Text("ID No: ${details.idNo}", color = Color.White)
                        Text("Home County: ${details.homeCounty}", color = Color.White)
                    } else {
                        Text("Loading...", color = Color.White)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { showManagementDetailsDialog.value = false },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                    ) {
                        Text("Close", color = saddleBrown)
                    }
                }
            }
        }
    }

    // Edit Profile Dialog
    if (showEditDialog.value) {
        var firstName by remember { mutableStateOf(tenant.firstName) }
        var lastName by remember { mutableStateOf(tenant.lastName) }
        var phoneNumber by remember { mutableStateOf(tenant.phoneNumber) }
        var idNumber by remember { mutableStateOf(tenant.idNumber) }
        var county by remember { mutableStateOf(tenant.county) }
        var estate by remember { mutableStateOf(tenant.estate) }
        var houseNumber by remember { mutableStateOf(tenant.houseNumber) }
        var password by remember { mutableStateOf(tenant.password) }

        AlertDialog(
            onDismissRequest = { showEditDialog.value = false },
            title = { Text("Edit Profile") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    TextField(value = firstName, onValueChange = { firstName = it }, label = { Text("First Name") })
                    TextField(value = lastName, onValueChange = { lastName = it }, label = { Text("Last Name") })
                    TextField(value = phoneNumber, onValueChange = { phoneNumber = it }, label = { Text("Phone Number") })
                    TextField(value = idNumber, onValueChange = { idNumber = it }, label = { Text("ID Number") })
                    TextField(value = county, onValueChange = { county = it }, label = { Text("County") })
                    TextField(value = estate, onValueChange = { estate = it }, label = { Text("Estate") })
                    TextField(value = houseNumber, onValueChange = { houseNumber = it }, label = { Text("House Number") })
                    TextField(value = password, onValueChange = { password = it }, label = { Text("Password") })
                }
            },
            confirmButton = {
                Button(onClick = {
                    val updatedTenant = tenant.copy(
                        firstName = firstName,
                        lastName = lastName,
                        phoneNumber = phoneNumber,
                        idNumber = idNumber,
                        county = county,
                        estate = estate,
                        houseNumber = houseNumber,
                        password = password
                    )
                    tenantViewModel.updateTenantDetails(
                        tenantId = tenant.id.toString(),
                        updatedTenant = updatedTenant,
                        onSuccess = {
                            Toast.makeText(context, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                            showEditDialog.value = false
                        },
                        onFailure = {
                            Toast.makeText(context, "Failed to update profile: ${it.message}", Toast.LENGTH_SHORT).show()
                        }
                    )
                }) {
                    Text("Save")
                }
            },
            dismissButton = {
                Button(onClick = { showEditDialog.value = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}