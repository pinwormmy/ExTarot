package com.example.extarot

import android.content.Context
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Serializable
data class TarotCard(
    val id: Int,
    val imageResource: Int,
    val name: String,
    val description: String,
    val isRevealed: Boolean = false
)

suspend fun loadTarotDeck(context: Context): List<TarotCard> {
    val json = context.assets.open("tarot_cards.json").bufferedReader().use { it.readText() }
    return Json.decodeFromString<List<TarotCard>>(json)
}

fun createTarotDeck(): List<TarotCard> {
    return List(78) { index ->
        TarotCard(id = index, imageResource = R.drawable.card_back, name = "", description = "")
    }
}

