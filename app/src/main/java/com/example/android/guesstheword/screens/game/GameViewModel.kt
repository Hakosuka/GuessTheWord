/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.guesstheword.screens.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

private const val TAG = "GameViewModel"
class GameViewModel : ViewModel() {
    // The current word - it's *Mutable*LiveData to allow for setters to be called on it
    private val inWord = MutableLiveData<String>()
    val word : LiveData<String>
        get() = inWord //return internal word

    // The current score
    private val inScore = MutableLiveData<Int>()
    val score : LiveData<Int>
        get() = inScore //return internal score

    private val inEGF = MutableLiveData<Boolean>()
    val eventGameFinish : LiveData<Boolean>
        get() = inEGF

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    init {
        inEGF.value = false
        resetList()
        nextWord()
        inScore.value = 0
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "GameViewModel destroyed")
    }

    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf("queen", "hospital", "basketball", "cat", "change", "snail", "soup",
                "calendar", "sad", "desk", "guitar", "home", "railway", "zebra", "jelly", "car",
                "crow", "trade", "bag", "roll", "bubble", "drink", "football", "sandwich",
                "baseball", "spaghetti"
        )
        wordList.shuffle()
    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            //TODO: Re-integrate gameFinished()
            inEGF.value = true
        } else {
            inWord.value = wordList.removeAt(0)
        }
        //updateWordText()
        //updateScoreText()
    }
    /** Informs Observers that the finish has been handled**/
    fun onGameFinishComplete() {
        inEGF.value = false
    }
    /** Methods for buttons presses **/
    fun onSkip() {
        inScore.value = (score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        inScore.value = (score.value)?.plus(1)
        nextWord()
    }
}