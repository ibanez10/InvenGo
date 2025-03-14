import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.invengo.ui.theme.component.First_Onboarding
import com.example.invengo.ui.theme.component.Getstarted_Page
import com.example.invengo.ui.theme.component.Onboarding_Page1
import com.example.invengo.ui.theme.component.Onboarding_Page2
import com.example.invengo.ui.theme.component.First_Login_Page
import com.example.invengo.ui.theme.component.Second_Login_Page


sealed class Screen(val route: String) {
    object First : Screen("first_onboarding")
    object Page1 : Screen("onboarding_page1")
    object Page2 : Screen("onboarding_page2")
    object Page3 : Screen("getstarted_page")
    object Page4 : Screen("firts_login_page")
    object Page5 : Screen("second_login_page")
}

@Composable
fun OnboardingNavController() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.First.route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(Screen.First.route) {
            First_Onboarding(modifier = Modifier, navController = navController, onNextClick = {
                navController.navigate(Screen.Page1.route)
            })
        }
        composable(Screen.Page1.route) {
            Onboarding_Page1(modifier = Modifier, navController = navController, onNextClick = {
                navController.navigate(Screen.Page2.route)
            })
        }
        composable(Screen.Page2.route) {
            Onboarding_Page2(modifier = Modifier, navController = navController, onNextClick = {
                navController.navigate(Screen.Page3.route)
            })
        }
        composable(Screen.Page3.route) {
            Getstarted_Page(modifier = Modifier, onNextClick = {
                navController.navigate(Screen.Page4.route)
            })
        }
        composable(Screen.Page4.route) {
            First_Login_Page(modifier = Modifier, navController, onNextClick = {
                navController.navigate(Screen.Page5.route)
            })
        }
        composable(Screen.Page5.route) {
            Second_Login_Page(modifier = Modifier, navController, onNextClick = {})
        }
    }
}
