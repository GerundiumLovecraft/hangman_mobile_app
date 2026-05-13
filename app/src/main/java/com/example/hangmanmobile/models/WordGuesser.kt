package com.example.hangmanmobile.models

class WordGuesser (
    val word: String,
    val guesser: MutableList<Char> = MutableList(word.length) {'_'}
    ) {
        fun getGuesser(): String {
            return guesser.joinToString("")
        }

        fun guessByLetter(letter: Char): Boolean {
            println("Guessing $word")
            if (!word.contains(letter)) return false
            for (i in word.indices) {
                if (word[i] == letter) {
                    guesser[i] = letter
                }
            }
            return true
        }
}