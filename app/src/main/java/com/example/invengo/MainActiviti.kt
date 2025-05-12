//package com.example.invengo
//
//import android.os.Bundle
//import android.os.PersistableBundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.compose.setContent
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Modifier
//import androidx.core.content.pm.ShortcutInfoCompat.Surface
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import androidx.lifecycle.lifecycleScope
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.example.invengo.auth.SignInViewModel
//import com.google.android.gms.auth.api.identity.Identity
//import kotlinx.coroutines.launch
//
//class MainActiviti : ComponentActivity() {
//    private val googleAuthUIClient by lazy{
//        GoogleAuthUIClient(
//            context = applicationContext,
//            oneTapClient = Identity.getSignInClient(applicationContext)
//        )
//    }
//
//
//    @Composable
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            InvenGoTheme {
//                Surface(
//                    modifier = Modifier.fillMaxSize()
//                )
//            }
//        }
//        val viewModel = viewModel<SignInViewModel>()
//        val state by viewModel.state.collectAsStateWithLifecycle()
//
//        val launcher = rememberLauncherForActivityResult(
//            contract = ActivityResultContracts.StartIntentSenderForResult(),
//            onResult = {result ->
//                if (result.resultCode == RESULT_OK) {
//                    lifecycleScope.launch {
//                        val signInResult = googleAuthUIClient.signInWithIntent(
//                            intent = result.data?: return@launch
//                        )
//                        viewModel.onSignedInResult(signInResult)
//                    }
//                }
//            }
//        )
//        }
//    }
//}