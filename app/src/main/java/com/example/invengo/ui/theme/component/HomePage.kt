import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.invengo.R

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    navController: NavController,
    onItemDataClick: () -> Unit,
    onInboundStockClick: () -> Unit,
    onStockReleaseClick: () -> Unit
) {
    Box(Modifier.fillMaxSize().background(color = Color.Black)) {
        Image(
            painter = painterResource(id = R.drawable.frame),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp, horizontal = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Button(
                onClick = {},
                colors = ButtonDefaults.textButtonColors(contentColor = Color.Transparent),
                contentPadding = PaddingValues(1.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.menu),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp).clickable {

                    }
                )
            }

            Text(
                text = "Hallo, User!",
                fontSize = 23.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier.weight(1f).clickable {

                },
            )

            Image(
                painter = painterResource(id = R.drawable.avatar),
                contentDescription = null,
                modifier = Modifier.size(60.dp).padding(5.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(Modifier.padding(vertical = 37.dp))
            Box(Modifier){
            Image(
                painter = painterResource(id = R.drawable.group),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .size(230.dp)
            )
                Button( onClick = onItemDataClick, modifier = Modifier.align(Alignment.BottomEnd), colors = ButtonDefaults.buttonColors(Color.Transparent),contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)) {
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
                ){
                Image(
                    painter = painterResource(id = R.drawable.listt),
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                )
                    Spacer(modifier = Modifier.height(65.dp)) // jarak antara gambar dan teks
                    Text(text = "Item data", fontSize = 33.sp, fontWeight = FontWeight(700), color = Color.White)
                    Text(text = "Contains the data items you saved", fontSize = 16.sp, fontWeight = FontWeight(400), color = Color.White)
            }}
            Box () {
            Image(
                painter = painterResource(id = R.drawable.group3),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .size(230.dp)
            )
                Button( onClick = onInboundStockClick, modifier = Modifier.align(Alignment.BottomEnd), colors = ButtonDefaults.buttonColors(Color.Transparent), contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)) {
                    Image(
                        painter = painterResource(R.drawable.arrowclone),
                        contentDescription = null,
                        modifier = Modifier.size(58.dp),
                    )
                }
                Column(Modifier.fillMaxWidth().padding(horizontal = 33.dp, vertical = 10.dp)){
                    Image(
                        painter = painterResource(id = R.drawable.boxgreen),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                    )
                    Spacer(modifier = Modifier.height(75.dp)) // jarak antara gambar dan teks
                    Text(text = "Inbound Stock", fontSize = 33.sp, fontWeight = FontWeight(700), color = Color.White)
                    Text(text = "Enter data on incoming goods", fontSize = 16.sp, fontWeight = FontWeight(400), color = Color.White)
                }
            }
            Box() {
            Image(
                painter = painterResource(id = R.drawable.group2),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .size(230.dp)
            )
                Button(onClick = onStockReleaseClick, modifier = Modifier.align(Alignment.BottomEnd), colors = ButtonDefaults.buttonColors(Color.Transparent), contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)) {
                    Image(
                        painter = painterResource(R.drawable.arrowclone),
                        contentDescription = null,
                        modifier = Modifier.size(58.dp)
                    )
                }
                Column(Modifier.fillMaxWidth().padding(horizontal = 35.dp, vertical = 20.dp)){
                Image(
                    painter = painterResource(id = R.drawable.boxred),
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                )
                    Spacer(modifier = Modifier.height(75.dp)) // jarak antara gambar dan teks
                    Text(text = "Stock release", fontSize = 33.sp, fontWeight = FontWeight(700), color = Color.White)
                    Text(text = "Enter outgoing goods data", fontSize = 16.sp, fontWeight = FontWeight(400), color = Color.White)
                }
            }
        }
    }
}
