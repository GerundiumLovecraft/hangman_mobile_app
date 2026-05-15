package com.example.hangmanmobile

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.hangmanmobile.ui.theme.HotPink

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun VirtualKeyboard(
    guessedLetters: Set<Char>,
    onLetterClick: (Char) -> Unit,
    modifier: Modifier = Modifier
) {
    val letters = stringArrayResource(R.array.alphabet)
    val itemSize = LocalConfiguration.current.screenWidthDp.dp / 7

    FlowRow(
        maxItemsInEachRow = 7,
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        letters.forEach { letter ->
            val char = letter[0]
            if (char in guessedLetters) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(itemSize)
                        .padding(6.dp)
                        .background(HotPink.copy(alpha = 0.2f))
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    Text(
                        text = letter,
                        color = HotPink.copy(alpha = 0.4f)
                    )
                }
            } else {
                LetterBtn(
                    letter = char,
                    onClick = {onLetterClick(char)},
                    modifier = Modifier
                        .size(itemSize)
                )
            }
        }
    }
}