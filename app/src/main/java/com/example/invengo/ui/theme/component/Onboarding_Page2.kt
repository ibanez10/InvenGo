package com.example.invengo.ui.theme.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.invengo.R

@Composable
fun Onboarding_Page2(modifier: Modifier, navController: NavController, onNextClick: () -> Unit) {
    Box(Modifier
        .fillMaxHeight()
        .background(color = Color.Black)){
        Image(painter = painterResource(
            id = R.drawable.frame),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceEvenly, // atau Arrangement.Center sesuai kebutuhan
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
            ) {
                Image(
                    painter = painterResource(id = R.drawable.line),
                    contentDescription = null,
                    modifier = Modifier.size(190.dp) // gunakan ukuran yang sesuai, bukan fillMaxWidth
                )
            }
            Box(
            ) {
                Image(
                    painter = painterResource(id = R.drawable.linegray),
                    contentDescription = null,
                    modifier =Modifier.size(195.dp),
                )
            }
        }
        Box(Modifier
            .fillMaxWidth()
            .height(600.dp)
            .padding(25.dp), contentAlignment = Alignment.Center){
        Image(
            painter = painterResource(R.drawable.iconnew),
            contentDescription = null,
            modifier = Modifier.size(250.dp)
        )
        }
        Column(Modifier.run { fillMaxWidth()
            .height(400.dp)
            .align(Alignment.BottomStart)
            .padding(25.dp) }){
            Text(text = "Welcome", fontSize = 25.sp, color = Color.Gray)
            Text(text = "Lorem ipsum dolor amey sin", fontSize = 55.sp, color = Color.LightGray, lineHeight = 60.sp, fontWeight = FontWeight(600))
            Spacer(Modifier.height(15.dp))
            Text(text = "et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco", fontSize = 20.sp, color = Color.LightGray)
            Spacer(Modifier.height(20.dp))
            Button(onNextClick, colors = ButtonDefaults.buttonColors(containerColor = Color.White), shape = RoundedCornerShape(20.dp)) {
                Row(Modifier.offset(x = -15.dp)){
                    Image(painterResource(
                        id = R.drawable.arrow),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                    )
                    Text(text = "Continue", Modifier.padding(vertical = 10.dp, horizontal = 15.dp), fontSize = 22.sp, color = Color.Black)
                }
            }
        }
    }
}


