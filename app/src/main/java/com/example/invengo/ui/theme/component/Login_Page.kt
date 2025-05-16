package com.example.invengo.ui.theme.component

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.invengo.R
import com.example.invengo.ui.theme.Teal
import com.example.invengo.AuthViewModel
import com.example.invengo.auth.SignInState
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login_Page(
    modifier: Modifier = Modifier,
    navController: NavController,
    onNextClick: () -> Unit,
    state: SignInState,
    onSignInClick: () -> Unit
) {
    val context = LocalContext.current
    val activity = context as Activity
    val authViewModel: AuthViewModel = viewModel()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isFocused1 by remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }
    var loginError by remember { mutableStateOf("") }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            authViewModel.loginWithGoogleIntent(
                data = result.data,
                onSuccess = {
                    navController.navigate("home_page_first") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onError = {
                    loginError = it
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
            )
        } else {
            loginError = "Login gagal, silakan coba lagi."
            Toast.makeText(context, "Login gagal", Toast.LENGTH_SHORT).show()
        }
    }


        Box(
            modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Image(
                painter = painterResource(id = R.drawable.frame),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(Modifier.fillMaxWidth().padding(vertical = 15.dp, horizontal = 5.dp)) {
                Button(
                    onClick = { navController.popBackStack() },
                    colors = ButtonDefaults.textButtonColors(contentColor = Color.Transparent),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.arrow2),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                }

                Column(Modifier.fillMaxWidth().padding(vertical = 70.dp, horizontal = 25.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.logfrm),
                        contentDescription = null,
                        modifier = Modifier.size(45.dp)
                    )
                    Spacer(Modifier.height(15.dp))
                    Text("Hi there!", fontSize = 50.sp, color = Color.White, fontWeight = FontWeight.Bold)
                    Text("Welcome back", fontSize = 50.sp, color = Color.White, fontWeight = FontWeight.Bold)
                    Text(
                        "Enter your credentials to access inventory and operations.",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )

                    Spacer(Modifier.height(20.dp))

                    TextField(
                        value = email,
                        onValueChange = { email = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                BorderStroke(1.dp, if (isFocused1) Color.Green else Color.Gray),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .onFocusChanged { isFocused1 = it.isFocused },
                        label = { Text("Email", color = Color.White) },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Email,
                                contentDescription = null,
                                tint = Color.LightGray,
                                modifier = Modifier.size(30.dp)
                            )
                        },
                        textStyle = LocalTextStyle.current.copy(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        ),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        )
                    )

                    Spacer(Modifier.height(10.dp))

                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                BorderStroke(1.dp, if (isFocused) Color.Green else Color.Gray),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .onFocusChanged { isFocused = it.isFocused },
                        label = { Text("Password", color = Color.White) },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_key_24),
                                contentDescription = null,
                                modifier = Modifier.size(30.dp)
                            )
                        },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    painter = painterResource(
                                        id = if (passwordVisible) R.drawable.baseline_visibility_24 else R.drawable.baseline_visibility_off_24
                                    ),
                                    contentDescription = null,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        },
                        textStyle = LocalTextStyle.current.copy(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        ),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        )
                    )

                    Spacer(Modifier.height(3.dp))
                    Row(Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.weight(1f))
                        Text("Forget password?", fontSize = 15.sp, color = Color.LightGray)
                    }

                    Spacer(Modifier.height(20.dp))

                    Button(
                        onClick = {
                            onSignInClick
//                        authViewModel.loginWithEmail(email, password,
//                            onSuccess = {
//                                navController.navigate("home_page_first") {
//                                    popUpTo("login") { inclusive = true }
//                                }
//                            },
//                            onError = {
//                                loginError = it // Menyimpan pesan kesalahan
//                                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
//                            })
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Teal)
                    ) {
                        Spacer(Modifier.padding(vertical = 20.dp))
                        Text("Login", fontSize = 20.sp)
                    }

                    Spacer(Modifier.height(15.dp))


                    Button(
                        onClick = {
                            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                .requestIdToken("1004455573154-lq9bp2o3hdoqqnrbq9l3qkkuvdnepj7u.apps.googleusercontent.com") // ganti ini
                                .requestEmail()
                                .build()

                            val signInClient = GoogleSignIn.getClient(context, gso)
                            launcher.launch(signInClient.signInIntent)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(20.dp)),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(Color.Transparent)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(10.dp)) {
                            Image(
                                painter = painterResource(id = R.drawable.google),
                                contentDescription = null,
                                modifier = Modifier.size(30.dp)
                            )
                            Spacer(Modifier.width(15.dp))
                            Text("Continue with Google", fontSize = 16.sp)
                        }
                    }
                }
            }
        }
}
