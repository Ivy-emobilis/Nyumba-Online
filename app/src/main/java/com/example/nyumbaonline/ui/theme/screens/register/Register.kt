package com.example.nyumbaonline.ui.theme.screens.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.nyumbaonline.R
import com.example.nyumbaonline.data.AuthViewModel
import com.example.nyumbaonline.ui.theme.brown
import com.example.nyumbaonline.ui.theme.saddleBrown
import com.example.nyumbaonline.models.ManagementData
import com.example.nyumbaonline.navigation.ROUTE_LOGIN

@Composable
fun Register(navController: NavController) {
    val authViewModel: AuthViewModel = viewModel()
    var fullname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var company by remember { mutableStateOf("") }
    var nationality by remember { mutableStateOf("") }
    var idNo by remember { mutableStateOf("") }
    var homeCounty by remember { mutableStateOf("") }
    val context = LocalContext.current
    val passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(brown),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Register Here!",
            fontSize = 20.sp,
            fontFamily = FontFamily.SansSerif,
            fontStyle = FontStyle.Italic,
            color = saddleBrown,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .background(Color.LightGray)
                .fillMaxWidth()
                .padding(5.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logo",
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .height(40.dp)
        )
        OutlinedTextField(
            value = fullname,
            onValueChange = { fullname = it },
            label = { Text(text = "Enter your Fullname") },
            placeholder = { Text(text = "Please enter Fullname") },
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally),
            leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = "Person Icon") }
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Enter Email") },
            placeholder = { Text(text = "Please enter your email") },
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally),
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "Email Icon") }
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Enter Password") },
            placeholder = { Text(text = "Please input Password") },
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally),
            leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "Password Icon") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
        )
        OutlinedTextField(
            value = company,
            onValueChange = { company = it },
            label = { Text(text = "Enter Company Name") },
            placeholder = { Text(text = "Please enter your company name") },
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally)
        )
        OutlinedTextField(
            value = nationality,
            onValueChange = { nationality = it },
            label = { Text(text = "Enter Nationality") },
            placeholder = { Text(text = "Please enter your nationality") },
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally)
        )
        OutlinedTextField(
            value = idNo,
            onValueChange = { idNo = it },
            label = { Text(text = "Enter ID Number") },
            placeholder = { Text(text = "Please enter your ID number") },
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally)
        )
        OutlinedTextField(
            value = homeCounty,
            onValueChange = { homeCounty = it },
            label = { Text(text = "Enter Home County") },
            placeholder = { Text(text = "Please enter your home county") },
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally)
        )
        Button(
            onClick = {
                val managementData = ManagementData(
                    fullname = fullname,
                    email = email,
                    password = password,
                    company = company,
                    nationality = nationality,
                    idNo = idNo,
                    homeCounty = homeCounty
                )
                authViewModel.registerManagement(managementData, navController, context)
            },
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(saddleBrown)
        ) {
            Text(text = "Register")
        }
        Text(
            text = buildAnnotatedString { append("If already registered, Login here") },
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally)
                .clickable {
                    navController.navigate(ROUTE_LOGIN)
                }
        )
    }
}
