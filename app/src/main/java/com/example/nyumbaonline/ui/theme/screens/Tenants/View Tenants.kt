package com.example.nyumbaonline.ui.theme.screens.Tenants

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nyumbaonline.data.TenantViewModel
import com.example.nyumbaonline.models.TenantModel
import com.example.nyumbaonline.ui.theme.burlywood
import com.example.nyumbaonline.ui.theme.peru
import com.example.nyumbaonline.ui.theme.sienna
import com.example.nyumbaonline.ui.theme.tan
import com.example.nyumbaonline.ui.theme.wheat
import com.example.nyumbaonline.ui.theme.white
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.launch

@Composable
fun ViewTenants(navController: NavController, tenantViewModel: TenantViewModel, propertyId: String) {
    var tenants by remember { mutableStateOf<List<TenantModel>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    val showAddTenantDialog = remember { mutableStateOf(false) }
    val showDeleteDialog = remember { mutableStateOf<TenantModel?>(null) }
    var listenerRegistration by remember { mutableStateOf<ListenerRegistration?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // Fetch tenants with real-time listener
    fun refreshTenants() {
        isLoading = true
        listenerRegistration?.remove()
        listenerRegistration = tenantViewModel.firestore.collection("tenants")
            .whereEqualTo("propertyId", propertyId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    isLoading = false
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Failed to fetch tenants: ${error.message}")
                    }
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val fetchedTenants = snapshot.documents.mapNotNull {
                        it.toObject(TenantModel::class.java)?.copy(id = it.id)
                    }
                    tenants = fetchedTenants
                    isLoading = false
                }
            }
    }

    DisposableEffect(propertyId) {
        refreshTenants()
        onDispose { listenerRegistration?.remove() }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddTenantDialog.value = true },
                containerColor = sienna,
                contentColor = white,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Tenant")
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = wheat
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(wheat)
        ) {
            // Header
            Text(
                text = "Tenants",
                style = MaterialTheme.typography.headlineSmall,
                color = sienna,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center
            )

            // Loading Indicator
            if (isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    color = peru,
                    trackColor = burlywood
                )
            }

            // Tenant List or Empty State
            AnimatedVisibility(
                visible = !isLoading,
                enter = fadeIn()
            ) {
                if (tenants.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Error,
                            contentDescription = "No Tenants",
                            tint = tan,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "No tenants found for this property.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = tan,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(tenants) { tenant ->
                            TenantCard(
                                tenant = tenant,
                                onClick = {
                                    // TODO: Implement navigation to tenant details screen
                                    // navController.navigate("tenantDetails/${tenant.id}")
                                },
                                onDelete = { showDeleteDialog.value = tenant }
                            )
                        }
                    }
                }
            }
        }
    }

    // Add Tenant Dialog
    if (showAddTenantDialog.value) {
        AddTenantDialog(
            onDismiss = { showAddTenantDialog.value = false },
            onAdd = { firstName, lastName, email, password ->
                val newTenant = TenantModel(
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    password = password,
//                    propertyId = propertyId
                )
                tenantViewModel.addTenant(
                    tenant = newTenant,
                    onSuccess = {
                        showAddTenantDialog.value = false
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Tenant added successfully")
                        }
                    },
                    onFailure = { error ->
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Failed to add tenant: ${error.message}")
                        }
                    }
                )
            }
        )
    }

    // Delete Confirmation Dialog
    showDeleteDialog.value?.let { tenant ->
        AlertDialog(
            onDismissRequest = { showDeleteDialog.value = null },
            title = { Text("Delete Tenant", color = sienna) },
            text = {
                Text(
                    "Are you sure you want to delete ${tenant.firstName} ${tenant.lastName}?",
                    color = tan
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        tenant.id?.let { id ->
                            tenantViewModel.firestore.collection("tenants").document(id)
                                .delete()
                                .addOnSuccessListener {
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar("Tenant deleted successfully")
                                    }
                                }
                                .addOnFailureListener { error ->
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar("Failed to delete tenant: ${error.message}")
                                    }
                                }
                        }
                        showDeleteDialog.value = null
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = sienna)
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteDialog.value = null },
                    colors = ButtonDefaults.textButtonColors(contentColor = tan)
                ) {
                    Text("Cancel")
                }
            },
            containerColor = wheat
        )
    }
}

@Composable
private fun TenantCard(
    tenant: TenantModel,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .clip(RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = burlywood),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "${tenant.firstName} ${tenant.lastName}".trim().ifEmpty { tenant.email },
                    style = MaterialTheme.typography.titleMedium,
                    color = sienna
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = tenant.email,
                    style = MaterialTheme.typography.bodyMedium,
                    color = tan
                )
            }
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete Tenant",
                    tint = peru
                )
            }
        }
    }
}

@Composable
private fun AddTenantDialog(
    onDismiss: () -> Unit,
    onAdd: (String, String, String, String) -> Unit
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var firstNameError by remember { mutableStateOf(false) }
    var lastNameError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Tenant", color = sienna) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                TextField(
                    value = firstName,
                    onValueChange = {
                        firstName = it
                        firstNameError = it.isBlank()
                    },
                    label = { Text("First Name", color = tan) },
                    isError = firstNameError,
                    supportingText = {
                        if (firstNameError) Text("First name is required", color = peru)
                    },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = tan,
                        unfocusedTextColor = tan,
                        focusedContainerColor = white,
                        unfocusedContainerColor = white,
                        focusedLabelColor = sienna,
                        unfocusedLabelColor = tan
                    )
                )
                TextField(
                    value = lastName,
                    onValueChange = {
                        lastName = it
                        lastNameError = it.isBlank()
                    },
                    label = { Text("Last Name", color = tan) },
                    isError = lastNameError,
                    supportingText = {
                        if (lastNameError) Text("Last name is required", color = peru)
                    },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = tan,
                        unfocusedTextColor = tan,
                        focusedContainerColor = white,
                        unfocusedContainerColor = white,
                        focusedLabelColor = sienna,
                        unfocusedLabelColor = tan
                    )
                )
                TextField(
                    value = email,
                    onValueChange = {
                        email = it
                        emailError = it.isBlank()
                    },
                    label = { Text("Email", color = tan) },
                    isError = emailError,
                    supportingText = {
                        if (emailError) Text("Email is required", color = peru)
                    },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = tan,
                        unfocusedTextColor = tan,
                        focusedContainerColor = white,
                        unfocusedContainerColor = white,
                        focusedLabelColor = sienna,
                        unfocusedLabelColor = tan
                    )
                )
                TextField(
                    value = password,
                    onValueChange = {
                        password = it
                        passwordError = it.isBlank()
                    },
                    label = { Text("Password", color = tan) },
                    isError = passwordError,
                    supportingText = {
                        if (passwordError) Text("Password is required", color = peru)
                    },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = tan,
                        unfocusedTextColor = tan,
                        focusedContainerColor = white,
                        unfocusedContainerColor = white,
                        focusedLabelColor = sienna,
                        unfocusedLabelColor = tan
                    )
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    firstNameError = firstName.isBlank()
                    lastNameError = lastName.isBlank()
                    emailError = email.isBlank()
                    passwordError = password.isBlank()
                    if (!firstNameError && !lastNameError && !emailError && !passwordError) {
                        onAdd(firstName, lastName, email, password)
                    }
                },
                colors = ButtonDefaults.textButtonColors(contentColor = sienna)
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(contentColor = tan)
            ) {
                Text("Cancel")
            }
        },
        containerColor = wheat
    )
}