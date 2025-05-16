package com.example.nyumbaonline.ui.theme.screens.Tenants

import TenantModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nyumbaonline.data.TenantViewModel

@Composable
fun ViewTenants(navController: NavController, tenantViewModel: TenantViewModel) {
    val tenants = remember { mutableStateListOf<TenantModel>() }
    val showAddTenantDialog = remember { mutableStateOf(false) }

    // Fetch tenants from Firestore
    LaunchedEffect(Unit) {
        tenantViewModel.fetchTenants( // Use the instance of TenantViewModel
            onSuccess = { fetchedTenants ->
                tenants.clear()
                tenants.addAll(fetchedTenants)
            },
            onFailure = { /* Handle failure */ }
        )
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddTenantDialog.value = true }) {
                Text("+")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            tenants.forEach { tenant ->
                Text(text = "${tenant.firstName} ${tenant.lastName}")
            }
        }
    }

    // Add Tenant Dialog
    if (showAddTenantDialog.value) {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showAddTenantDialog.value = false },
            title = { Text("Add Tenant") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    TextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
                    TextField(value = password, onValueChange = { password = it }, label = { Text("Password") })
                }
            },
            confirmButton = {
                Button(onClick = {
                    val newTenant = TenantModel(email = email, password = password)
                    tenantViewModel.addTenant(
                        tenant = newTenant,
                        onSuccess = {
                            tenants.add(newTenant)
                            showAddTenantDialog.value = false
                        },
                        onFailure = { /* Handle failure */ }
                    )
                }) {
                    Text("Add")
                }
            },
            dismissButton = {
                Button(onClick = { showAddTenantDialog.value = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
