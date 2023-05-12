package com.example.extarot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

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

                    val deck = remember { mutableStateOf(emptyList<TarotCard>()) }
                    LaunchedEffect(Unit) {
                        deck.value = loadTarotDeck(this@MainActivity)
                    }

                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "main_screen") {
                        composable("main_screen") { MainScreen(navController) }
                        composable("tarot_card_screen") { TarotCardScreen(navController) }
                        composable("draw_cards_screen") { DrawCardsScreen(navController) }
                        composable("card_detail/{cardId}") { backStackEntry ->
                            val cardId = backStackEntry.arguments?.getString("cardId")?.toIntOrNull()
                            if (cardId != null) {
                                CardDetailScreen(cardId, deck.value)
                            } else {
                                // cardId가 잘못되었거나 카드를 찾을 수 없을 때 처리를 여기에 추가합니다.
                            }
                        }
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
