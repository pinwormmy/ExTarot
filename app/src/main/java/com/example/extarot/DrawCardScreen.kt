package com.example.extarot

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
    val cornerRadius = 56.dp
    val cardOutAnimation = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopStart // 좌측 상단부터 시작
    ) {
        cards.forEachIndexed { index, card ->
            val offsetX = if (index < totalCardsPerColumn) 0.dp else cardSize + cardSpacing
            val offsetY = remember { mutableStateOf((-300).dp) }

            LaunchedEffect(key1 = index) {
                delay(50L * index)
                offsetY.value = ((index % totalCardsPerColumn) * 15).dp // 카드 간격 조정
            }

            val animatedOffsetY by animateDpAsState(
                targetValue = offsetY.value,
                animationSpec = spring(dampingRatio = 0.8f, stiffness = 300f)
            )

            val isSelected = selectedCard.value == card
            val animatedOffsetX by animateDpAsState(
                targetValue = if (isSelected && cardOutAnimation.value) (-1000).dp else offsetX
            )

            val rotationDegrees = if (isSelected) 30f else 0f
            val animatedRotation by animateFloatAsState(targetValue = rotationDegrees)

            Box(
                modifier = Modifier
                    .size(cardSize)
                    .offset(x = animatedOffsetX, y = animatedOffsetY)
                    .clip(RoundedCornerShape(cornerRadius)) // 이미지 모서리 곡선 적용
                    .clickable { selectedCard.value = if (selectedCard.value == card) null else card } // 카드 선택 구현
            ) {
                TarotCard(
                    modifier = Modifier
                        .size(cardSize)
                        .rotate(animatedRotation), // 기울어지는 효과 추가
                    card = card,
                    faceUp = false,
                    rotate = true
                )
            }
        }

        if (selectedCard.value != null) {
            Button(
                onClick = {
                    cardOutAnimation.value = true
                },
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Text("이 카드로 하시겠습니까?")
            }

            LaunchedEffect(cardOutAnimation.value) {
                if (cardOutAnimation.value) {
                    delay(1000L) // 카드 이동 애니메이션이 끝날 때까지 기다립니다.
                    val cardId = selectedCard.value?.id
                    if (cardId != null) {
                        val uri = "card_detail/$cardId"
                        Log.d("Navigation", "Navigating to $uri")
                        navController.navigate(uri)
                    } else {
                        Log.e("Navigation", "Card ID is null, cannot navigate.")
                    }
                }
            }
        }

    }
}




