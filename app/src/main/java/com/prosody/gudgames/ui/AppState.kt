package com.prosody.gudgames.ui

import androidx.compose.runtime.*
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.prosody.gudgames.ui.mygames.myGamesNavigationRoute
import com.prosody.gudgames.ui.mygames.navigateToMyGames
import com.prosody.gudgames.ui.navigation.TopLevelDestination

@Composable
fun rememberAppState(navController: NavHostController = rememberNavController()): AppState {
    return remember(navController) {
        AppState(navController)
    }
}

class AppState(
    val navController: NavHostController,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            myGamesNavigationRoute -> TopLevelDestination.MY_GAMES
            else -> null
        }

    var shouldShowSettingsDialog by mutableStateOf(false)
        private set

    /**
     * Map of top level destinations to be used in the TopBar, BottomBar and NavRail. The key is the
     * route.
     */
    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.values().asList()

    /**
     * UI logic for navigating to a top level destination in the app. Top level destinations have
     * only one copy of the destination of the back stack, and save and restore state whenever you
     * navigate to and from it.
     *
     * @param topLevelDestination: The destination the app needs to navigate to.
     */
    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }

        when (topLevelDestination) {
            TopLevelDestination.MY_GAMES -> navController.navigateToMyGames(topLevelNavOptions)
            else -> {}
        }
    }

    fun onBackClick() {
        navController.popBackStack()
    }

    fun setShowSettingsDialog(shouldShow: Boolean) {
        shouldShowSettingsDialog = shouldShow
    }
}