package com.example.invengo.ui.theme.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.invengo.R

@Composable
fun Onboarding_Page1(modifier: Modifier, navController: NavController, onNextClick: () -> Unit) {
    Box(Modifier.fillMaxSize().background(color = Color.Black)){
        Image(
            painter = painterResource(id = R.drawable.imginvbg1),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth().padding(vertical = 130.dp).size(360.dp))
        Row(Modifier.fillMaxWidth().padding(5.dp)) {
            Button(onClick = {}, colors = ButtonDefaults.textButtonColors(contentColor = Color.Transparent), contentPadding = PaddingValues(1.dp) ) {
                Image(
                    painter = painterResource(id = R.drawable.component),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
            }
            Button(onClick = { navController.navigate(Screen.Page3.route) }, colors = ButtonDefaults.textButtonColors(contentColor = Color.Transparent)) {
                Text( modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Right ,text = "Skip", color = Color.White, fontWeight = FontWeight(600), fontSize = 20.sp )
            }
        }
        Box(Modifier.fillMaxWidth().padding(20.dp), contentAlignment = Alignment.Center){
            Image(
                painter = painterResource(id = R.drawable.lmginv2),
                contentDescription = null,
                modifier = Modifier.size(400.dp)
            )
        }
        Box(Modifier.run { fillMaxWidth().clip(RoundedCornerShape(topStart = 55.dp, topEnd = 55.dp)).height(300.dp).background(color = Color.White).align(Alignment.BottomCenter) }){
            Row(Modifier.fillMaxWidth().padding(vertical = 100.dp)) {
                Text(modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, text = "Stay on top of your tasks with our intuitive checklists and real-time updates ", fontWeight = FontWeight(500), fontSize = 18.sp)
            }
            Box(modifier = Modifier.fillMaxWidth().height(150.dp).align(Alignment.BottomCenter)){
                Button(onClick = onNextClick, colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                ), modifier = Modifier.width(250.dp).align(alignment = Alignment.Center).padding(horizontal = 30.dp)) {
                    Text(modifier = Modifier.padding(8.dp), text = "Next", fontSize = 17.sp)
                }
            }
            Text(modifier = Modifier.fillMaxWidth().padding(vertical = 50.dp), textAlign = TextAlign.Center, text = "Stay Organized, Stay Ahead", fontWeight = FontWeight(700), fontSize = 22.sp)
        }
    }
}


