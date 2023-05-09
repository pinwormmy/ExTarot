package com.example.extarot

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavController

@Composable
fun FullScreenCard(navController: NavController, card: TarotCard) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            bitmap = card.image.asImageBitmap(),
            contentDescription = card.name,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )
    }
}
