package com.prosody.gudgames.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.prosody.gudgames.R

enum class TopLevelDestination(
    val selectedIcon: Icon,
    val unselectedIcon: Icon,
    @StringRes
    val iconTextId: Int,
    @StringRes
    val titleTextId: Int
) {
    HOME(
        selectedIcon = Icon(R.drawable.ic_baseline_home_24, R.string.home),
        unselectedIcon = Icon(R.drawable.ic_outline_home_24, R.string.home),
        iconTextId = R.string.home,
        titleTextId = R.string.app_name
    ),
    MY_GAMES(
        selectedIcon = Icon(R.drawable.ic_baseline_videogame_asset_24, R.string.my_games),
        unselectedIcon = Icon(R.drawable.ic_outline_videogame_asset_24, R.string.my_games),
        iconTextId = R.string.my_games,
        titleTextId = R.string.my_games
    ),
    SEARCH(
        selectedIcon = Icon(R.drawable.ic_baseline_search_24, R.string.search),
        unselectedIcon = Icon(R.drawable.ic_outline_search_24, R.string.search),
        iconTextId = R.string.search,
        titleTextId = R.string.search
    )
}

class Icon(
    @DrawableRes
    val drawableId: Int,
    @StringRes
    val contentDescriptionId: Int
)
