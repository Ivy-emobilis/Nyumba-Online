package com.example.nyumbaonline.ui.theme.screens.Tenants

import ManagementViewModel
import android.net.Uri
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.nyumbaonline.R
import com.example.nyumbaonline.data.ManagementViewModel
import com.google.api.Property

@Composable
fun AddstudentScreen(navController: NavController){
    val imageUri = rememberSaveable() { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? -> uri?.let { imageUri.value=it } }
    var Fullname by remember { mutableStateOf("") }
    var Property by remember { mutableStateOf("") }
    var HouseNumber by remember { mutableStateOf("") }
    var PhoneNumber by remember { mutableStateOf("") }
    var Email by remember { mutableStateOf("") }
    var IdentificationNo by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val ManagementViewModel: ManagementViewModel= viewModel()
    val context= LocalContext.current
    Column (modifier = Modifier.padding(10.dp)
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally){
        Box(modifier = Modifier.fillMaxWidth()
            .background(Color.LightGray).padding(16.dp)){ Text(text = "ADD NEW TENANT",
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
            textAlign = TextAlign.Center,
            color = Color.Magenta,
            modifier = Modifier.fillMaxWidth()) }
        Card(shape = CircleShape, modifier = Modifier.padding(10.dp).size(200.dp)) {
            AsyncImage(model = imageUri.value ?: R.drawable.ic_person,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(200.dp)
                    .clickable { launcher.launch("image/*") })
        }
        Text(text = "Upload Picture Here")

        OutlinedTextField(value = Fullname,
            onValueChange = {newName->Fullname=newName},
            label = { Text(text = "Enter Tenant name") },
            placeholder = { Text(text = "PLease enter Tenant name") },
            modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = Property,
            onValueChange = {newProperty->Property=newProperty},
            label = { Text(text = "Enter Property") },
            placeholder = { Text(text = "PLease enter Property name") },
            modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = HouseNumber,
            onValueChange = {newHouseNumber->HouseNumber=newHouseNumber},
            label = { Text(text = "Enter HouseNumber") },
            placeholder = { Text(text = "PLease enter HouseNumber") },
            modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = PhoneNumber,
            onValueChange = {newPhoneNumber->PhoneNumber=newPhoneNumber},
            label = { Text(text = "Enter PhoneNumber") },
            placeholder = { Text(text = "PLease enter PhoneNumber") },
            modifier = Modifier.fillMaxWidth().height(150.dp), singleLine = false)
        OutlinedTextField(value = Email,
            onValueChange = {newEmail->Email=newEmail},
            label = { Text(text = "Enter Email") },
            placeholder = { Text(text = "PLease enter Email") },
            modifier = Modifier.fillMaxWidth().height(150.dp), singleLine = false)
        OutlinedTextField(value = IdentificationNo,
            onValueChange = {newIdentificationNo->IdentificationNo=newIdentificationNo},
            label = { Text(text = "Enter IdentificationNo") },
            placeholder = { Text(text = "PLease enter IdentificationNo") },
            modifier = Modifier.fillMaxWidth().height(150.dp), singleLine = false)
        OutlinedTextField(value = description,
            onValueChange = {newdescription->description=newdescription},
            label = { Text(text = "Enter description") },
            placeholder = { Text(text = "PLease enter brief dascription about the tenant") },
            modifier = Modifier.fillMaxWidth().height(150.dp), singleLine = false)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { /*TODO: Handle Go Back*/ }) {
                Text(text = "GO BACK")
            }
            Spacer(modifier = Modifier.width(20.dp))
            Button(onClick = {
                imageUri.value?.let {
                    ManagementViewModel.uploadTenantWithImage(it,context,Fullname, Property, HouseNumber, PhoneNumber,Email,IdentificationNo,description,NavController)
                }?:Toast.makeText(context,"Please pick an image",Toast.LENGTH_LONG).show()
            }) {
                Text(text = "SAVE")
            }
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddstudentScreenPreview(){
    AddstudentScreen(rememberNavController())
}