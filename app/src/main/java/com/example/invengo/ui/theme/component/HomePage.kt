import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.invengo.R
import com.example.invengo.ui.theme.Teal
import com.example.invengo.ui.theme.component.profile

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    navController: NavController,
    onItemDataClick: () -> Unit,
    onInboundStockClick: () -> Unit,
    onStockReleaseClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    // State untuk mengatur tampilan sidebar
    var showSidebar by remember { mutableStateOf(false) }

    Box(Modifier
        .fillMaxSize()
        .background(color = Color.Black)) {

        // Background image
        Image(
            painter = painterResource(id = R.drawable.frame),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Top App Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp, horizontal = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { showSidebar = !showSidebar }, // toggle sidebar
                colors = ButtonDefaults.textButtonColors(contentColor = Color.Transparent),
                contentPadding = PaddingValues(1.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.menu),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp)
                )
            }

            Text(
                text = "Hallo, User!",
                fontSize = 23.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier.weight(1f)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onProfileClick, // toggle sidebar
                    colors = ButtonDefaults.textButtonColors(contentColor = Color.Transparent),
                    contentPadding = PaddingValues(1.dp)
                ) { Image(
                    painter = painterResource(id = R.drawable.avatar),
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                        .padding(5.dp)
                )
                }

            }

        }

        // Sidebar
        if (showSidebar) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(280.dp)
                    .background(
                        shape = RoundedCornerShape(topEnd = 120.dp),
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF2C2C2C), // warna bawah
                                Color.Black, // warna atas
                            )
                        ),
                    )
                    .padding(vertical = 55.dp, horizontal = 15.dp)
                    .align(Alignment.TopStart)
                    .zIndex(1f)
            ) {
                Column {
                    Image(
                        painter = painterResource(R.drawable.logfrm),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp)
                        )
                Spacer(Modifier.height(50.dp))
                Row(Modifier
                    .fillMaxWidth()
                    .background(
                        shape = RoundedCornerShape(10.dp),
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color.DarkGray,
                                Teal,
                                Color.DarkGray,
                            ),
                        )
                    )
                    .padding(10.dp)
                ){
                    Image(
                        painter = painterResource(R.drawable.avatar),
                        contentDescription = null,
                        modifier = Modifier.size(50.dp)
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    Column {
                    Text(text = "Nama", color = Color.White, fontSize = 17.sp, fontWeight = FontWeight(600))
                    Text(text = "Email@gmail.com", color = Color.White, fontSize = 12.sp)
                    }
                }
                    Spacer(Modifier.height(30.dp))
                Row(Modifier.padding(horizontal = 10.dp)) {
                    Image(
                        painter = painterResource(R.drawable.dashboard),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp)
                    )
                    Spacer(Modifier.width(20.dp))
                    Text(text = "Dashboard", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight(600),)
                }
                    Spacer(Modifier.height(30.dp))

                    Row(Modifier.padding(horizontal = 10.dp)) {
                        Image(
                            painter = painterResource(R.drawable.stocklst),
                            contentDescription = null,
                            modifier = Modifier.size(25.dp)
                        )
                        Spacer(Modifier.width(20.dp))
                        Text(text = "Stock Data", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight(600),)
                    }

                    Spacer(Modifier.height(30.dp))

                    Row(Modifier.padding(horizontal = 10.dp)) {
                        Image(
                            painter = painterResource(R.drawable.manage),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp)
                        )
                        Spacer(Modifier.width(20.dp))
                        Text(text = "Manage Stock", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight(600),)
                    }

                    Spacer(Modifier.height(30.dp))

                    Row(Modifier.padding(horizontal = 10.dp)) {
                        Image(
                            painter = painterResource(R.drawable.store),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp)
                        )
                        Spacer(Modifier.width(20.dp))
                        Text(text = "Manage Store", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight(600),)
                    }
                }
            }
        }

        // Main content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(Modifier.padding(vertical = 37.dp))

            // Box 1 - Item Data
            Box {
                Image(
                    painter = painterResource(id = R.drawable.group),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .size(230.dp)
                )
                Button(
                    onClick = onItemDataClick,
                    modifier = Modifier.align(Alignment.BottomEnd),
                    colors = ButtonDefaults.buttonColors(Color.Transparent),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.arrowclone),
                        contentDescription = null,
                        modifier = Modifier.size(58.dp),
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(horizontal = 33.dp, vertical = 20.dp)
                        .fillMaxWidth(),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.listt),
                        contentDescription = null,
                        modifier = Modifier.size(60.dp)
                    )
                    Spacer(modifier = Modifier.height(65.dp))
                    Text("Item data", fontSize = 33.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Text("Contains the data items you saved", fontSize = 16.sp, color = Color.White)
                }
            }

            // Box 2 - Inbound Stock
            Box {
                Image(
                    painter = painterResource(id = R.drawable.group3),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .size(230.dp)
                )
                Button(
                    onClick = onInboundStockClick,
                    modifier = Modifier.align(Alignment.BottomEnd),
                    colors = ButtonDefaults.buttonColors(Color.Transparent),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.arrowclone),
                        contentDescription = null,
                        modifier = Modifier.size(58.dp),
                    )
                }
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 33.dp, vertical = 10.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.boxgreen),
                        contentDescription = null,
                        modifier = Modifier.size(50.dp)
                    )
                    Spacer(modifier = Modifier.height(75.dp))
                    Text("Inbound Stock", fontSize = 33.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Text("Enter data on incoming goods", fontSize = 16.sp, color = Color.White)
                }
            }

            // Box 3 - Stock Release
            Box {
                Image(
                    painter = painterResource(id = R.drawable.group2),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .size(230.dp)
                )
                Button(
                    onClick = onStockReleaseClick,
                    modifier = Modifier.align(Alignment.BottomEnd),
                    colors = ButtonDefaults.buttonColors(Color.Transparent),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.arrowclone),
                        contentDescription = null,
                        modifier = Modifier.size(58.dp)
                    )
                }
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 35.dp, vertical = 20.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.boxred),
                        contentDescription = null,
                        modifier = Modifier.size(50.dp)
                    )
                    Spacer(modifier = Modifier.height(75.dp))
                    Text("Stock release", fontSize = 33.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Text("Enter outgoing goods data", fontSize = 16.sp, color = Color.White)
                }
            }
        }
    }
}
