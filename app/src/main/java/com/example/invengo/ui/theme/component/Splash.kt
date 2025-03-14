package com.example.invengo.ui.theme.component

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.invengo.R
import com.example.invengo.Screen
import kotlinx.coroutines.delay

@Composable
fun AnimatedSigmaSplash(navController: NavHostController) {
    var startAnimation by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(1500)
        navController.popBackStack()
        navController.navigate(Screen.Login_Page.route)
    }
    Splash()
}

@Composable
fun Splash(modifier: Modifier = Modifier.fillMaxSize().background(color = Color.Black)) {
    Box(Modifier.fillMaxSize().background(Color.Black), contentAlignment = Alignment.Center){
        Image(
            painter = painterResource(id = R.drawable.logo_invengo),
            contentDescription = null,
            modifier = Modifier.size(500.dp)
        )
    }
}
