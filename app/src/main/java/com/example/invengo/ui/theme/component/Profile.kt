package com.example.invengo.ui.theme.component

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.invengo.R
import com.example.invengo.ui.theme.Teal
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun profile(modifier: Modifier = Modifier, navController: NavController, onNextClick: () -> Unit) {
    val scrollState = rememberScrollState()
    val user = FirebaseAuth.getInstance().currentUser
    val displayName = user?.displayName ?: "Admin"
    val email = user?.email ?: "Email tidak tersedia"
    val photoUrl = user?.photoUrl
    val context = LocalContext.current
    val items = remember { mutableStateListOf<Map<String, Any>>() }
    val historyScrollState = rememberScrollState()
    var isLoading by remember { mutableStateOf(true) }

    val uid = user?.uid
    LaunchedEffect(uid) {
        if (uid != null) {
            Firebase.firestore.collection("users").document(uid).collection("items")
                .get()
                .addOnSuccessListener { result ->
                    items.clear()
                    for (document in result) {
                        items.add(document.data)
                    }
                    isLoading = false
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Gagal mengambil data", Toast.LENGTH_SHORT).show()
                    isLoading = false
                }
        } else {
            isLoading = false
        }
    }

    Box(Modifier.fillMaxSize().background(Color.Black)) {
        Image(
            painter = painterResource(id = R.drawable.frame),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            Modifier
                .padding(20.dp)
                .verticalScroll(scrollState)
        ) {
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.textButtonColors(contentColor = Color.Transparent),
                contentPadding = PaddingValues(1.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arrowtrans),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(Modifier.height(20.dp))
            Text("Account", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(20.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(listOf(Color.DarkGray, Teal, Color.DarkGray)),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (photoUrl != null) {
                    AsyncImage(
                        model = photoUrl,
                        contentDescription = "Foto Profil",
                        modifier = Modifier.size(50.dp).clip(CircleShape)
                    )
                } else {
                    Image(
                        painter = painterResource(R.drawable.avatar),
                        contentDescription = null,
                        modifier = Modifier.size(50.dp)
                    )
                }

                Spacer(Modifier.width(15.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(displayName, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.width(8.dp))
                        Box(
                            Modifier
                                .background(Teal, shape = RoundedCornerShape(20.dp))
                                .padding(horizontal = 10.dp)
                        ) {
                            Text("Admin", color = Color.White, fontSize = 14.sp)
                        }
                    }
                    Spacer(Modifier.height(3.dp))
                    Text(email, color = Color.LightGray, fontSize = 14.sp)
                }
            }

            Spacer(Modifier.height(30.dp))
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(Teal, shape = RoundedCornerShape(30.dp))
                    .padding(top = 10.dp)
            ) {
                Column {
                    Row(
                        Modifier.padding(horizontal = 20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(R.drawable.time),
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(Modifier.width(5.dp))
                        Text("History", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                    }

                    Spacer(Modifier.height(3.dp))

                    Column(
                        Modifier
                            .fillMaxWidth()
                            .height(350.dp)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(Color.Black, Color.DarkGray, Color.Black)
                                ),
                                shape = RoundedCornerShape(30.dp)
                            )
                    ) {
                        Row(
                            Modifier.padding(20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(R.drawable.stockout),
                                contentDescription = null,
                                modifier = Modifier.size(25.dp)
                            )
                            Spacer(Modifier.width(5.dp))
                            Text("Inbound Stock", fontSize = 15.sp, color = Color.Green)
                        }

                        if (isLoading) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(color = Color.White)
                            }
                        } else {
                            Column(Modifier.verticalScroll(historyScrollState)) {
                                items.forEach { item ->
                                    val createdAt = item["created_at"]
                                    val dateString = if (createdAt is Timestamp) {
                                        val date = createdAt.toDate()
                                        val formatter = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
                                        formatter.format(date)
                                    } else {
                                        "-"
                                    }

                                    Column(
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 20.dp, vertical = 10.dp)
                                            .border(1.dp, Color.Gray, shape = RoundedCornerShape(10.dp))
                                            .padding(10.dp)
                                    ) {
                                        Text(dateString, color = Color.LightGray, fontSize = 12.sp)
                                        Text("Item Id: ${item["item_id"] ?: "-"}", color = Color.White, fontSize = 14.sp)
                                        Text("Item Name: ${item["name"] ?: "-"}", color = Color.White, fontSize = 14.sp)
                                        Text("Stock: ${item["opening_stock"] ?: "-"}", color = Color.White, fontSize = 14.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(30.dp))
            Button(
                onClick = onNextClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Teal)
            ) {
                Spacer(Modifier.padding(vertical = 20.dp))
                Text("Export monthly release report as PDF", fontSize = 16.sp, color = Color.White)
            }

            Spacer(Modifier.height(20.dp))
            Column {
                Text("Account setting", color = Color.White, fontSize = 19.sp)
                Spacer(Modifier.height(10.dp))
                Column(
                    Modifier
                        .background(Color.DarkGray, shape = RoundedCornerShape(20.dp))
                        .padding(10.dp)
                        .fillMaxWidth()
                        .clickable { }
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(R.drawable.pw_security),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp)
                        )
                        Spacer(Modifier.width(15.dp))
                        Text("Password & Security", color = Color.White, fontSize = 17.sp)
                    }
                }

                Spacer(Modifier.height(30.dp))
                Text("Information", color = Color.White, fontSize = 19.sp)
                Spacer(Modifier.height(10.dp))
                Column(
                    Modifier
                        .background(Color.DarkGray, shape = RoundedCornerShape(20.dp))
                        .padding(10.dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        Modifier.clickable { }.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(R.drawable.terms),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp)
                        )
                        Spacer(Modifier.width(15.dp))
                        Text("Terms & conditions of use", color = Color.White, fontSize = 17.sp)
                    }
                    Spacer(Modifier.height(10.dp))
                    HorizontalDivider(thickness = 1.dp, color = Color.Gray)
                    Spacer(Modifier.height(10.dp))
                    Row(
                        Modifier.clickable { }.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(R.drawable.contact),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp)
                        )
                        Spacer(Modifier.width(15.dp))
                        Text("Contact Us", color = Color.White, fontSize = 17.sp)
                    }
                }

                Spacer(Modifier.height(30.dp))
                Row(
                    Modifier
                        .background(Color.DarkGray, shape = RoundedCornerShape(20.dp))
                        .fillMaxWidth()
                        .padding(15.dp)
                        .clickable { },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.logout),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(Modifier.width(15.dp))
                    Text("Log Out", color = Color.Red, fontSize = 17.sp)
                }
            }
        }
    }
}
