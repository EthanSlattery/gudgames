package com.prosody.gudgames.ui.gameshelf

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.prosody.gudgames.ui.model.Game

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun GameShelf(
    games: List<Game>,
    onSave: (game: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var nameGame by remember { mutableStateOf("Compose") }
    LazyColumn(modifier) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TextField(
                    value = nameGame,
                    onValueChange = { nameGame = it }
                )

                Button(modifier = Modifier.width(96.dp), onClick = { onSave(nameGame) }) {
                    Text("Save")
                }
            }
        }
        items(games) { game ->
            Row() {}
            Text("Saved item: ${game.title}")
        }
    }
}