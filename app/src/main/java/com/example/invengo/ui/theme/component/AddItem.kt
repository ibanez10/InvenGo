package com.example.invengo.ui.theme.component

import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
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
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.UUID
import android.widget.Toast

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItem(
    modifier: Modifier = Modifier,
    navController: NavController,
    onNextClick: () -> Unit
) {
    val scrollState = rememberScrollState()
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    val imageBitmap: ImageBitmap? = remember(selectedImageUri) {
        selectedImageUri?.let { uri ->
            try {
                if (Build.VERSION.SDK_INT < 28) {
                    @Suppress("DEPRECATION")
                    android.provider.MediaStore.Images.Media.getBitmap(context.contentResolver, uri).asImageBitmap()
                } else {
                    val source = ImageDecoder.createSource(context.contentResolver, uri)
                    ImageDecoder.decodeBitmap(source).asImageBitmap()
                }
            } catch (e: Exception) {
                null
            }
        }
    }

    var textItemId by remember { mutableStateOf("") }
    var textItemName by remember { mutableStateOf("") }
    var openingStock by remember { mutableStateOf("") }
    var priceToSell1 by remember { mutableStateOf("") }
    var priceToSell2 by remember { mutableStateOf("") }
    var isFocused1 by remember { mutableStateOf(false) }
    var isFocused2 by remember { mutableStateOf(false) }
    var isFocused3 by remember { mutableStateOf(false) }
    var isFocused4 by remember { mutableStateOf(false) }
    var isFocused5 by remember { mutableStateOf(false) }

    Image(
        painter = painterResource(id = R.drawable.frame),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )

    Box(Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 20.dp)) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(scrollState)
        ) {
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
                    text = "Add Item",
                    fontSize = 23.sp,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(30.dp))
            Spacer(Modifier.height(15.dp))

            Column(Modifier.fillMaxWidth()) {
                Text("Item ID", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Medium)
                Spacer(Modifier.height(5.dp))
                TextField(
                    value = textItemId,
                    onValueChange = { textItemId = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(GrayB, RoundedCornerShape(10.dp))
                        .border(BorderStroke(1.5.dp, if (isFocused1) Teal else Color.Transparent), RoundedCornerShape(10.dp))
                        .onFocusChanged { isFocused1 = it.isFocused },
                    label = { Text("Item ID", color = Color.Gray) },
                    textStyle = LocalTextStyle.current.copy(fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    maxLines = 1
                )

                Spacer(Modifier.height(10.dp))
                Text("Item Name", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Medium)
                Spacer(Modifier.height(5.dp))
                TextField(
                    value = textItemName,
                    onValueChange = { textItemName = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(GrayB, RoundedCornerShape(10.dp))
                        .border(BorderStroke(1.5.dp, if (isFocused2) Teal else Color.Transparent), RoundedCornerShape(10.dp))
                        .onFocusChanged { isFocused2 = it.isFocused },
                    label = { Text("Item Name", color = Color.Gray) },
                    textStyle = LocalTextStyle.current.copy(fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    maxLines = 1
                )

                Spacer(Modifier.height(10.dp))
                Text("Opening Stock", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Medium)
                Spacer(Modifier.height(5.dp))
                TextField(
                    value = openingStock,
                    onValueChange = { if (it.all { char -> char.isDigit() }) openingStock = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(GrayB, RoundedCornerShape(10.dp))
                        .border(BorderStroke(1.5.dp, if (isFocused3) Teal else Color.Transparent), RoundedCornerShape(10.dp))
                        .onFocusChanged { isFocused3 = it.isFocused },
                    label = { Text("Opening Stock", color = Color.Gray) },
                    textStyle = LocalTextStyle.current.copy(fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    maxLines = 1
                )

                Spacer(Modifier.height(10.dp))
                Text("Price To Sell", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Medium)
                Spacer(Modifier.height(5.dp))
                Row(Modifier.fillMaxWidth().background(GrayB, RoundedCornerShape(10.dp))) {
                    TextField(
                        value = priceToSell1,
                        onValueChange = { priceToSell1 = it },
                        modifier = Modifier
                            .width(170.dp)
                            .padding(5.dp)
                            .background(Color.Gray, RoundedCornerShape(10.dp))
                            .border(BorderStroke(1.5.dp, if (isFocused4) Teal else Color.Transparent), RoundedCornerShape(10.dp))
                            .onFocusChanged { isFocused4 = it.isFocused },
                        label = { Text("Retail Price", color = Color.White) },
                        textStyle = LocalTextStyle.current.copy(fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        maxLines = 1
                    )
                    TextField(
                        value = priceToSell2,
                        onValueChange = { if (it.all { char -> char.isDigit() }) priceToSell2 = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .background(Color.Transparent, RoundedCornerShape(10.dp))
                            .border(BorderStroke(1.5.dp, if (isFocused5) Teal else Color.Gray), RoundedCornerShape(10.dp))
                            .onFocusChanged { isFocused5 = it.isFocused },
                        label = { Text("Enter a Number", color = Color.Gray) },
                        textStyle = LocalTextStyle.current.copy(fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        maxLines = 1
                    )
                }
            }

            Spacer(Modifier.padding(10.dp))
            Button(
                onClick = {
                    isLoading = true
                    val currentUser = FirebaseAuth.getInstance().currentUser
                    val uid = currentUser?.uid
                    if (uid != null) {
                        val db = Firebase.firestore
                        val storage = Firebase.storage
                        val itemId = textItemId
                        val itemData = hashMapOf(
                            "item_id" to textItemId,
                            "name" to textItemName,
                            "opening_stock" to openingStock.toInt(),
                            "retail_price" to priceToSell1,
                            "numeric_price" to priceToSell2.toIntOrNull(),
                            "created_at" to Timestamp.now()
                        )

                        if (selectedImageUri != null) {
                            val imageRef = storage.reference.child("item_images/${UUID.randomUUID()}.jpg")
                            imageRef.putFile(selectedImageUri!!)
                                .addOnSuccessListener {
                                    imageRef.downloadUrl.addOnSuccessListener { uri ->
                                        itemData["image_url"] = uri.toString()
                                        db.collection("users").document(uid).collection("items")
                                            .document(textItemId).set(itemData)
                                            .addOnSuccessListener {
                                                Toast.makeText(context, "Data berhasil disimpan", Toast.LENGTH_SHORT).show()
                                                isLoading = false
                                                onNextClick()
                                            }
                                            .addOnFailureListener {
                                                Toast.makeText(context, "Gagal menyimpan data", Toast.LENGTH_SHORT).show()
                                                isLoading = false
                                            }
                                    }
                                }
                                .addOnFailureListener {
                                    Toast.makeText(context, "Gagal mengunggah gambar", Toast.LENGTH_SHORT).show()
                                    isLoading = false
                                }
                        } else {
                            db.collection("users").document(uid).collection("items")
                                .document(textItemId).set(itemData)
                                .addOnSuccessListener {
                                    Toast.makeText(context, "Data berhasil disimpan", Toast.LENGTH_SHORT).show()
                                    textItemId = ""
                                    textItemName = ""
                                    openingStock = ""
                                    priceToSell1 = ""
                                    priceToSell2 = ""
                                    selectedImageUri = null
                                    isLoading = false
                                    onNextClick()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(context, "Gagal menyimpan data", Toast.LENGTH_SHORT).show()
                                    isLoading = false
                                }
                        }
                    } else {
                        isLoading = false
                    }
                },
                modifier = Modifier.fillMaxWidth().height(55.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Teal, contentColor = Color.White),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.White, strokeWidth = 3.dp, modifier = Modifier.size(24.dp))
                } else {
                    Text("Save", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}