package com.example.invengo.ui.theme.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.invengo.R
import kotlin.math.roundToInt

@Composable
fun Onboarding_First(modifier: Modifier, navController: NavController, onNextClick: () -> Unit) {
    Box(
        Modifier
            .fillMaxHeight()
            .background(color = Color.Black)
    ) {
        Image(
            painter = painterResource(id = R.drawable.frame),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )

        // Garis navigasi atas
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                Image(
                    painter = painterResource(id = R.drawable.line),
                    contentDescription = null,
                    modifier = Modifier.size(160.dp)
                )
            }
            Box {
                Image(
                    painter = painterResource(id = R.drawable.linegray),
                    contentDescription = null,
                    modifier = Modifier.size(160.dp)
                )
            }
        }

        // Icon utama
        Box(
            Modifier
                .fillMaxWidth()
                .height(600.dp)
                .padding(25.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.iconnew),
                contentDescription = null,
                modifier = Modifier.size(250.dp)
            )
        }

        // Konten bawah
        Column(
            Modifier
                .fillMaxWidth()
                .height(400.dp)
                .align(Alignment.BottomStart)
                .padding(25.dp)
        ) {
            Text(text = "InvenGo", fontSize = 25.sp, color = Color.Gray)
            Text(
                text = "Maximize your Job",
                fontSize = 40.sp,
                color = Color.LightGray,
                lineHeight = 40.sp,
                fontWeight = FontWeight(600)
            )
            Spacer(Modifier.height(15.dp))
            Text(
                text = "Enjoy your work experience with real-time storage application",
                fontSize = 20.sp,
                color = Color.LightGray
            )
            Spacer(Modifier.height(20.dp))

            // Slide-to-continue Button
            BoxWithConstraints(
                modifier = Modifier
                    .width(500.dp)
                    .height(60.dp)
                    .background(Color.White.copy(alpha = 0.2f), shape = RoundedCornerShape(20.dp))
            ) {
                val maxWidth = constraints.maxWidth.toFloat()
                var offsetX by remember { mutableStateOf(0f) }

                val threshold = maxWidth * 0.6f

                Box(
                    modifier = Modifier
                        .offset { IntOffset(offsetX.roundToInt(), 0) }
                        .draggable(
                            orientation = Orientation.Horizontal,
                            state = rememberDraggableState { delta ->
                                val newOffset = (offsetX + delta).coerceIn(0f, maxWidth - 100f)
                                offsetX = newOffset
                                if (offsetX > threshold) {
                                    onNextClick()
                                    offsetX = 0f // reset
                                }
                            }
                        )
                        .background(Color.Gray, shape = RoundedCornerShape(20.dp))
                        .height(60.dp)
                        .width(200.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(start = 10.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.arrow),
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Slide to continue",
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}