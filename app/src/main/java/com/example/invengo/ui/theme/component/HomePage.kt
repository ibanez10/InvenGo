import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import coil.compose.AsyncImage
import com.example.invengo.R
import com.example.invengo.ui.theme.Teal

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    navController: NavController,
    // <-- user data dari Google login
    onItemDataClick: () -> Unit,
    onInboundStockClick: () -> Unit,
    onStockReleaseClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    var showSidebar by remember { mutableStateOf(false) }

    Box(Modifier.fillMaxSize()) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.frame),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Sidebar & backdrop
        Box(modifier = Modifier.fillMaxSize().zIndex(20f)) {
            if (showSidebar) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.4f))
                        .clickable { showSidebar = false }
                )
            }

            AnimatedVisibility(
                visible = showSidebar,
                enter = slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(200)),
                exit = slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(200))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(280.dp)
                        .background(
                            shape = RoundedCornerShape(topEnd = 120.dp),
                            brush = Brush.verticalGradient(
                                colors = listOf(Color(0xFF2C2C2C), Color.Black)
                            )
                        )
                        .padding(vertical = 55.dp, horizontal = 15.dp)
                ) {
                    Column {
                        Image(
                            painter = painterResource(R.drawable.logfrm),
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(Modifier.height(50.dp))
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .background(
                                    shape = RoundedCornerShape(10.dp),
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(Color.DarkGray, Teal, Color.DarkGray)
                                    )
                                )
                                .padding(10.dp)
                        ) {
                            if (userData?.ProfilePictureUrl != null) {
                                AsyncImage(
                                    model = userData.ProfilePictureUrl,
                                    contentDescription = null,
                                    modifier = Modifier.size(50.dp)
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
                                Text(
                                    userData?.username ?: "Nama",
                                    color = Color.White,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    userData?.email ?: "Email@gmail.com",
                                    color = Color.White,
                                    fontSize = 12.sp
                                )
                            }
                        }

                        Spacer(Modifier.height(30.dp))
                        Column(Modifier.padding(horizontal = 10.dp)) {
                            listOf(
                                R.drawable.dashboard to "Dashboard",
                                R.drawable.stocklst to "Stock Data",
                                R.drawable.manage to "Manage Stock",
                                R.drawable.store to "Manage Store"
                            ).forEach { (icon, title) ->
                                Row(Modifier.padding(vertical = 10.dp)) {
                                    Image(
                                        painter = painterResource(icon),
                                        contentDescription = null,
                                        modifier = Modifier.size(25.dp)
                                    )
                                    Spacer(Modifier.width(20.dp))
                                    Text(
                                        title,
                                        color = Color.White,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Top App Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp, horizontal = 5.dp)
                .zIndex(11f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { showSidebar = !showSidebar },
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
                text = "Hallo, ${userData?.username ?: "User"}!",
                fontSize = 23.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier.weight(1f)
            )

            Button(
                onClick = onProfileClick,
                colors = ButtonDefaults.textButtonColors(contentColor = Color.Transparent),
                contentPadding = PaddingValues(1.dp)
            ) {
                if (userData?.ProfilePictureUrl != null) {
                    AsyncImage(
                        model = userData.ProfilePictureUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .size(60.dp)
                            .padding(5.dp)
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.avatar),
                        contentDescription = null,
                        modifier = Modifier
                            .size(60.dp)
                            .padding(5.dp)
                    )
                }
            }
        }

        // Main content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .zIndex(1f),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(Modifier.padding(vertical = 16.dp))

            listOf(
                Triple(R.drawable.group, R.drawable.listt, Triple("Item data", "Contains the data items you saved", onItemDataClick)),
                Triple(R.drawable.group3, R.drawable.boxgreen, Triple("Inbound Stock", "Enter data on incoming goods", onInboundStockClick)),
                Triple(R.drawable.group2, R.drawable.boxred, Triple("Stock release", "Enter outgoing goods data", onStockReleaseClick))
            ).forEach { (bgImage, iconImage, textData) ->
                val (title, subtitle, onClick) = textData
                Box(
                    Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .size(230.dp)
                        .clickable { onClick() }
                ) {
                    Image(
                        painter = painterResource(id = bgImage),
                        contentDescription = null,
                        modifier = Modifier.matchParentSize()
                    )
                    Button(
                        onClick = onClick,
                        modifier = Modifier.align(Alignment.BottomEnd),
                        colors = ButtonDefaults.buttonColors(Color.Transparent),
                        contentPadding = PaddingValues(horizontal = 26.dp, vertical = 10.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.arrowclone),
                            contentDescription = null,
                            modifier = Modifier.size(58.dp)
                        )
                    }
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 33.dp, vertical = 20.dp)
                            .fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(id = iconImage),
                            contentDescription = null,
                            modifier = Modifier.size(50.dp)
                        )
                        Spacer(modifier = Modifier.height(75.dp))
                        Text(title, fontSize = 33.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Text(subtitle, fontSize = 16.sp, color = Color.White)
                    }
                }
            }
        }
    }
}
