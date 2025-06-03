import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.invengo.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.example.invengo.ui.theme.Teal
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Locale


@Composable
fun ItemData(
    modifier: Modifier = Modifier,
    navController: NavController,
    onNextClick: () -> Unit
) {
    val context = LocalContext.current
    val items = remember { mutableStateListOf<Map<String, Any>>() }

    // Fetch data from Firestore
    val currentUser = FirebaseAuth.getInstance().currentUser
    val uid = currentUser?.uid
    LaunchedEffect(uid) {
        if (uid != null) {
            val db = Firebase.firestore
            items.clear()

            val itemsRef = db.collection("users").document(uid).collection("items")
            val inboundRef = db.collection("users").document(uid).collection("inbound_stock")

            itemsRef.get().addOnSuccessListener { itemDocs ->
                for (itemDoc in itemDocs) {
                    val data = itemDoc.data.toMutableMap()
                    val itemName = data["name"] as? String ?: continue
                    val opening = (data["opening_stock"] as? Long)?.toInt() ?: 0

                    // Ambil semua inbound dengan nama yang sama
                    inboundRef.whereEqualTo("product_name", itemName).get().addOnSuccessListener { inboundDocs ->
                        val totalInbound = inboundDocs.sumOf {
                            (it.get("quantity") as? Long ?: 0L).toInt()
                        }

                        data["total_stock"] = opening + totalInbound
                        items.add(data)
                    }
                }
            }.addOnFailureListener {
                Toast.makeText(context, "Gagal mengambil data", Toast.LENGTH_SHORT).show()
            }
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

            // Daftar Barang dari Firestore
            if (items.isEmpty()) {
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
                        val date = null
                        Column(
                            Modifier
                                .background(Teal, shape = RoundedCornerShape(20.dp))
                        ) {
                            Row(
                                Modifier
                                    .padding( top = 10.dp, end = 20.dp, start = 20.dp, bottom = 5.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ){
                                Text(
                                    text = "${dateString ?: "-"}",
                                    color = Color.White,
                                    fontSize = 15.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.weight(1f)
                                )
                            Text(
                                text = "${item["item_id"] ?: "-"}",
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
                                    shape = RoundedCornerShape(20.dp))
                                .padding(16.dp)
                        ) {
                            Row( horizontalArrangement = Arrangement.SpaceBetween) {
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
                        Spacer(modifier.padding(start= 2.dp,top = 11.dp,5.dp))
                        Button(
                            onClick = {
                                val itemId = item["item_id"].toString()
                                if(uid != null && itemId != null){
                                    Firebase.firestore.collection("users").document(uid).collection("items")
                                        .document(itemId).delete()
                                        .addOnSuccessListener {
                                            Toast.makeText(
                                                context,"${itemId} berhasil dihapus",Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                        .addOnFailureListener {
                                            Toast.makeText(
                                                context, "Gagal menghapus ${itemId}", Toast.LENGTH_SHORT
                                            ).show()
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
                        }}
                        Spacer(Modifier.height(30.dp))
                    }
                }
            }
        }
    }
}