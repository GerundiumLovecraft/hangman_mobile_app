package com.example.hangmanmobile

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.hangmanmobile.models.WordGuesser

class GameViewModel(application: Application) : AndroidViewModel(application) {
    var gameOn by mutableStateOf(false)
        private set
    var failures by mutableStateOf(0)
        private set
    var displayWord by mutableStateOf("")
        private set
    var guessedLetters by mutableStateOf(setOf<Char>())
        private set
    var gameWon by mutableStateOf(false)

    // array of words for the game
    private val wordLibrary = getApplication<Application>()
        .resources
        .getStringArray(R.array.words_array)

    // word handler to guess the word
    private lateinit var wordGuesser: WordGuesser

    fun startGame() {
        wordGuesser = WordGuesser(wordLibrary.random())
        failures = 0
        guessedLetters = setOf()
        displayWord = wordGuesser.getGuesser()
        gameOn = true
        gameWon = false
    }

    fun endGame() {
        gameOn = false
    }

    /*
        turn() will receive a character
        Check the character against the guessed word
        If the turn results in victory (the whole word is guessed)
        or defeat (6 failures)
     */
    fun turn(letter: Char) {
        guessedLetters = guessedLetters + letter

        if (wordGuesser.guessByLetter(letter)) {
            displayWord = wordGuesser.getGuesser()
            if (!displayWord.contains("_")) {
                gameWon = true
                endGame()
            }
        } else {
            //Ends the game at the sixth failure
            if (failures >= 5) {
                failures = 6
                endGame()
            } else {
                failures++
            }
        }
    }
}