package com.example.invengo.ui.theme.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.invengo.R

@Composable
fun Getstarted(modifier: Modifier, onNextClick: () -> Unit) {
    Box(
        Modifier
            .fillMaxSize()
            .background(color = Color.Transparent)
    ) {
    //  Start Frame
        Box(
            Modifier
                .fillMaxWidth()
                .background(color = Color.Transparent)
        ) {
            Image(
                painter = painterResource(id = R.drawable.frame),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        // Start Logo
            Box(
                Modifier
                    .align(alignment = Alignment.BottomCenter)
                    .offset(y = 50.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.loogo),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(95.dp)
                    )
                }
            }
        // End Logo
        }
        // End Frame
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .offset(y = 150.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Box(Modifier, contentAlignment = Alignment.BottomCenter){
            Image(
                painterResource(id = R.drawable.invengo),
                contentDescription = null,
                modifier = Modifier
                    .size(250.dp)
                    .align(alignment = Alignment.BottomCenter)
                    .offset(y = 20.dp)
                )
                Text(modifier = Modifier, fontSize = 17.sp, textAlign = TextAlign.Center, fontWeight = FontWeight(700), text = "Get started with InvenGo and experience the future of inventory management today!")
                }
                Box(modifier = Modifier.fillMaxWidth().height(200.dp)){
                Button(onNextClick, Modifier.align(alignment = Alignment.BottomCenter), colors = ButtonDefaults.buttonColors(containerColor = Color.Black), shape = RoundedCornerShape(20.dp)) {
                    Row(Modifier.offset(x = -14.dp)){
                        Image(painterResource(
                            id = R.drawable.arrow),
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                        )
                    Text(text = "Get started for free", Modifier.padding(vertical = 10.dp, horizontal = 8.dp), fontSize = 16.sp)
                    }
                }
                }
            }
    }
}
