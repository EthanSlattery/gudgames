/*
 * Copyright (C) 2022 The Android Open Source Project
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

package com.prosody.gudgames.ui.mygames

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.prosody.gudgames.data.GameRepository
import com.prosody.gudgames.ui.model.Game
import com.prosody.gudgames.ui.mygames.MyGamesUiState.Error
import com.prosody.gudgames.ui.mygames.MyGamesUiState.Loading
import com.prosody.gudgames.ui.mygames.MyGamesUiState.Success
import javax.inject.Inject

@HiltViewModel
class MyGamesViewModel @Inject constructor(
    private val gameRepository: GameRepository
) : ViewModel() {

    val uiState: StateFlow<MyGamesUiState> = gameRepository
        .games.map(::Success)
        .catch { Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)

    fun addGame(gameTitle: String) {
        viewModelScope.launch {
            gameRepository.add(Game(title = gameTitle))
        }
    }
}

sealed interface MyGamesUiState {
    object Loading : MyGamesUiState
    data class Error(val throwable: Throwable) : MyGamesUiState
    data class Success(val games: List<Game>) : MyGamesUiState
}
