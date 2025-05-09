package com.example.tanya_ustadz

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tanya_ustadz.navigation.Screen
import com.example.tanya_ustadz.ui.theme.Tanya_UstadzTheme
import com.example.tanya_ustadz.util.ViewModelFactory

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val backgroundColor = if (isSystemInDarkTheme()) {
                Color.Black
            } else {
                Color.White
            }
            val navController = rememberNavController()


            Tanya_UstadzTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(backgroundColor)
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                            ) {
                                NavHost(
                                    navController = navController,
                                    startDestination = "jadwal"
                                ) {
                                    composable("jadwal") { PrayerTimeScreen(onNavigateToAbout = { navController.navigate("about") },
                                        onLogout = {
                                            navController.navigate("login") {
                                                popUpTo("jadwal") { inclusive = true }
                                            }
                                        }) }
                                    composable("cari") { CariScreen() }
                                    composable("about") { AboutScreen(onBack = { navController.popBackStack() }) }
                                    composable("tambah") { TambahScreen() }
                                    composable("akun") { AkunScreen() }
                                    composable("tambah_doa") { TambahDoaScreen(navController) }
                                    composable(Screen.FormBaru.route) {
                                        DetailScreen(navController)
                                    }
                                    composable(
                                        route = "detailScreen/{id}",
                                        arguments = listOf(navArgument("id") { type = NavType.LongType })
                                    ) { backStackEntry ->
                                        val id = backStackEntry.arguments?.getLong("id")
                                        DetailScreen(id = id, navController = navController)
                                    }
                                    composable("recycle_bin") {
                                        val context = LocalContext.current
                                        val factory = ViewModelFactory(context)
                                        val mainViewModel: MainViewModel = viewModel(factory = factory)

                                        RecycleBinScreen(viewModel = mainViewModel, navController = navController)
                                    }

                                }
                            }
                            BottomBawah(navController)
                        }
                    }
                }
            }
            }
        }
    }

