package com.example.extarot

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

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
    val rotationStateList = remember { List(maxCards) { mutableStateOf(getRandomAngle()) } }
    val translationStateList = remember { List(maxCards) { mutableStateOf(getRandomTranslation()) } }

    val duration = 3000

    fun shuffleDeck() {
        rotationStateList.indices.forEach { index ->
            rotationStateList[index].value = getRandomAngle()
            translationStateList[index].value = getRandomTranslation()
        }
    }

    LaunchedEffect(shuffle.value) {
        if (shuffle.value) {
            shuffleDeck()
            delay(duration.toLong())
            shuffle.value = false
            isShuffling.value = false
        }
    }

    Box(
        modifier = Modifier.size(cardSize),
        contentAlignment = Alignment.Center
    ) {
        repeat(maxCards) { index ->
            val targetAngle = rotationStateList[index].value
            val angle by animateFloatAsState(targetValue = targetAngle, animationSpec = tween(duration))

            val targetTranslation = translationStateList[index].value
            val translationX by animateFloatAsState(targetValue = targetTranslation.first.value, animationSpec = tween(duration))
            val translationY by animateFloatAsState(targetValue = targetTranslation.second.value, animationSpec = tween(duration))

            Image(
                painter = cardBackImage,
                contentDescription = stringResource(R.string.tarot_deck),
                modifier = Modifier
                    .graphicsLayer(
                        translationX = translationX,
                        translationY = translationY,
                        rotationZ = angle
                    )
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


@Preview(showBackground = true)
@Composable
fun LightPreview() {
    MaterialTheme {
        TarotCardScreen()
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DarkPreview() {
    MaterialTheme(darkColors()) {
        TarotCardScreen()
    }
}

private fun getRandomAngle(): Float {
    return (-5..5).random().toFloat()
}

private fun getRandomTranslation(): Pair<Dp, Dp> {
    val x = (-10..10).random().dp
    val y = (-10..10).random().dp
    return Pair(x, y)
}
