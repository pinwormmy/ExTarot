package com.example.extarot

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
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
    val selectedCard = remember { mutableStateOf<TarotCard?>(null) }
    val cornerRadius = 16.dp

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopStart // 좌측 상단부터 시작
    ) {
        cards.forEachIndexed { index, card ->
            val offsetX = if (index < totalCardsPerColumn) 0.dp else cardSize + cardSpacing
            val offsetY = remember { mutableStateOf(-300.dp) }

            LaunchedEffect(key1 = index) {
                delay(50L * index)
                offsetY.value = ((index % totalCardsPerColumn) * 15).dp // 카드 간격 조정
            }

            val animatedOffsetY by animateDpAsState(
                targetValue = offsetY.value,
                animationSpec = spring(dampingRatio = 0.8f, stiffness = 300f)
            )

            val isSelected = selectedCard.value == card
            val rotationDegrees = if (isSelected) 30f else 0f
            val animatedRotation by animateFloatAsState(targetValue = rotationDegrees)

            TarotCard(
                modifier = Modifier
                    .size(cardSize)
                    .offset(x = offsetX, y = animatedOffsetY)
                    .rotate(animatedRotation) // 기본 상태에서 가로로 눕히고, 선택 시 30도 회전
                    .clip(RoundedCornerShape(cornerRadius)) // 이미지 모서리 곡선 적용
                    .clickable { selectedCard.value = if (isSelected) null else card },
                card = card,
                faceUp = false,
                rotate = true // 기본 상태에서 가로로 눕히기 위해 rotate 속성을 true로 설정
            )
        }
    }
}
