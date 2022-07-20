@file:OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class, ExperimentalUnitApi::class)

package cfsb.crimsoncoder.osc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LastPage
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cfsb.crimsoncoder.osc.pages.SetEndTimePage
import cfsb.crimsoncoder.osc.pages.SetStartTimePage
import cfsb.crimsoncoder.osc.ui.theme.OptimalSleepCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val PAGES = listOf(
            Page.SetStartTime,
            Page.SetEndTime
        )

        setContent {
            OptimalSleepCalculatorTheme (darkTheme = true) {
                val navController = rememberNavController()

                Scaffold(
                    bottomBar = { BottomNavigationBar(navController, pages = PAGES) }
                ) { innerPadding ->
                    NavHost(
                        navController,
                        startDestination = Page.SetStartTime.route,
                        Modifier.padding(innerPadding)
                    ) {
                        composable(Page.SetStartTime.route) { SetStartTimePage() }
                        composable(Page.SetEndTime.route) { SetEndTimePage() }
                    }
                }
            }
        }
    }
}

sealed class Page (@StringRes val title_rid: Int, val route: String, val navIcon: ImageVector) {
    object SetStartTime : Page(R.string.page_start, "pg_set-start-time", Icons.Outlined.PlayCircle)
    object SetEndTime : Page(R.string.page_end, "pg_set-end_time", Icons.Outlined.LastPage)
}

@Composable
fun BottomNavigationBar (navController: NavController, pages: List<Page>) {
    BottomNavigation() {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        pages.forEach { page ->
            BottomNavigationItem(
                icon = { Icon(page.navIcon, contentDescription = null) },
                label = { Text(stringResource(page.title_rid)) },
                selected = currentDestination?.hierarchy?.any { it.route == page.route } == true,
                onClick = {
                    navController.navigate(page.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
