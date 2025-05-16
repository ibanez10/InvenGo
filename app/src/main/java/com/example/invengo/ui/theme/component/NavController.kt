import android.app.Activity.RESULT_OK
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.invengo.GoogleAuthUIClient
import com.example.invengo.auth.SignInViewModel
import com.example.invengo.ui.theme.component.*
import com.google.android.gms.auth.api.identity.Identity
//import kotlinx.coroutines.flow.internal.NoOpContinuation.context
import kotlinx.coroutines.launch
//import kotlin.coroutines.jvm.internal.CompletedContinuation.context

sealed class Screen(val route: String) {
    object Page1 : Screen("onboarding_first")
    object Page2 : Screen("getstarted")
    object Page3 : Screen("login_page") // Email/Google Login Page
    object Page4 : Screen("home_page_first") // Home
    object Page5 : Screen("itemData")
    object Page6 : Screen("AddItem")
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
            Onboarding_First(
                modifier = Modifier,
                navController = navController,
                onNextClick = {
                    navController.navigate(Screen.Page2.route)
                }
            )
        }

        composable(Screen.Page2.route) {
            Getstarted(
                modifier = Modifier,
                onNextClick = {
                    navController.navigate(Screen.Page3.route)
                }
            )
        }

        composable(Screen.Page3.route) {
            val context = LocalContext.current
            val lifecycleOwner = LocalLifecycleOwner.current

            val googleAuthUIClient = remember {
                GoogleAuthUIClient(
                    context = context,
                    oneTapClient = Identity.getSignInClient(context)
                )
            }

            val viewModel: SignInViewModel = viewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = {result ->
                    if (result.resultCode == RESULT_OK) {
                        lifecycleOwner.lifecycleScope.launch {
                            val signInResult = googleAuthUIClient.signInWithIntent(
                                intent = result.data?: return@launch
                            )
                            viewModel.onSignedInResult(signInResult)
                        }
                    }
                }
            )

            LaunchedEffect(key1 = state.isSignInSuccessful) {
                if(state.isSignInSuccessful){
                    Toast.makeText(context, "Sign in successfull", Toast.LENGTH_LONG).show()

                }
            }

            Login_Page(
                modifier = Modifier,
                navController = navController,
                onNextClick = {
                    navController.navigate(Screen.Page3.route)
                },
                onSignInClick = {
                    lifecycleOwner.lifecycleScope.launch {
                        val signInIntentSender = googleAuthUIClient.signIn()
                        launcher.launch(
                            IntentSenderRequest.Builder(
                                signInIntentSender ?: return@launch
                            ).build()
                        )
                    }
                },
                state = state
            )
        }
        composable(Screen.Page4.route) {
            HomePage(
                modifier = Modifier,
                navController = navController,
                onNextClick = {
                    navController.navigate(Screen.Page5.route)
                }
            )
        }
        composable(Screen.Page5.route) {
            ItemData (
                modifier = Modifier,
                navController = navController,
                onNextClick = {
                    navController.navigate(Screen.Page6.route)
                }
            )
        }
        composable(Screen.Page6.route) {
            AddItem (
                modifier = Modifier,
                navController = navController,
                onNextClick = {

                }
            )
        }
    }
}
