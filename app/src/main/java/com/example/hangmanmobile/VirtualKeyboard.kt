package com.example.hangmanmobile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun VirtualKeyboard(
    guessedLetters: Set<Char>,
    onLetterClick: (Char) -> Unit,
    modifier: Modifier = Modifier
) {
    val letters = stringArrayResource(R.array.alphabet)

    FlowRow(
        maxItemsInEachRow = 7,
        modifier = modifier
    ) {
        letters.forEach { letter ->
            val char = letter[0]
            if (char in guessedLetters) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .padding(2.dp)
                ) {
                    Text(
                        text = letter
                    )
                }
            } else {
                LetterBtn(
                    letter = char,
                    onClick = {onLetterClick(char)},
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                )
            }
        }
    }
}