package com.example.invengo.ui.theme.component

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.invengo.R
import com.example.invengo.ui.theme.Teal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Second_Login_Page(modifier: Modifier, navController: NavController, onNextClick: () -> Unit){
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
                Text(fontSize = 40.sp, text = "Enter Code!", color = Color.White, fontWeight = FontWeight(700))
                Text(fontSize = 40.sp, text = "Send to your email", color = Color.White, fontWeight = FontWeight(700))
                Text(fontSize = 15.sp, text = "We've sent a verification code to your email. Please enter the code below to continue.", color = Color.Gray, fontWeight = FontWeight(500))
                // Verification
                Spacer(Modifier.padding(vertical = 10.dp))
                var codes = remember { List(4) { mutableStateOf("") } }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(15.dp),
                    modifier = Modifier.padding(10.dp)
                ) {
                    codes.forEachIndexed { index, codeState ->
                        TextField(
                            value = codeState.value,
                            onValueChange = {
                                if (it.length <= 1 && it.all { char -> char.isDigit() }) {
                                    codeState.value = it
                                }
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier
                                .size(65.dp)
                                .border(
                                    1.dp,
                                    Color.Gray,
                                    RoundedCornerShape(8.dp)
                                ), // Border tetap terlihat
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                errorContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent, // Menghilangkan garis bawah default
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            textStyle = LocalTextStyle.current.copy(
                                fontSize = 30.sp,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            ),
                            singleLine = true
                        )
                    }
                    }
                // End verification
                Spacer(modifier = Modifier.height(60.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Resend OTP",
                        fontSize = 15.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = onNextClick,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Teal
                )){
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
                        Spacer(Modifier.width(10.dp))
                        Text(text = "Continue with Google", fontSize = 16.sp)
                    }
                }
                Spacer(Modifier.height(10.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    val context = LocalContext.current
                    val annotatedString = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.LightGray)) {
                            append("Don't have an account? ")
                        }
                        pushStringAnnotation(tag = "signup", annotation = "https://your-signup-url.com")
                        withStyle(style = SpanStyle(color = Color.Cyan, textDecoration = TextDecoration.Underline)) {
                            append("Sign up")
                        }
                        pop()
                    }

                    ClickableText(
                        text = annotatedString,
                        onClick = { offset ->
                            annotatedString.getStringAnnotations(tag = "signup", start = offset, end = offset)
                                .firstOrNull()?.let {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.item))
                                    context.startActivity(intent)
                                }
                        }
                    )
                }
            }
        }
    }
}