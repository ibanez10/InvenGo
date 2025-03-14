package com.example.invengo

import OnboardingNavController
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.invengo.ui.theme.component.AnimatedSigmaSplash
import com.example.invengo.ui.theme.component.Onboarding_Page2
import com.example.invengo.ui.theme.component.First_Onboarding
import com.example.invengo.ui.theme.component.Getstarted_Page
import com.example.invengo.ui.theme.component.Onboarding_Page1

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
//            First_Onboarding()
//            Onboarding_Page1()
//            Onboarding_Page2()
//            Getstarted_Page()
            OnboardingNavController()
        }
    }
}