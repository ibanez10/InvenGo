import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.invengo.ui.theme.component.*

sealed class Screen(val route: String) {
    object Page1 : Screen("onboarding_page2")
    object Page2 : Screen("getstarted_page")
    object Page3 : Screen("first_login_page") // Email/Google Login Page
    object Page4 : Screen("five_login_page")  // Optional extra page
    object Page5 : Screen("six_login_page")   // Not used here
    object Page6 : Screen("home_page_first")  // Home
}

@Composable
fun OnboardingNavController() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Page1.route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(Screen.Page1.route) {
            Onboarding_Page2(
                modifier = Modifier,
                navController = navController,
                onNextClick = {
                    navController.navigate(Screen.Page2.route)
                }
            )
        }

        composable(Screen.Page2.route) {
            Getstarted_Page(
                modifier = Modifier,
                onNextClick = {
                    navController.navigate(Screen.Page3.route)
                }
            )
        }

        composable(Screen.Page3.route) {
            First_Login_Page(
                modifier = Modifier,
                navController = navController,
                onNextClick = {
                    navController.navigate(Screen.Page6.route) // Navigate to Home after successful login
                }
            )
        }

        composable(Screen.Page4.route) {
            Five_Login_Page(
                modifier = Modifier,
                navController = navController,
                onNextClick = {
                    navController.navigate(Screen.Page6.route)
                }
            )
        }

        composable(Screen.Page6.route) {
            HomePage_First(
                modifier = Modifier,
                navController = navController,
                onNextClick = {
                    // Optional: Navigate to another screen if needed
                }
            )
        }
    }
}
