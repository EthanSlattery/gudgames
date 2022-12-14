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

package com.prosody.gudgames.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.prosody.gudgames.data.local.database.GameDao
import com.prosody.gudgames.data.local.database.GameEntity
import com.prosody.gudgames.ui.model.Game
import javax.inject.Inject

interface GameRepository {
    val games: Flow<List<Game>>

    suspend fun add(game: Game)
}

class DefaultGameRepository @Inject constructor(
    private val gameDao: GameDao
) : GameRepository {

    override val games: Flow<List<Game>> =
        gameDao.getGames().map { items -> items.map { Game(it.title) } }

    override suspend fun add(game: Game) {
        gameDao.insertGame(GameEntity(title = game.title))
    }
}
