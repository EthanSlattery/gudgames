package com.prosody.gudgames.ui.mygames

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.prosody.gudgames.ui.gameshelf.GameShelf
import com.prosody.gudgames.ui.model.Game
import com.prosody.gudgames.ui.theme.MyApplicationTheme

@Composable
fun MyGamesRoute(modifier: Modifier = Modifier, viewModel: MyGamesViewModel = hiltViewModel()) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val state by produceState<MyGamesUiState>(
        initialValue = MyGamesUiState.Loading,
        key1 = lifecycle,
        key2 = viewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            viewModel.uiState.collect { value = it }
        }
    }
    
    when (state) {
        is MyGamesUiState.Success -> {
            MyGamesScreen(state, viewModel::addGame, modifier)
        }
        is MyGamesUiState.Error -> TODO()
        MyGamesUiState.Loading -> CircularProgressIndicator()
    }
}

@Composable
fun MyGamesScreen(state: MyGamesUiState, onSave: (String) -> Unit, modifier: Modifier = Modifier,) {
    GameShelf(
        games = (state as MyGamesUiState.Success).games,
        onSave = onSave,
        modifier = modifier
    )
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