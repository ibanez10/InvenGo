import android.app.Activity.RESULT_OK
import android.provider.ContactsContract.Profile
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.invengo.GoogleAuthUIClient
import com.example.invengo.auth.SignInViewModel
import com.example.invengo.ui.theme.component.*
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch

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
    object Profile : Screen("Profile")
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun OnboardingNavController() {
    val navController = rememberAnimatedNavController()
    val context = LocalContext.current

    var backPressedTime by remember { mutableStateOf(0L) }

    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.OnboardingFirst.route,
        modifier = Modifier.fillMaxSize(),
        enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }, animationSpec = tween(300)) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = tween(300)) },
        popEnterTransition = { slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = tween(300)) },
        popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = tween(300)) },
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
                onResult = { result ->
                    if (result.resultCode == android.app.Activity.RESULT_OK) {
                        lifecycleOwner.lifecycleScope.launch {
                            val signInResult = googleAuthUIClient.signInWithIntent(
                                intent = result.data ?: return@launch
                            )
                            viewModel.onSignedInResult(signInResult)
                        }
                    }
                }
            )

            LaunchedEffect(key1 = state.isSignInSuccessful) {
                if (state.isSignInSuccessful) {
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
            BackHandler(enabled = true) {
                val currentTime = System.currentTimeMillis()
                if (currentTime - backPressedTime < 2000) {
                    (context as? android.app.Activity)?.finish()
                } else {
                    Toast.makeText(context, "Tekan sekali lagi untuk keluar", Toast.LENGTH_SHORT).show()
                    backPressedTime = currentTime
                }
            }

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
                },
                onProfileClick = {

                    navController.navigate(Screen.Profile.route)}
            )
        }

        composable(Screen.ItemData.route) {
            ItemData(
                modifier = Modifier,
                navController = navController,
                onNextClick = {
                    navController.navigate(Screen.AddItem.route)
                }
            )
        }
        composable(Screen.AddItem.route) {
            AddItem(
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
        composable(Screen.Profile.route) {
            profile (
                modifier = Modifier,
                navController = navController,
                onNextClick = {

                }
            )
        }
    }
}
