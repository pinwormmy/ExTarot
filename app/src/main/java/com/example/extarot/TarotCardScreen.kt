package com.example.extarot

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.darkColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Shuffle
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.delay

data class CardPosition(val rotation: Float, val translationX: Dp, val translationY: Dp)

@Composable
fun TarotCardScreen() {
    val isDarkTheme = isSystemInDarkTheme()
    val shuffle = remember { mutableStateOf(false) }
    val isShuffling = remember { mutableStateOf(false) }

    MaterialTheme(
        colors = if (isDarkTheme) darkColors() else lightColors()
    ) {
        Surface(color = MaterialTheme.colors.background) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                TarotDeck(isDarkTheme, 78, 200.dp, 2.dp, shuffle, isShuffling)

                Spacer(modifier = Modifier.height(16.dp))

                ShuffleButton(isDarkTheme, isShuffling) {
                    shuffle.value = true
                    isShuffling.value = true
                }
            }
        }
    }
}

@Composable
fun TarotDeck(
    isDarkTheme: Boolean,
    maxCards: Int,
    cardSize: Dp,
    cardGap: Dp,
    shuffle: MutableState<Boolean>,
    isShuffling: MutableState<Boolean>
) {
    val cardBackImage = painterResource(R.drawable.card_back)
    val cornerRadius = 64.dp
    val translationStateList = remember { MutableList(maxCards) { mutableStateOf(0.dp) } }
    val rotationStateList = remember { MutableList(maxCards) { mutableStateOf(getRandomAngle()) } }

    val duration = 300
    val delayBetweenIterations = 500

    // Calculate split index
    val splitIndex = (maxCards * 0.5).toInt()

    fun shuffleDeck() {
        val targetTranslation = 600.dp

        (0 until splitIndex).forEach { index ->
            translationStateList[index].value = 0.dp
        }

        (splitIndex until maxCards).forEach { index ->
            translationStateList[index].value = targetTranslation
        }
    }

    LaunchedEffect(shuffle.value) {
        if (shuffle.value) {
            // 전체 동작을 3번 반복
            repeat(3) {
                shuffleDeck()
                delay(duration.toLong())

                // 원래 위치로 되돌리는 코드 수정 (한장씩 연속해서 되돌리기)
                for (index in splitIndex until maxCards) {
                    translationStateList[index].value = 0.dp
                    delay(10) // 카드 한 장씩 이동하는데 10ms 간격
                }

                // 카드 뭉치의 앞쪽으로 이동하는 코드 추가
                val movedCardIndices = (splitIndex until maxCards).toList()
                movedCardIndices.forEach { index ->
                    rotationStateList.removeAt(index)
                    rotationStateList.add(0, mutableStateOf(getRandomAngle()))
                    translationStateList.removeAt(index)
                    translationStateList.add(0, mutableStateOf(0.dp))
                }

                // 각 반복 사이에 텀을 둡니다.
                if (it < 2) {
                    delay(delayBetweenIterations.toLong())
                }
            }

            shuffle.value = false
            isShuffling.value = false
        }
    }



    Box(
        modifier = Modifier.size(cardSize),
        contentAlignment = Alignment.Center
    ) {
        (0 until maxCards).reversed().forEach { index ->
            val translationX by animateFloatAsState(
                targetValue = translationStateList[index].value.value,
                animationSpec = tween(duration)
            )

            Image(
                painter = cardBackImage,
                contentDescription = stringResource(R.string.tarot_deck),
                modifier = Modifier
                    .graphicsLayer(
                        rotationZ = rotationStateList[index].value,
                        translationX = translationX
                    )
                    // zIndex를 추가하여 카드 순서를 제어합니다.
                    .zIndex((maxCards - index).toFloat())
                    .size(cardSize)
                    .clip(RoundedCornerShape(cornerRadius))
            )
        }
    }



}

@Composable
fun ShuffleButton(isDarkTheme: Boolean, isShuffling: MutableState<Boolean>, onClick: () -> Unit) {
    val icon: ImageVector = Icons.Outlined.Shuffle
    val shuffleString: String = stringResource(R.string.shuffle)
    val shufflingString: String = stringResource(R.string.shuffling)

    val backgroundColor = if (isDarkTheme) colorResource(id = R.color.purple_500) else Color.White
    val contentColor = if (isDarkTheme) Color.White else MaterialTheme.colors.onBackground

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor),
        contentPadding = PaddingValues(16.dp),
        enabled = !isShuffling.value
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier.padding(end = 8.dp)
        )

        Text(
            text = if (isShuffling.value) shufflingString else shuffleString,
            color = contentColor
        )
    }
}

private fun getRandomAngle(): Float {
    return (-5..5).random().toFloat()
}

