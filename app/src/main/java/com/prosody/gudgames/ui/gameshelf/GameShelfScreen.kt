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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle.State.STARTED
import androidx.lifecycle.repeatOnLifecycle
import com.prosody.gudgames.ui.model.Game
import com.prosody.gudgames.ui.theme.MyApplicationTheme

@Composable
fun GameShelfScreen(modifier: Modifier = Modifier, viewModel: GameViewModel = hiltViewModel()) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val shelfState by produceState<GameShelfUiState>(
        initialValue = GameShelfUiState.Loading,
        key1 = lifecycle,
        key2 = viewModel
    ) {
        lifecycle.repeatOnLifecycle(state = STARTED) {
            viewModel.uiState.collect { value = it }
        }
    }
    when (shelfState) {
        is GameShelfUiState.Success -> {
            GameShelf(
                games = (shelfState as GameShelfUiState.Success).games,
                onSave = viewModel::addGame,
                modifier = modifier
            )
        }
        is GameShelfUiState.Error -> TODO()
        GameShelfUiState.Loading -> TODO()
    }
}

// Previews

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    MyApplicationTheme {
        GameShelf(games = listOf(Game("Compose"), Game("Room"), Game("Kotlin")), onSave = {})
    }
}

@Preview(showBackground = true, widthDp = 480)
@Composable
private fun PortraitPreview() {
    MyApplicationTheme {
        GameShelf(games = listOf(Game("Compose"), Game("Room"), Game("Kotlin")), onSave = {})
    }
}
