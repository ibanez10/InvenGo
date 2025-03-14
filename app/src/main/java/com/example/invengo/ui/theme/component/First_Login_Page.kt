package com.example.invengo.ui.theme.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.invengo.R
import com.example.invengo.ui.theme.Teal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun First_Login_Page(modifier: Modifier, navController: NavController, onNextClick: () -> Unit){
    Box(Modifier
        .fillMaxWidth()
        .fillMaxHeight()
    ){
    Image(painter = painterResource(
        id = R.drawable.frame),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    )
        Box(Modifier.fillMaxWidth().padding(vertical = 15.dp, horizontal = 5.dp)){
            Button(onNextClick, colors = ButtonDefaults.textButtonColors(contentColor = Color.Transparent), contentPadding = PaddingValues(0.dp)) {
            Image(painter = painterResource(
                id = R.drawable.arrow2),
                contentDescription = null,
                modifier = Modifier.size(25.dp))
            }
            Column(Modifier.fillMaxWidth().padding(vertical = 70.dp, horizontal = 25.dp)) {
                Image(painter = painterResource(
                    id = R.drawable.log),
                    contentDescription = null,
                    modifier = Modifier
                        .size(45.dp)
                )
                Spacer(Modifier.padding(vertical = 15.dp))
                Text(fontSize = 50.sp, text = "Hi there!", color = Color.White, fontWeight = FontWeight(700))
                Text(fontSize = 50.sp, text = "Welcome back", color = Color.White, fontWeight = FontWeight(700))
                Text(fontSize = 16.sp, text = "Enter your credentials to access inventory and operations.", color = Color.Gray, fontWeight = FontWeight(500))
                Spacer(Modifier.padding(vertical = 20.dp))
                TextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(BorderStroke(1.dp, Color.Gray), shape = RoundedCornerShape(10.dp)),
                    label = { Text(text = "Email", color = Color.White) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = null,
                            tint = Color.LightGray,
                            modifier = Modifier.size(30.dp)
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    )
                )
                Spacer(Modifier.padding(vertical = 10.dp))
                TextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(BorderStroke(1.dp, Color.Gray), shape = RoundedCornerShape(10.dp)),
                    label = { Text(text = "Password", color = Color.White) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = Color.LightGray,
                            modifier = Modifier.size(30.dp)
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    )
                )
                Spacer(Modifier.padding(vertical = 3.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = "Forget password?", fontSize = 15.sp, textAlign = TextAlign.End, color = Color.LightGray)
                }
                Spacer(Modifier.padding(vertical = 20.dp))
                Button(
                    onClick = onNextClick,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Teal)
                ) {
                    Spacer(Modifier.padding(vertical = 20.dp))
                    Text(text = "Login", fontSize = 20.sp)
                }
                Spacer(Modifier.padding(vertical = 15.dp))
                Button(
                    onClick = onNextClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(BorderStroke(1.dp, Color.Gray), shape = RoundedCornerShape(20.dp)),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(Color.Transparent)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.google),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp)
                        )
                        Spacer(Modifier.width(15.dp))
                        Text(text = "Continue with Google", fontSize = 16.sp)
                    }
                }
            }
        }
    }
}