package com.example.invengo

import ItemData
import OnboardingNavController
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.invengo.ui.theme.component.AddItem
import com.example.invengo.ui.theme.component.AnimatedSigmaSplash

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            AnimatedSigmaSplash(navController)
        }
        composable(route = Screen.Login_Page.route) {
            OnboardingNavController()
        }
        composable("item_data") {
            ItemData(
                navController = navController,
                onNextClick = { navController.navigate("add_item") }
            )
        }
        composable("add_item") {
            AddItem(
                navController = navController,
                onNextClick = { navController.popBackStack() }
            )
        }
    }
}