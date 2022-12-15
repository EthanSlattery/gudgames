package com.prosody.gudgames.ui.mygames

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val myGamesNavigationRoute = "my_games_route"

fun NavController.navigateToMyGames(navOptions: NavOptions? = null) {
    this.navigate(myGamesNavigationRoute, navOptions)
}

fun NavGraphBuilder.myGamesScreen() {
    composable(route = myGamesNavigationRoute) {
        MyGamesRoute()
    }
}