package com.example.extarot

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.extarot.DrawCardsScreen
import com.example.extarot.FullScreenCard
import com.example.extarot.createTarotDeck
import com.example.extarot.shuffleCards

@Composable
fun NavGraph(startDestination: String = "drawCardsScreen") {
    val navController = rememberNavController()
    val cards = shuffleCards(createTarotDeck())

    NavHost(navController = navController, startDestination = startDestination) {
        composable("drawCardsScreen") {
            DrawCardsScreen(navController = navController)
        }
        composable("fullScreenCard/{cardId}") { backStackEntry ->
            val cardId = backStackEntry.arguments?.getInt("cardId") ?: -1
            val card = cards.firstOrNull { it.id == cardId }

            if (card != null) {
                FullScreenCard(card = card) // 이제 'card' 파라미터를 전달하고 있습니다.
            }
        }
    }
}




