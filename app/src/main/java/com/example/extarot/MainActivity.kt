package com.example.extarot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    val isDarkTheme = isSystemInDarkTheme()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "ExTarot",
            fontSize = 50.sp,
            color = if (isDarkTheme) Color.White else Color.Black
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            // 카드 뽑기 화면으로 이동하는 코드를 여기에 작성하세요.
            // 테스트 냥냥
        }) {
            Text(
                text = "타로 카드 뽑기",
                color = if (isDarkTheme) Color.White else Color.Black
            )
        }
    }
}

