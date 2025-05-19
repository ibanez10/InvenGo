package com.example.invengo.ui.theme.component

import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
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
import com.example.invengo.ui.theme.Teal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InbounStock(
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
                    text = "Inbound Stock",
                    fontSize = 23.sp,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(Modifier.height(30.dp))
            Box(
                modifier = Modifier
            )
            Spacer(Modifier.height(15.dp))
            Column(Modifier.fillMaxWidth()) {
                var textItemId by remember { mutableStateOf("") }
                var textItemName by remember { mutableStateOf("") }
                var openingStock by remember { mutableStateOf("") }
                var Description by remember { mutableStateOf("") }
                var isFocused1 by remember { mutableStateOf(false) }
                var isFocused2 by remember { mutableStateOf(false) }
                var isFocused3 by remember { mutableStateOf(false) }
                var isFocused4 by remember { mutableStateOf(false) }
                Text(text = "Select Date", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight(500))
                Spacer(modifier.padding(5.dp))
                TextField(
                    value = textItemId,
                    onValueChange = { textItemId = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.DarkGray, shape = RoundedCornerShape(10.dp))
                        .border(
                            BorderStroke(1.5.dp, if (isFocused1) Teal else Color.Transparent),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .onFocusChanged { isFocused1 = it.isFocused },
                    label = { Text("Chose Date", color = Color.Gray) },
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
                            painter = painterResource(
                                id = R.drawable.date
                            ),
                            contentDescription = null,
                            modifier = Modifier.size(25.dp),
                            tint = Color.Unspecified
                        )
                    },
                    maxLines = 1
                )

                Spacer(modifier.height(10.dp))
                Text(text = "Product Name", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight(500))
                Spacer(modifier.padding(5.dp))
                TextField(
                    value = textItemName,
                    onValueChange = { textItemName = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.DarkGray, shape = RoundedCornerShape(10.dp))
                        .border(
                            BorderStroke(1.5.dp, if (isFocused2) Teal else Color.Transparent),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .onFocusChanged { isFocused2 = it.isFocused },
                    label = { Text("Select Item", color = Color.Gray) },
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

                Spacer(modifier.height(10.dp))
                Text(text = "Quantity", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight(500))
                Spacer(modifier.padding(5.dp))
                TextField(
                    value = openingStock,
                    onValueChange = { // Hanya izinkan angka
                        if (it.all { char -> char.isDigit() }) {
                            openingStock = it
                        }},
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.DarkGray, shape = RoundedCornerShape(10.dp))
                        .border(
                            BorderStroke(1.5.dp, if (isFocused3) Teal else Color.Transparent),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .onFocusChanged { isFocused3 = it.isFocused },
                    label = { Text("Ex.5", color = Color.Gray) },
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
                Spacer(modifier.height(10.dp))
                Text(text = "Description", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight(500))
                Spacer(modifier.padding(5.dp))
                TextField(
                    value = Description,
                    onValueChange = { Description = it},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .background(color = Color.DarkGray, shape = RoundedCornerShape(10.dp))
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
                Spacer(Modifier.padding(10.dp))
                Button(
                    onClick = {
                        onNextClick()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Teal)
                ) {
                    Spacer(Modifier.padding(vertical = 20.dp))
                    Text("Save", fontSize = 20.sp)
                }
        }
    }
}
}
