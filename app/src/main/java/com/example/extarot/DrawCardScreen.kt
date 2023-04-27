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
    val cardSize = 185.dp
    val cardSpacing = 15.dp
    val totalCardsPerColumn = cards.size / columns

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopStart // 좌측 상단부터 시작
    ) {
        cards.forEachIndexed { index, card ->
            val offsetX = if (index < totalCardsPerColumn) 0.dp else cardSize + cardSpacing
            val initialOffsetX = remember { mutableStateOf(300.dp) }
            val offsetY = remember { mutableStateOf(-300.dp) }

            LaunchedEffect(key1 = index) {
                delay(50L * index)
                offsetY.value = ((index % totalCardsPerColumn) * 15).dp // 카드 간격 조정
                initialOffsetX.value = 0.dp // 애니메이션 시작 위치 수정
            }

            val animatedOffsetY by animateDpAsState(
                targetValue = offsetY.value,
                animationSpec = spring(dampingRatio = 0.8f, stiffness = 300f)
            )

            TarotCard(
                modifier = Modifier
                    .size(cardSize)
                    .offset(x = offsetX + initialOffsetX.value, y = animatedOffsetY),
                card = card,
                faceUp = false,
                rotate = true // 가로로 돌린 상태를 유지
            )
        }
    }
}
