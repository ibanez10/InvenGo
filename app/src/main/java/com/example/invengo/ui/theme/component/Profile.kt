package com.example.invengo.ui.theme.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.invengo.R
import com.example.invengo.ui.theme.PurpleGrey40
import com.example.invengo.ui.theme.Teal

@Composable
fun profile(modifier: Modifier = Modifier, navController: NavController, onNextClick: () -> Unit) {
    val scrollState = rememberScrollState()
    Box(
        Modifier.fillMaxSize().background(color = Color.Black)
    ) {
        Image(
            painter = painterResource(id = R.drawable.frame),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column( Modifier.padding(vertical = 20.dp, horizontal = 10.dp).verticalScroll(scrollState)) {
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.textButtonColors(contentColor = Color.Transparent),
                contentPadding = PaddingValues(1.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arrowtrans),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
            }
            Spacer(Modifier.height(20.dp))
            Text(
                text = "Account",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight(700)
            )
            Spacer(Modifier.height(20.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color.DarkGray,
                                Teal,
                                Color.DarkGray,
                            ),
                        ),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.avatar),
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )
                Spacer(modifier = Modifier.width(15.dp))
                Column {
                    Row( verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Ardhananta Ibanez",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight(700)
                        )
                        Spacer(Modifier.width(8.dp))
                        Box(
                            Modifier.background(color = Teal, shape = RoundedCornerShape(20.dp))
                                .padding(horizontal = 10.dp)
                        ) {
                            Text(text = "Admin", color = Color.White, fontSize = 14.sp)
                        }
                        Spacer(Modifier.width(5.dp))
                    }
                    Spacer(Modifier.height(3.dp))
                    Text(text = "Email@gmail.com", color = Color.LightGray, fontSize = 14.sp)
                }
            }
            Spacer(Modifier.height(30.dp))
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(color = Teal, shape = RoundedCornerShape(30.dp))
                    .padding(top =10.dp, bottom = 0.dp)
            ) {
                Column {
                    Row(
                        Modifier
                            .padding(horizontal = 20.dp, vertical = 0.dp)
                            .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically // <-- kuncinya di sini
                    ) {
                        Image(
                            painter = painterResource(R.drawable.time),
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(Modifier.width(5.dp))
                        Text(text = "History", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight(600))
                    }

                    Spacer(Modifier.height(3.dp)) // Beri jarak agar tidak saling menempel

                    // Box hitam di bawah History
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .padding()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Black,
                                        Color.DarkGray,
                                        Color.Black,
                                    )
                                ),
                                shape = RoundedCornerShape(30.dp)
                            )
                            .verticalScroll(scrollState)
                    ) {
                    Row(Modifier
                        .padding(20.dp)
                    ,verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(R.drawable.stockout),
                            contentDescription = null,
                            modifier = Modifier.size(25.dp)
                        )
                        Spacer(Modifier.width(5.dp))
                        Text(text = "Inbound Stock", fontSize = 15.sp, color = Color.Green, textAlign = TextAlign.Left)
                    }
                    }
                }
            }
        }
    }
}