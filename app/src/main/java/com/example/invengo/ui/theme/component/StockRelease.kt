package com.example.invengo.ui.theme.component

import android.app.DatePickerDialog
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.invengo.R
import com.example.invengo.ui.theme.GrayB
import com.example.invengo.ui.theme.Teal
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.storage.storage
import java.security.Timestamp
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockRelease(
    modifier: Modifier = Modifier,
    navController: NavController,
    onNextClick: () -> Unit
) {

    Image(
        painter = painterResource(id = R.drawable.frame),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
    val db = com.google.firebase.ktx.Firebase.firestore
    val currentUser = FirebaseAuth.getInstance().currentUser
    val uid = currentUser?.uid
    var itemList by remember { mutableStateOf<List<String>>(emptyList()) }
    var expanded by remember { mutableStateOf(false) }



    LaunchedEffect(Unit) {
        if (uid != null) {
            db.collection("users").document(uid).collection("items")
                .get()
                .addOnSuccessListener { result ->
                    val names = result.documents.mapNotNull { it.getString("name") }
                    itemList = names
                }
        }
    }

    Box(Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 20.dp)) {
        Column {
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
                    text = "Stock Release",
                    fontSize = 23.sp,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(30.dp))

            Column(Modifier.fillMaxWidth()) {
                var textItemId by remember { mutableStateOf("") }
                var textItemName by remember { mutableStateOf("") }
                var openingStock by remember { mutableStateOf("") }
                var description by remember { mutableStateOf("") }
                var isFocused1 by remember { mutableStateOf(false) }
                var isFocused2 by remember { mutableStateOf(false) }
                var isFocused3 by remember { mutableStateOf(false) }
                var isFocused4 by remember { mutableStateOf(false) }

                val context = LocalContext.current
                var calendar = remember { Calendar.getInstance() }
                var selectedDate by remember { mutableStateOf(Calendar.getInstance()) }
                val datePickerDialog = remember {
                    DatePickerDialog(
                        context,
                        { _, year, month, dayOfMonth ->
                            selectedDate.set(year, month, dayOfMonth)
                            val formattedDate = String.format("%02d-%02d-%04d", dayOfMonth, month + 1, year)
                            textItemId = formattedDate
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    )
                }

                Text(text = "Select Date", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight(500))
                Spacer(modifier = Modifier.height(5.dp))
                TextField(
                    value = textItemId,
                    onValueChange = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = GrayB, shape = RoundedCornerShape(10.dp))
                        .border(
                            BorderStroke(1.5.dp, if (isFocused1) Teal else Color.Transparent),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .onFocusChanged { isFocused1 = it.isFocused },
                    label = { Text("Choose Date", color = Color.Gray) },
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
                    ),
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.date),
                            contentDescription = null,
                            modifier = Modifier
                                .size(25.dp)
                                .clickable { datePickerDialog.show() },
                            tint = Color.Unspecified
                        )
                    },
                    readOnly = true,
                    maxLines = 1
                )

                Spacer(Modifier.height(10.dp))
                Text(text = "Product Name", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight(500))
                Spacer(Modifier.height(5.dp))
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    TextField(
                        value = textItemName,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Select Item", color = Color.Gray) },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                            .background(color = GrayB, shape = RoundedCornerShape(10.dp))
                            .border(
                                BorderStroke(1.5.dp, if (isFocused2) Teal else Color.Transparent),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .onFocusChanged { isFocused2 = it.isFocused },
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

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        itemList.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(text = item) },
                                onClick = {
                                    textItemName = item
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(Modifier.height(10.dp))
                Text(text = "Quantity", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight(500))
                Spacer(Modifier.height(5.dp))
                TextField(
                    value = openingStock,
                    onValueChange = {
                        if (it.all { char -> char.isDigit() }) {
                            openingStock = it
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = GrayB, shape = RoundedCornerShape(10.dp))
                        .border(
                            BorderStroke(1.5.dp, if (isFocused3) Teal else Color.Transparent),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .onFocusChanged { isFocused3 = it.isFocused },
                    label = { Text("Ex. 5", color = Color.Gray) },
                    textStyle = LocalTextStyle.current.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    maxLines = 1
                )

                Spacer(Modifier.height(10.dp))
                Text(text = "Description", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight(500))
                Spacer(Modifier.height(5.dp))
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .background(color = GrayB, shape = RoundedCornerShape(10.dp))
                        .border(
                            BorderStroke(1.5.dp, if (isFocused4) Teal else Color.Transparent),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .onFocusChanged { isFocused4 = it.isFocused },
                    label = { Text("Add additional notes about the stock item...", color = Color.Gray) },
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
                    ),
                    maxLines = 1
                )

                Spacer(Modifier.height(30.dp))
                Button(
                    onClick = {
                        val stockData = hashMapOf(
                            "date" to textItemId,
                            "product_name" to textItemName,
                            "quantity" to openingStock.toIntOrNull(),
                            "description" to description
                        )

                        if (uid != null) {
                            db.collection("users").document(uid)
                                .collection("items").whereEqualTo("name", textItemName).get()
                                .addOnSuccessListener { result ->
                                    if (!result.isEmpty) {
                                        val itemDoc = result.documents[0]
                                        val itemId = itemDoc.id
                                        val currentStock = itemDoc.getLong("opening_stock") ?: 0
                                        val addedStock = openingStock.toIntOrNull() ?: 0
                                        val updatedStock = currentStock - addedStock

                                        // 1. Tambahkan ke inbound_stock
                                        db.collection("users").document(uid)
                                            .collection("items").document(itemId)
                                            .collection("release_stock").add(stockData)

                                        // 2. Update stok di item
                                        db.collection("users").document(uid)
                                            .collection("items").document(itemId)
                                            .update("opening_stock", updatedStock)
                                            .addOnSuccessListener {
                                                Toast.makeText(context, "Barang berhasil dikeluarkan", Toast.LENGTH_SHORT).show()
                                                // Reset semua field
                                                textItemId = ""
                                                textItemName = ""
                                                openingStock = ""
                                                description = ""
                                                selectedDate = Calendar.getInstance()

                                                onNextClick()
                                            }
                                            .addOnFailureListener {
                                                Toast.makeText(context, "Gagal mengupdate stok", Toast.LENGTH_SHORT).show()
                                            }
                                    }
                                }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Teal)
                ) {
                    Spacer(modifier = Modifier.padding(vertical = 20.dp))
                    Text("Save", fontSize = 20.sp, color = Color.White)
                }
            }
        }
    }
}