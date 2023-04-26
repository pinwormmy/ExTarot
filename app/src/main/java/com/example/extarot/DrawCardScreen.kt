package com.example.extarot

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun DrawCardsScreen(navController: NavController) {
    val cards = remember { shuffleCards(createTarotDeck()) }
    val columns = 2
    val rows = cards.size / columns
    val cardSize = 150.dp

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        for (row in 0 until rows) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (column in 0 until columns) {
                    val cardIndex = row * columns + column
                    val card = cards[cardIndex]
                    val offsetX = remember { mutableStateOf(300.dp) }
                    val offsetY = remember { mutableStateOf(-300.dp) }

                    LaunchedEffect(key1 = cardIndex) {
                        delay(50L * cardIndex)
                        offsetX.value = 0.dp
                        offsetY.value = 0.dp
                    }

                    val animatedOffsetX by animateDpAsState(
                        targetValue = offsetX.value,
                        animationSpec = spring(dampingRatio = 0.8f, stiffness = 300f)
                    )
                    val animatedOffsetY by animateDpAsState(
                        targetValue = offsetY.value,
                        animationSpec = spring(dampingRatio = 0.8f, stiffness = 300f)
                    )

                    TarotCard(
                        modifier = Modifier
                            .size(cardSize)
                            .offset(x = animatedOffsetX, y = animatedOffsetY),
                        card = card,
                        faceUp = false
                    )
                }
            }
        }
    }
}