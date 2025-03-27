package com.example.invengo.ui.theme.component

import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.sharp.Build
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
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
fun Threed_Login_Page(modifier: Modifier, navController: NavController, onNextClick: () -> Unit){
    Box(
        Modifier
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
        Box(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp, horizontal = 5.dp)){
            Button(onNextClick, colors = ButtonDefaults.textButtonColors(contentColor = Color.Transparent), contentPadding = PaddingValues(0.dp)) {
                Image(painter = painterResource(
                    id = R.drawable.arrow2),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp))
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 70.dp, horizontal = 25.dp)) {
                Image(painter = painterResource(
                    id = R.drawable.log),
                    contentDescription = null,
                    modifier = Modifier
                        .size(45.dp)
                )
                Spacer(Modifier.padding(vertical = 15.dp))
                Text(fontSize = 50.sp, text = "Forgot", color = Color.White, fontWeight = FontWeight(700))
                Text(fontSize = 50.sp, text = "your password?", color = Color.White, fontWeight = FontWeight(700))
                Text(fontSize = 16.sp, text = "Enter your email address, and we’ll send you a link to reset your password.", color = Color.Gray, fontWeight = FontWeight(500))
                Spacer(Modifier.padding(vertical = 20.dp))
                var email by remember { mutableStateOf("") }
                var isFocused by remember { mutableStateOf(false) }
                TextField(
                    value = email,
                    onValueChange = {email = it},
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            1.dp,
                            if (isFocused) Color.Gray else Color.Green,
                            RoundedCornerShape(15.dp)
                        )
                        .background(
                            if (isFocused) Color.Gray.copy(alpha = 0.2f) else Color.Transparent,
                            RoundedCornerShape(15.dp)
                        )
                        .onFocusChanged { isFocused = it.isFocused },
                    label = { Text(text = "Email Address", color = Color.LightGray) },
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight(700),
                        color = Color.White,
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    )
                )
                Spacer(Modifier.padding(vertical = 15.dp))
                Button(
                    onClick = onNextClick,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Teal)
                ) {
                    Spacer(Modifier.padding(vertical = 20.dp))
                    Text(text = "Send", fontSize = 22.sp)
                }
                Spacer(Modifier.padding(vertical = 10.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    val context = LocalContext.current
                    val annotatedString = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.White)) {
                            append("Back to? ")
                        }
                        pushStringAnnotation(tag = "signup", annotation = "https://your-signup-url.com")
                        withStyle(style = SpanStyle(color = Color.Cyan, textDecoration = TextDecoration.Underline)) {
                            append("Login")
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