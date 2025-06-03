package com.example.invengo.ui.theme.component

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.invengo.R
import com.example.invengo.ui.theme.PurpleGrey40
import com.example.invengo.ui.theme.Teal
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf

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
    // Fetch data from Firestore
    val currentUser = FirebaseAuth.getInstance().currentUser
    val uid = currentUser?.uid
    LaunchedEffect(uid) {
        if (uid != null) {
            Firebase.firestore.collection("users").document(uid).collection("items")
                .get()
                .addOnSuccessListener { result ->
                    items.clear()
                    for (document in result) {
                        items.add(document.data)
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Gagal mengambil data", Toast.LENGTH_SHORT).show()
                }
        }
    }

    Box(
        Modifier.fillMaxSize().background(color = Color.Black)
    ) {
        Image(
            painter = painterResource(id = R.drawable.frame),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column( Modifier.padding(vertical = 20.dp, horizontal = 20.dp).verticalScroll(scrollState)) {
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
            Text(
                text = "Account",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight(700)
            )
            Spacer(Modifier.height(20.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(Color.DarkGray, Teal, Color.DarkGray),
                        ),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (photoUrl != null) {
                    AsyncImage(
                        model = photoUrl,
                        contentDescription = "Foto Profil",
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                    )
                } else {
                    Image(
                        painter = painterResource(R.drawable.avatar),
                        contentDescription = null,
                        modifier = Modifier.size(50.dp)
                    )
                }

                Spacer(modifier = Modifier.width(15.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = displayName,
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight(700)
                        )
                        Spacer(Modifier.width(8.dp))
                        Box(
                            Modifier
                                .background(color = Teal, shape = RoundedCornerShape(20.dp))
                                .padding(horizontal = 10.dp)
                        ) {
                            Text(text = "Admin", color = Color.White, fontSize = 14.sp)
                        }
                    }
                    Spacer(Modifier.height(3.dp))
                    Text(text = email, color = Color.LightGray, fontSize = 14.sp)
                }
            }

            Spacer(Modifier.height(30.dp))
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(color = Teal, shape = RoundedCornerShape(30.dp))
                    .padding(top =10.dp, bottom = 0.dp)
            ) {
                Column {
                    Row(
                        Modifier
                            .padding(horizontal = 20.dp, vertical = 0.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically // <-- kuncinya di sini
                    ) {
                        Image(
                            painter = painterResource(R.drawable.time),
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(Modifier.width(5.dp))
                        Text(text = "History", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight(600))
                    }

                    Spacer(Modifier.height(3.dp)) // Beri jarak agar tidak saling menempel

                    // Box hitam di bawah History
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .height(350.dp)
                            .padding()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Black,
                                        Color.DarkGray,
                                        Color.Black,
                                    )
                                ),
                                shape = RoundedCornerShape(30.dp)
                            )
                    ) {
                        Row(Modifier
                            .padding(20.dp)
                            ,verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(R.drawable.stockout),
                                contentDescription = null,
                                modifier = Modifier.size(25.dp)
                            )
                            Spacer(Modifier.width(5.dp))
                            Text(text = "Inbound Stock", fontSize = 15.sp, color = Color.Green, textAlign = TextAlign.Left)
                        }
                        Column(Modifier.verticalScroll(historyScrollState)) {
                            items.forEach { item ->
                                Column(Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp, vertical = 10.dp)
                                    .border(1.dp, Color.Gray, shape = RoundedCornerShape(10.dp))
                                    .padding(10.dp)
                                )
                                {
                                    val createdAt = item["created_at"]
                                    val dateString = if (createdAt is Timestamp) {
                                        val date = createdAt.toDate()
                                        val formatter = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
                                        formatter.format(date)
                                    } else {
                                        "-"
                                    }
                                    val date = null
                                    Text(
                                        text = "${dateString ?: "-"}",
                                        color = Color.LightGray,
                                        fontSize = 12.sp
                                    )
                                    Text(
                                        text = "Item Id: ${item["item_id"] ?: "-"}",
                                        color = Color.White,
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        text = "Item Name: ${item["name"] ?: "-"}",
                                        color = Color.White,
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        text = "Stock: ${item["opening_stock"] ?: "-"}",
                                        color = Color.White,
                                        fontSize = 14.sp
                                    )
                                }
                            }
                        }}
                }
            }

            Spacer(Modifier.height(30.dp))
            Button(
                onClick = { onNextClick(

                ) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Teal)
            ) {
                Spacer(modifier = Modifier.padding(vertical = 20.dp))
                Text("Export monthly release report as PDF", fontSize = 16.sp, color = Color.White)
            }

            Spacer(Modifier.height(20.dp))
            Column (Modifier
            ) {
                Text(text = "Account setting", color = Color.White, fontSize = 19.sp)
                Spacer(Modifier.height(10.dp))
                Column(Modifier
                    .background(Color.DarkGray, shape = RoundedCornerShape(20.dp))
                    .padding(10.dp)
                    .fillMaxWidth()
                    .clickable {  }
                ) {
                    Row(Modifier,verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(R.drawable.pw_security),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp)
                        )
                        Spacer(Modifier.width(15.dp))
                        Text(text = "Password & Security", color = Color.White, fontSize = 17.sp)
                    }

                }
                Spacer(Modifier.height(30.dp))
                Text(text = "Information", color = Color.White, fontSize = 19.sp)
                Spacer(Modifier.height(10.dp))
                Column(Modifier
                    .background(Color.DarkGray, shape = RoundedCornerShape(20.dp))
                    .padding(10.dp)
                    .fillMaxWidth()
                ) {
                    Row(Modifier.clickable {  }.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(R.drawable.terms),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp)
                        )
                        Spacer(Modifier.width(15.dp))
                        Text(text = "Terms & conditions of use", color = Color.White, fontSize = 17.sp)
                    }
                    Spacer(Modifier.height(10.dp))
                    HorizontalDivider(thickness = 1.dp, color = Color.Gray)
                    Spacer(Modifier.height(10.dp))
                    Row(Modifier.clickable {  }.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(R.drawable.contact),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp)
                        )
                        Spacer(Modifier.width(15.dp))
                        Text(text = "Contact Us", color = Color.White, fontSize = 17.sp)
                    }
                }
                Spacer(Modifier.height(30.dp))
                Row(Modifier.background(Color.DarkGray, shape = RoundedCornerShape(20.dp)).fillMaxWidth().padding(15.dp).clickable {  },verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(R.drawable.logout),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(Modifier.width(15.dp))
                    Text(text = "Log Out", color = Color.Red, fontSize = 17.sp)
                }
            }
        }
    }
}