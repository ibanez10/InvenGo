import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.invengo.R
import com.example.invengo.ui.theme.Teal
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ItemData(
    modifier: Modifier = Modifier,
    navController: NavController,
    onNextClick: () -> Unit
) {
    val context = LocalContext.current
    val items = remember { mutableStateListOf<Map<String, Any>>() }
    var isLoading by remember { mutableStateOf(true) }
    var isDeleting by remember { mutableStateOf<String?>(null) }

    // Fetch data from Firestore
    fun fetchData(uid: String) {
        val db = Firebase.firestore
        items.clear()

        val itemsRef = db.collection("users").document(uid).collection("items")
        val inboundRef = db.collection("users").document(uid).collection("inbound_stock")

        itemsRef.get().addOnSuccessListener { itemDocs ->
            var loadedCount = 0
            val totalItems = itemDocs.size()

            for (itemDoc in itemDocs) {
                val data = itemDoc.data.toMutableMap()
                val itemName = data["name"] as? String ?: continue
                val opening = (data["opening_stock"] as? Long)?.toInt() ?: 0

                inboundRef.whereEqualTo("product_name", itemName).get().addOnSuccessListener { inboundDocs ->
                    val totalInbound = inboundDocs.sumOf {
                        (it.get("quantity") as? Long ?: 0L).toInt()
                    }

                    data["total_stock"] = opening + totalInbound
                    items.add(data)

                    loadedCount++
                    if (loadedCount == totalItems) {
                        isLoading = false
                    }
                }.addOnFailureListener {
                    loadedCount++
                    if (loadedCount == totalItems) {
                        isLoading = false
                    }
                }
            }

            if (totalItems == 0) {
                isLoading = false
            }
        }.addOnFailureListener {
            Toast.makeText(context, "Gagal mengambil data", Toast.LENGTH_SHORT).show()
            isLoading = false
        }
    }

    val currentUser = FirebaseAuth.getInstance().currentUser
    val uid = currentUser?.uid
    LaunchedEffect(uid) {
        if (uid != null) {
            fetchData(uid)
        } else {
            isLoading = false
        }
    }

    Box(Modifier.fillMaxSize().background(color = Color.Black)) {
        Image(
            painter = painterResource(id = R.drawable.frame),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 20.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
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

                Text(
                    text = "Item Data",
                    fontSize = 23.sp,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Search + Add Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                var text by remember { mutableStateOf("") }

                TextField(
                    value = text,
                    onValueChange = { text = it },
                    placeholder = { Text("Search...", color = Color.Gray) },
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_search_24),
                            contentDescription = "Search",
                            tint = Color.DarkGray
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .background(Color.Gray, shape = RoundedCornerShape(20.dp))
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = onNextClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues(10.dp),
                ) {
                    Image(
                        painter = painterResource(R.drawable.plus),
                        contentDescription = null,
                        modifier = Modifier.size(45.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            } else if (items.isEmpty()) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 180.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.canot),
                        contentDescription = null,
                        modifier = Modifier.size(200.dp)
                    )
                    Text(
                        text = "There is no input provided",
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 10.dp, bottom = 80.dp)
                ) {
                    items(items) { item ->
                        val createdAt = item["created_at"]
                        val dateString = if (createdAt is Timestamp) {
                            val date = createdAt.toDate()
                            val formatter = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
                            formatter.format(date)
                        } else {
                            "-"
                        }

                        val itemId = item["item_id"].toString()

                        Column(
                            Modifier
                                .background(Teal, shape = RoundedCornerShape(20.dp))
                        ) {
                            Row(
                                Modifier
                                    .padding(top = 10.dp, end = 20.dp, start = 20.dp, bottom = 5.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = dateString,
                                    color = Color.White,
                                    fontSize = 15.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    text = itemId,
                                    color = Color.White,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.End,
                                    modifier = Modifier.weight(1f)
                                )
                            }

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        brush = Brush.verticalGradient(
                                            colors = listOf(Color.Gray, Color.DarkGray),
                                        ),
                                        shape = RoundedCornerShape(20.dp)
                                    )
                                    .padding(16.dp)
                            ) {
                                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(
                                        text = "${item["name"] ?: "-"}",
                                        color = Color.White,
                                        fontSize = 23.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Spacer(Modifier.height(30.dp))
                                    Text(
                                        text = "${item["total_stock"] ?: "-"}",
                                        color = Color.Yellow,
                                        fontSize = 25.sp,
                                        maxLines = 1,
                                        fontWeight = FontWeight(600),
                                        overflow = TextOverflow.Ellipsis,
                                        textAlign = TextAlign.End,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Spacer(Modifier.height(30.dp))
                                }

                                Text(
                                    text = "${item["numeric_price"] ?: "-"} | Harga: ${item["retail_price"] ?: "-"}",
                                    color = Color.Green,
                                    fontSize = 16.sp
                                )

                                Spacer(modifier = Modifier.padding(start = 2.dp, top = 11.dp, end = 5.dp))

                                if (isDeleting == itemId) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(32.dp),
                                        color = Color.Red,
                                        strokeWidth = 3.dp
                                    )
                                } else {
                                    Button(
                                        onClick = {
                                            if (uid != null && itemId.isNotEmpty()) {
                                                isDeleting = itemId
                                                Firebase.firestore
                                                    .collection("users").document(uid)
                                                    .collection("items").document(itemId)
                                                    .delete()
                                                    .addOnSuccessListener {
                                                        items.remove(item)
                                                        Toast.makeText(
                                                            context, "$itemId berhasil dihapus", Toast.LENGTH_SHORT
                                                        ).show()
                                                        isDeleting = null
                                                    }
                                                    .addOnFailureListener {
                                                        Toast.makeText(
                                                            context, "Gagal menghapus $itemId", Toast.LENGTH_SHORT
                                                        ).show()
                                                        isDeleting = null
                                                    }
                                            }
                                        },
                                        modifier = Modifier
                                            .width(100.dp)
                                            .height(35.dp),
                                        shape = RoundedCornerShape(5.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color.Red,
                                            contentColor = Color.White
                                        )
                                    ) {
                                        Text("delete", fontSize = 17.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }

                        Spacer(Modifier.height(30.dp))
                    }
                }
            }
        }
    }
}
