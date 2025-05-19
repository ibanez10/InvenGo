package com.example.invengo

sealed class Screen(val route: String) {
    object Splash: Screen("splash_screen")
    object Login_Page: Screen("login_Page")
    object Main : Screen("main_screen")
    object Add : Screen("add_item")
}

