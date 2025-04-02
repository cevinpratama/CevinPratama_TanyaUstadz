package com.example.tanya_ustadz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tanya_ustadz.ui.theme.Tanya_UstadzTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val backgroundColor = if (isSystemInDarkTheme()) {
                Color.Black
            } else {
                Color.White
            }

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
                        val navController = rememberNavController()

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
                                    composable("jadwal") { PrayerTimeScreen() }
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

