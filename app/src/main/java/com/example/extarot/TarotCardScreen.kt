package com.example.extarot

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TarotCardScreen() {
    val isDarkTheme = isSystemInDarkTheme()

    MaterialTheme(
        colors = if (isDarkTheme) darkColors() else lightColors()
    ) {
        Surface(color = MaterialTheme.colors.background) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                TarotDeck(isDarkTheme)

                Spacer(modifier = Modifier.height(16.dp))

                ShuffleButton(isDarkTheme)
            }
        }
    }
}

@Composable
fun TarotDeck(isDarkTheme: Boolean) {
    Text(
        text = stringResource(R.string.tarot_deck),
        color = if (isDarkTheme) Color.White else MaterialTheme.colors.onBackground
    )
}

@Composable
fun ShuffleButton(isDarkTheme: Boolean) {
    val icon: ImageVector = Icons.Outlined.Shuffle
    val shuffleString: String = stringResource(R.string.shuffle)

    val backgroundColor = if (isDarkTheme) colorResource(id = R.color.purple_500) else Color.White
    val contentColor = if (isDarkTheme) Color.White else MaterialTheme.colors.onBackground

    Button(
        onClick = { /* TODO: 카드 섞기 애니메이션 코드 작성 */ },
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor),
        contentPadding = PaddingValues(16.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier.padding(end = 8.dp)
        )

        Text(
            text = shuffleString,
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
