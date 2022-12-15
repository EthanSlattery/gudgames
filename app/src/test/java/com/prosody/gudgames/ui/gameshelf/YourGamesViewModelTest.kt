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

package com.prosody.gudgames.ui.gameshelf


import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import com.prosody.gudgames.data.GameRepository
import com.prosody.gudgames.ui.model.Game
import com.prosody.gudgames.ui.mygames.MyGamesUiState
import com.prosody.gudgames.ui.mygames.MyGamesViewModel

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@OptIn(ExperimentalCoroutinesApi::class) // TODO: Remove when stable
class MyGamesViewModelTest {
    @Test
    fun uiState_initiallyLoading() = runTest {
        val viewModel = MyGamesViewModel(FakeGameRepository())
        assertEquals(viewModel.uiState.first(), MyGamesUiState.Loading)
    }

    @Test
    fun uiState_onItemSaved_isDisplayed() = runTest {
        val viewModel = MyGamesViewModel(FakeGameRepository())
        assertEquals(viewModel.uiState.first(), MyGamesUiState.Loading)
    }
}

private class FakeGameRepository : GameRepository {

    private val data = mutableListOf<Game>()

    override val games: Flow<List<Game>>
        get() = flow { emit(data.toList()) }

    override suspend fun add(game: Game) {
        data.add(0, game)
    }
}
