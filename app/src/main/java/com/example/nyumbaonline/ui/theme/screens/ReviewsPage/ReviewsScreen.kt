package com.example.nyumbaonline.ui.theme.screens.ReviewsPage

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.nyumbaonline.data.FirestoreRepository
import kotlinx.coroutines.launch

@Composable
fun ReviewsPopup(
    userName: String,
    propertyId: String,
    onDismiss: () -> Unit
) {
    var reviewText by remember { mutableStateOf(TextFieldValue("")) }
    var smileyRating by remember { mutableStateOf(3) }
    var userDetails by remember { mutableStateOf<Map<String, Any>?>(null) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(userName) {
        coroutineScope.launch {
            userDetails = FirestoreRepository.getUserDetails(userName)
        }
    }

    Dialog(
        onDismissRequest = {
            onDismiss()
        }
    )
    {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x80000000)) // Semi-transparent background
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "😊",
                        fontSize = 48.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Text(
                        text = "Hello, $userName!",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Please review the property:",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    // Uncomment and implement SmileyRatingBar if needed
                    // SmileyRatingBar(
                    //     rating = smileyRating,
                    //     onRatingChange = { smileyRating = it }
                    // )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = reviewText,
                        onValueChange = { reviewText = it },
                        label = { Text("Write your review here...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                FirestoreRepository.saveReview(
                                    userName = userName,
                                    propertyId = propertyId,
                                    reviewText = reviewText.text,
                                    smileyRating = smileyRating,
                                    userDetails = userDetails
                                )
                                Toast.makeText(
                                    context,
                                    "Review submitted successfully!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                onDismiss()
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Submit")
                    }
                }
            }
        }
    }
}