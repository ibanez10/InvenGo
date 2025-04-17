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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.invengo.R

@Composable
fun HomePage_First(modifier: Modifier = Modifier, navController: NavController, onNextClick: () -> Unit) {
    Box(Modifier.fillMaxSize().background(color = Color.Black)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp, horizontal = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {},
                colors = ButtonDefaults.textButtonColors(contentColor = Color.Transparent),
                contentPadding = PaddingValues(1.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.menu),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
            }

            Text(
                text = "Hallo, User!",
                fontSize = 23.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier.weight(1f)
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
            Spacer(Modifier.padding(vertical = 15.dp))
            Box(Modifier){
            Image(
                painter = painterResource(id = R.drawable.group),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .size(230.dp)
            )
                Box(Modifier.padding(horizontal = 25.dp, vertical = 20.dp).fillMaxWidth()){
                Image(
                    painter = painterResource(id = R.drawable.listt),
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                        .align(alignment = Alignment.TopStart)
                )
                }
            }
            Box () {
            Image(
                painter = painterResource(id = R.drawable.group3),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .size(230.dp)
            )
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
                Box(Modifier.fillMaxWidth().padding(horizontal = 35.dp, vertical = 20.dp)){
                Image(
                    painter = painterResource(id = R.drawable.stockrelease),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                )
                }
            }
        }
    }
}
