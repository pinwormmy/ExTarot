package com.example.extarot

import android.os.Bundle
import androidx.activity.ComponentActivity

import com.example.extarot.TarotCardScreen

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
    val navController = rememberNavController()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "ExTarot",
                fontSize = 50.sp,
                color = if (isDarkTheme) Color.White else Color.Black
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = { navController.navigate("tarot_card_screen") }) {
                Text(
                    text = "타로 카드 뽑기",
                    color = if (isDarkTheme) Color.White else Color.Black
                )
            }
        }

        NavHost(navController = navController, startDestination = "main_screen") {
            composable("main_screen") { /* 메인 스크린을 나타내는 Composable 함수를 작성하세요. */ }
            composable("tarot_card_screen") { TarotCardScreen() }
        }
    }
}

