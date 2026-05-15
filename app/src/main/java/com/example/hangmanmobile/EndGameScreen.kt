package com.example.hangmanmobile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hangmanmobile.ui.theme.CyanGlow
import com.example.hangmanmobile.ui.theme.HotPink
import com.example.hangmanmobile.ui.theme.OrbitronFamily
import com.example.hangmanmobile.ui.theme.ShareTechFamily
import com.example.hangmanmobile.ui.theme.TextDim

@Composable
fun EndGameScreen(
    gameView: GameViewModel,
    newGame: () -> Unit,
    backHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        if (gameView.gameWon) {
            Text(
                text = "YOU WON!",
                fontFamily = OrbitronFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 38.sp,
                letterSpacing = 8.sp,
                color = CyanGlow
            )
        } else {
            Text(
                text = "YOU LOST!",
                fontFamily = OrbitronFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 38.sp,
                letterSpacing = 8.sp,
                color = CyanGlow
            )
        }
        HorizontalDivider(
            modifier = Modifier
                .width(120.dp)
                .padding(vertical = 20.dp),
            thickness = 1.dp,
            color = CyanGlow.copy(alpha = 0.2f)
        )
        OutlinedButton(
            onClick = {
                newGame()
                gameView.startGame()
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
                text = "▶ NEW GAME",
                fontFamily = OrbitronFamily,
                fontSize = 13.sp,
                letterSpacing = 3.sp
            )
        }
        Spacer(modifier = modifier.height(20.dp))
        OutlinedButton(
            onClick = { backHome() },
            border = BorderStroke(1.5.dp, HotPink),
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = HotPink,
                containerColor = Color.Transparent
            ),
            modifier = Modifier.width(200.dp)
        ) {
            Text(
                text = "▶ HOME SCREEN",
                fontFamily = OrbitronFamily,
                fontSize = 13.sp,
                letterSpacing = 2.sp
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