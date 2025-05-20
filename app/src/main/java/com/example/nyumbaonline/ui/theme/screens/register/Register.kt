package com.example.nyumbaonline.ui.theme.screens.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.nyumbaonline.R
import com.example.nyumbaonline.data.AuthViewModel
import com.example.nyumbaonline.models.ManagementData
import com.example.nyumbaonline.navigation.ROUTE_LOGIN

// Define neutral color palette
private val backgroundColor = Color(0xFFF5F5F5) // Light gray background
private val primaryColor = Color(0xFF5D4037) // Brown
private val secondaryColor = Color(0xFF8D6E63) // Light brown
private val textColor = Color(0xFF3E2723) // Dark brown
private val surfaceColor = Color.White
private val accentColor = Color(0xFFD7CCC8) // Very light brown

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
    var passwordVisible by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        backgroundColor,
                        Color(0xFFECEFF1) // Slightly different shade for gradient effect
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Logo and header section
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo",
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .shadow(4.dp),
                contentScale = ContentScale.Fit
            )

            Text(
                text = "Create Account",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = textColor,
                fontFamily = FontFamily.SansSerif
            )

            Text(
                text = "Please fill in your details to get started",
                fontSize = 14.sp,
                color = textColor.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Card containing all the form fields
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(8.dp, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = surfaceColor)
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 24.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Personal Information Section
                    Text(
                        text = "Personal Information",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = primaryColor
                    )

                    OutlinedTextField(
                        value = fullname,
                        onValueChange = { fullname = it },
                        label = { Text("Full Name") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Person Icon",
                                tint = primaryColor
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = primaryColor,
                            unfocusedBorderColor = accentColor,
                            focusedLabelColor = primaryColor,
                            cursorColor = primaryColor
                        )
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email Address") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = "Email Icon",
                                tint = primaryColor
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = primaryColor,
                            unfocusedBorderColor = accentColor,
                            focusedLabelColor = primaryColor,
                            cursorColor = primaryColor
                        )
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Password Icon",
                                tint = primaryColor
                            )
                        },
                        trailingIcon = {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Default.Lock else Icons.Default.Lock,
                                contentDescription = "Toggle password visibility",
                                modifier = Modifier.clickable { passwordVisible = !passwordVisible },
                                tint = primaryColor
                            )
                        },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = primaryColor,
                            unfocusedBorderColor = accentColor,
                            focusedLabelColor = primaryColor,
                            cursorColor = primaryColor
                        )
                    )

                    // Company Information Section
                    Divider(color = accentColor, thickness = 1.dp)

                    Text(
                        text = "Company Details",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = primaryColor
                    )

                    OutlinedTextField(
                        value = company,
                        onValueChange = { company = it },
                        label = { Text("Company Name") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = "Company Icon",
                                tint = primaryColor
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = primaryColor,
                            unfocusedBorderColor = accentColor,
                            focusedLabelColor = primaryColor,
                            cursorColor = primaryColor
                        )
                    )

                    // Personal Identification Section
                    Divider(color = accentColor, thickness = 1.dp)

                    Text(
                        text = "Personal Identification",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = primaryColor
                    )

                    OutlinedTextField(
                        value = nationality,
                        onValueChange = { nationality = it },
                        label = { Text("Nationality") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Face,
                                contentDescription = "Nationality Icon",
                                tint = primaryColor
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = primaryColor,
                            unfocusedBorderColor = accentColor,
                            focusedLabelColor = primaryColor,
                            cursorColor = primaryColor
                        )
                    )

                    OutlinedTextField(
                        value = idNo,
                        onValueChange = { idNo = it },
                        label = { Text("ID Number") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "ID Icon",
                                tint = primaryColor
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = primaryColor,
                            unfocusedBorderColor = accentColor,
                            focusedLabelColor = primaryColor,
                            cursorColor = primaryColor
                        )
                    )

                    OutlinedTextField(
                        value = homeCounty,
                        onValueChange = { homeCounty = it },
                        label = { Text("Home County") },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "Home Icon",
                                tint = primaryColor
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = primaryColor,
                            unfocusedBorderColor = accentColor,
                            focusedLabelColor = primaryColor,
                            cursorColor = primaryColor
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Register Button
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
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
            ) {
                Text(
                    text = "CREATE ACCOUNT",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Login text
            Row(
                modifier = Modifier
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Already have an account? ",
                    color = textColor.copy(alpha = 0.7f)
                )
                Text(
                    text = "Login",
                    color = primaryColor,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        navController.navigate(ROUTE_LOGIN)
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}