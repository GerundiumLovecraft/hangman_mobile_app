package com.example.hangmanmobile

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hangmanmobile.ui.theme.CyanGlow
import com.example.hangmanmobile.ui.theme.DeepSpace
import com.example.hangmanmobile.ui.theme.HangmanMobileTheme
import com.example.hangmanmobile.ui.theme.HotPink
import com.example.hangmanmobile.ui.theme.OrbitronFamily
import com.example.hangmanmobile.ui.theme.ShareTechFamily
import com.example.hangmanmobile.ui.theme.TextDim

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HangmanMobileTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = DeepSpace,
                    contentWindowInsets = WindowInsets(0)
                ) { innerPadding ->
                    App(
                        modifier = Modifier
                            .padding(innerPadding)
                            .background(DeepSpace)
                    )
                }
            }
        }
    }
}

@Composable
fun App(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        val navController = rememberNavController()
        val gameView: GameViewModel = viewModel()

        NavHost(navController = navController, startDestination = "home") {
            composable(route = "home") {
                HomeScreen(
                    gameView = gameView,
                    onClickPlay = {navController.navigate("game")}
                )
            }

            composable(route = "game") {
                GameScreen(
                    gameView = gameView,
                    endGame = {navController.navigate("endGame")}
                )
            }
            composable(route="endGame") {
                EndGameScreen(
                    gameView = gameView,
                    newGame = {navController.navigate("game")},
                    backHome = {navController.navigate("home")},
                    modifier = modifier
                )
            }
        }
    }
}

@Composable
fun HomeScreen(
    gameView: GameViewModel,
    onClickPlay: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            HomeScreenHangmanAnimation(modifier = Modifier.offset(x = 12.dp))
        }
        Spacer(modifier = modifier.height(12.dp))
        Text(
            text = "HANGMAN",
            fontFamily = OrbitronFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 38.sp,
            letterSpacing = 8.sp,
            color = CyanGlow
        )
        HorizontalDivider(
            modifier = Modifier
                .width(120.dp)
                .padding(vertical = 20.dp),
            thickness = 1.dp,
            color = CyanGlow.copy(alpha = 0.2f)
        )
        OutlinedButton(
            onClick = {
                gameView.startGame()
                onClickPlay()
                      },
            border = BorderStroke(1.5.dp, CyanGlow),
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = CyanGlow,
                containerColor = Color.Transparent
            ),
            modifier = Modifier.width(200.dp)
        ) {
            Text(
                text = "▶ PLAY",
                fontFamily = OrbitronFamily,
                fontSize = 13.sp,
                letterSpacing = 3.sp
            )
        }
        Spacer(modifier = modifier.height(20.dp))
        OutlinedButton(
            onClick = { (context as Activity).finish() },
            border = BorderStroke(1.5.dp, HotPink),
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = HotPink,
                containerColor = Color.Transparent
            ),
            modifier = Modifier.width(200.dp)
        ) {
            Text(
                text = "▶ EXIT",
                fontFamily = OrbitronFamily,
                fontSize = 13.sp,
                letterSpacing = 3.sp
            )
        }
        Text(
            text = "// error no keyboard //",
            fontFamily = ShareTechFamily,
            fontSize = 10.sp,
            color = TextDim,
            letterSpacing = 2.sp
        )
    }
}


@Composable
fun GameScreen(
    gameView: GameViewModel,
    endGame: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            GameScreenHangmanAnimation(
                failureCount = gameView.failures
            )
        }
        MaskedWordContainer(
            gameView.displayWord,
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        VirtualKeyboard(
            gameView.guessedLetters,
            {char -> gameView.turn(char) },
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }

    // check if the game is won or lost
    LaunchedEffect(gameView.gameWon, gameView.failures) {
        if (gameView.gameWon || gameView.failures > 5) {
            endGame()
        }
    }
}


@Preview(showBackground = true, showSystemUi = true, name = "Hangman")
@Composable
fun HangmanPreview() {
    HangmanMobileTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = DeepSpace,
            contentWindowInsets = WindowInsets(0)
        ) { innerPadding ->
            App(
                modifier = Modifier
                    .padding(innerPadding)
                    .background(DeepSpace)
            )
        }
    }
}