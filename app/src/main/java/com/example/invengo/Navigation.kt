package com.example.invengo

import ItemData
import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.provider.FontsContractCompat.Columns
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.invengo.auth.SignInResult
import com.example.invengo.auth.SignInViewModel
import com.example.invengo.ui.theme.component.AddItem

@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Login_Page.route){
        composable(route = Screen.Login_Page.route){

        }
        composable("item_data") {
            ItemData(navController = navController) {
                navController.navigate("add_item")
            }
        }
        composable("add_item") {
            AddItem(navController = navController, onNextClick = {
                navController.popBackStack()
            })
        }
    }
}

