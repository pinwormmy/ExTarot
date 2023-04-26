package com.example.extarot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isDarkTheme = isSystemInDarkTheme()
            val colors = if (isDarkTheme) {
                darkColors()
            } else {
                lightColors()
            }

            MaterialTheme(colors = colors) {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "main_screen") {
                        composable("main_screen") { MainScreen(navController) }
                        composable("tarot_card_screen") { TarotCardScreen(navController) }
                        composable("draw_cards_screen") { DrawCardsScreen(navController) }
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "ExTarot",
                fontSize = 50.sp,
                color = MaterialTheme.colors.onBackground
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = { navController.navigate("tarot_card_screen") }) {
                Text(
                    text = "타로 카드 뽑기",
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}
