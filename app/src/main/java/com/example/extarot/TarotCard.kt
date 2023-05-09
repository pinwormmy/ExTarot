package com.example.extarot

import androidx.annotation.DrawableRes

data class TarotCard(
    val id: Int,
    val name: String,
    @DrawableRes val image: Int,
    val description: String,
)
