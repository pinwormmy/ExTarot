package com.example.extarot

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun CardDetailScreen(cardId: Int, deck: List<TarotCard>) {
    val card = deck.find { it.id == cardId }
    var isRevealed by remember { mutableStateOf(false) }
    var isOverlayVisible by remember { mutableStateOf(false) }

    if (card != null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(500.dp)
                    .clip(RoundedCornerShape(84.dp))
                    .clickable { if (!isRevealed) isRevealed = true },
            ) {
                if (!isRevealed) {
                    Image(
                        painter = painterResource(id = card.imageResource),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Text(
                        text = "Card ${card.id + 1}",  // 카드의 ID를 표시합니다.
                        style = MaterialTheme.typography.h4,
                        modifier = Modifier.align(Alignment.Center)
                            .clickable { isOverlayVisible = !isOverlayVisible }
                    )
                }
            }

            if (isOverlayVisible) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.7f)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = card.name,
                            style = MaterialTheme.typography.h5,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = card.description,
                            style = MaterialTheme.typography.body1,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
