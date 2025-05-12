package com.example.invengo

sealed class Screen(val route: String) {
    object Splash: Screen("splash_screen")
    object Login_Page: Screen("login_Page")
    object Rules_Login1: Screen("Rules_Login1")
    object Rules_Login2: Screen("Rules_Login2")
    object Page6 : Screen("home_page_first")
}

