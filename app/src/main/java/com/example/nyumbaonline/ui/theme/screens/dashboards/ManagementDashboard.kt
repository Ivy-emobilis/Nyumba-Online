package com.example.nyumbaonline.ui.theme.screens.dashboards

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nyumbaonline.R
import com.example.nyumbaonline.navigation.ROUTE_VIEW_TENANTS
import com.example.nyumbaonline.navigation.ROUTE_VIEW_PROPERTY
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nyumbaonline.models.PropertyData
import com.example.nyumbaonline.data.PropertyViewModel
import com.example.nyumbaonline.models.ManagementData
import com.example.nyumbaonline.navigation.ROUTE_CHAT_ROOM
import com.example.nyumbaonline.ui.theme.brown
import com.example.nyumbaonline.ui.theme.saddleBrown
import kotlinx.coroutines.launch
import android.widget.Toast

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManagementDashboard(navController: NavController, management: ManagementData) {

    val selectedItem = remember { mutableStateOf(0) }
    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }
    val propertyViewModel: PropertyViewModel = viewModel()
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome, ${management.fullname}")
        Text(text = "Email: ${management.email}")
        Text(text = "Company: ${management.company}")
        // Add more fields as needed
    }


    if (showDialog.value) {
        AddPropertyDialog(
            managementId = management.id ?: "",
            onDismiss = { showDialog.value = false },
            onSave = { propertyData ->
                scope.launch {
                    propertyViewModel.addProperty(
                        propertyData,
                        onSuccess = { showDialog.value = false },
                        onFailure = { /* Handle error */ }
                    )
                }
            }
        )
    }

    Scaffold (
        bottomBar = {
            NavigationBar (containerColor = Color.White){
                NavigationBarItem(
                    selected = selectedItem.value == 0,
                    onClick = {selectedItem.value = 0
                        val sendIntent = Intent().apply {
                            action=Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT,"download here: https://www.download.com")
                            type = "text/plain"
                        }
                        val shareIntent = Intent.createChooser(sendIntent,null)
                        context.startActivity(shareIntent)
                    },
                    icon = { Icon(Icons.Filled.Share, contentDescription = "Share")},
                    label = { Text(text = "Share") },
                    alwaysShowLabel = true
                )
                NavigationBarItem(
                    selected = selectedItem.value == 1,
                    onClick = {selectedItem.value = 1
                        val intent=Intent(Intent.ACTION_DIAL).apply {
                        data="tel: 0700000000".toUri()
                        }
                        context.startActivity(intent)
                    },
                    icon = { Icon(Icons.Filled.Phone, contentDescription = "Phone")},
                    label = { Text(text = "Phone") },
                    alwaysShowLabel = true
                )
                NavigationBarItem(
                    selected = selectedItem.value == 2,
                    onClick = {selectedItem.value = 2
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = "mailto:info@nyumbaonline.com".toUri()
                            putExtra(Intent.EXTRA_SUBJECT,"Inquiry")
                            putExtra(Intent.EXTRA_TEXT,"Hello, I would like to meet you.Please help")
                        }
                        context.startActivity(intent)
                    },
                    icon = { Icon(Icons.Filled.Email, contentDescription = "Email")},
                    label = { Text(text = "Email") },
                    alwaysShowLabel = true
                )
            }
        }
    )
    { innerPadding ->


        Box() {
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = "background image",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.padding(innerPadding)
            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBar(
                title = { Text(text = "Nyumba Online") },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = "Home"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Profile"
                        )
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search"
                        )
                    }
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Logout"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Gray,
                    navigationIconContentColor = Color.Gray,
                    actionIconContentColor = Color.Gray
                )
            )
            Row(modifier = Modifier.wrapContentWidth()) {
                Card(
                    modifier = Modifier.padding(10.dp).clickable { showDialog.value = true },
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(10.dp),
                    colors = CardDefaults.cardColors(Color.LightGray)
                ) {
                    Box(
                        modifier = Modifier.height(100.dp).padding(25.dp),
                        contentAlignment = Alignment.Center
                    ) { Text(text = "Add Property", color = Color.Magenta) }
                }
                Card(
                    modifier = Modifier.padding(10.dp).clickable {
                        // Only navigate if management.id is not null or empty
                        val managementId = management.id
                        if (!managementId.isNullOrEmpty()) {
                            navController.navigate("${ROUTE_VIEW_PROPERTY}/$managementId")
                        } else {
                            Toast.makeText(context, "Management ID not found. Cannot view properties.", Toast.LENGTH_SHORT).show()
                        }
                    },
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(10.dp),
                    colors = CardDefaults.cardColors(Color.LightGray)
                )
                {
                    Box(
                        modifier = Modifier.height(100.dp)
                            .padding(25.dp),
                        contentAlignment = Alignment.Center
                    )
                    { Text(text = "View Properties", color = Color.Magenta) }
                }
                Card(
                    modifier = Modifier.padding(10.dp).clickable {
                        navController.navigate(ROUTE_CHAT_ROOM) },
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(10.dp),
                    colors = CardDefaults.cardColors(Color.LightGray)
                )
                {
                    Box(
                        modifier = Modifier.height(100.dp)
                            .padding(25.dp),
                        contentAlignment = Alignment.Center
                    )
                    { Text(text = "View Chatroom", color = Color.Magenta) }
                }
            }
        }
    }
}
@Composable
fun AddPropertyDialog(
    managementId: String,
    onDismiss: () -> Unit,
    onSave: (PropertyData) -> Unit
) {
    val pictureUri = remember { mutableStateOf("") }
    val name = remember { mutableStateOf("") }
    val numberOfUnits = remember { mutableStateOf("") }
    val address = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Add Property", color = saddleBrown) },
        text = {
            Column {
                OutlinedTextField(
                    value = pictureUri.value,
                    onValueChange = { pictureUri.value = it },
                    label = { Text("Picture URI") }
                )
                OutlinedTextField(
                    value = name.value,
                    onValueChange = { name.value = it },
                    label = { Text("Name") }
                )
                OutlinedTextField(
                    value = numberOfUnits.value,
                    onValueChange = { numberOfUnits.value = it },
                    label = { Text("Number of Units") }
                )
                OutlinedTextField(
                    value = address.value,
                    onValueChange = { address.value = it },
                    label = { Text("Address") }
                )
                OutlinedTextField(
                    value = description.value,
                    onValueChange = { description.value = it },
                    label = { Text("Description") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(
                        PropertyData(
                            pictureUri = pictureUri.value,
                            name = name.value,
                            numberOfUnits = numberOfUnits.value.toIntOrNull() ?: 0,
                            address = address.value,
                            description = description.value,
                            managementId = managementId // Use actual management id
                        )
                    )
                },
                colors = ButtonDefaults.buttonColors(brown)
            ) {
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


//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun DashboardScreenPreview(){
//    ManagementDashboard(rememberNavController())
//}

