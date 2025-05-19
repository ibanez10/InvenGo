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
    object OnboardingFirst : Screen("onboarding_first")
    object OnboardingSeccond : Screen("onboarding_seccond")
    object GetStarted : Screen("getstarted")
    object LoginPage : Screen("login_page") // Email/Google Login Page
    object HomePage : Screen("home_page_first") // Home
    object ItemData : Screen("itemData")
    object AddItem : Screen("AddItem")
    object InboundStock : Screen("InboundStock")
    object StockRelease : Screen("StockRelease")
}

@Composable
fun OnboardingNavController() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.OnboardingFirst.route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(Screen.OnboardingFirst.route) {
            Onboarding_First(
                modifier = Modifier,
                navController = navController,
                onNextClick = {
                    navController.navigate(Screen.OnboardingSeccond.route)
                }
            )
        }

        composable(Screen.OnboardingSeccond.route) {
            Onboarding_Seccond(
                modifier = Modifier,
                navController = navController,
                onNextClick = {
                    navController.navigate(Screen.GetStarted.route)
                }
            )
        }

        composable(Screen.GetStarted.route) {
            Getstarted(
                modifier = Modifier,
                onNextClick = {
                    navController.navigate(Screen.LoginPage.route)
                }
            )
        }

        composable(Screen.LoginPage.route) {
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
                    navController.navigate(Screen.LoginPage.route)
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
        composable(Screen.HomePage.route) {
            HomePage(
                modifier = Modifier,
                navController = navController,
                onItemDataClick = {
                    navController.navigate(Screen.ItemData.route)
                },
                onInboundStockClick = {
                    navController.navigate(Screen.InboundStock.route)
                },
                onStockReleaseClick = {
                    navController.navigate(Screen.StockRelease.route)
                }
            )
        }

        composable(Screen.ItemData.route) {
            ItemData (
                modifier = Modifier,
                navController = navController,
                onNextClick = {
                    navController.navigate(Screen.AddItem.route)
                }
            )
        }
        composable(Screen.AddItem.route) {
            AddItem (
                modifier = Modifier,
                navController = navController,
                onNextClick = {

                }
            )
        }
        composable(Screen.InboundStock.route) {
            InbounStock(
                modifier = Modifier,
                navController = navController,
                onNextClick = {

                }
            )
        }
        composable(Screen.StockRelease.route) {
            StockRelease(
                modifier = Modifier,
                navController = navController,
                onNextClick = {

                }
            )
        }
    }
}