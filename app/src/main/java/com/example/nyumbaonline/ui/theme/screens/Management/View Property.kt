package com.example.nyumbaonline.ui.theme.screens.Management

import ManagementViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.nyumbaonline.data.ManagementViewModel
import com.example.nyumbaonline.models.TenantModel
import com.example.nyumbaonline.navigation.ROUTE_UPDATE_PROPERTY


@Composable
fun ViewProperty(navController: NavHostController){
    val context = LocalContext.current
    val TenantRepository = ManagementViewModel()
    val emptyUploadState = remember {
        mutableStateOf(
            TenantModel("","","","","",""))
    }
    val emptyUploadListState = remember {
        mutableStateListOf<Model>()
    }
    val students = studentRepository.viewStudents(
        emptyUploadState,emptyUploadListState, context)
    Column (modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "All Students",
                fontSize = 30.sp,
                fontFamily = FontFamily.SansSerif,
                color= Color.Black)
            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn(){
                items(students){
                    StudentItem(
                        name = it.name,
                        gender = it.gender,
                        course = it.course,
                        summary = it.summary,
                        studentId = it.studentId,
                        imageUrl = it.imageUrl,
                        navController = navController,
                        studentRepository = studentRepository

                    )
                }

            }
        }
    }
}
@Composable
fun StudentItem(name:String,gender:String,course:String,
                summary: String,studentId:String,imageUrl: String,navController: NavHostController,
                studentRepository: StudentViewModel
){
    val context = LocalContext.current
    Column (modifier = Modifier.fillMaxWidth()){
        Card (modifier = Modifier
            .padding(10.dp)
            .height(210.dp),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors
                (containerColor = Color.Gray))
        {
            Row {
                Column {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(200.dp)
                            .height(150.dp)
                            .padding(10.dp)
                    )

                    Row(horizontalArrangement = Arrangement.SpaceBetween) {
                        Button(onClick = {
                            studentRepository.deleteStudent(context,studentId,navController)

                        },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(Color.Red)
                        ) {
                            Text(text = "REMOVE",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp)
                        }
                        Button(onClick = {
                            navController.navigate("$ROUTE_UPDATE_STUDENT/$studentId")
                        },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(Color.Green)
                        ) {
                            Text(text = "UPDATE",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp)
                        }
                    }

                }
                Column (modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 10.dp)
                    .verticalScroll(rememberScrollState())){
                    Text(text = "STUDENT NAME",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold)
                    Text(text = name,
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold)
                    Text(text = "STUDENT GENDER",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold)
                    Text(text = gender,
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold)
                    Text(text = "STUDENT COURSE",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold)
                    Text(text = course,
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold)
                    Text(text = "SUMMARY",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold)
                    Text(text = summary,
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
@Preview
@Composable
fun ViewStudentsPreview(){
    ViewStudents(rememberNavController())
}