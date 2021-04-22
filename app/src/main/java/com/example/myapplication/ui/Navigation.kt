package com.example.myapplication.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Store
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.example.myapplication.R
import com.example.myapplication.util.Constants


/**
 * @Description 导航
 * @Author tll
 * @Date 2021/4/10 11:37
 */

@ExperimentalFoundationApi
@Preview
@Composable
fun Navigation() {

    val baseTitle = stringResource(id = R.string.app_name)
    val (title, setTitle) = remember { mutableStateOf(baseTitle) }

    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        color = Color.White
                    )
                },
            )
        },
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)
                items.forEach { screen ->
                    BottomNavigationItem(
                        icon = { Icon(screen.icon, screen.route) },
                        label = { Text(stringResource(screen.resourceId)) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo = navController.graph.startDestination
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        }
    ) {
        NavHost(navController = navController, startDestination = Screen.Find.route) {
            composable(Screen.Find.route) {
                FindScreen(navController, setTitle)
            }
            composable(Screen.Store.route) {
                StoreScreen(navController, setTitle)
            }
            composable(Screen.Favourite.route) {
                FavouriteScreen(navController, setTitle)
            }
            composable(Screen.ProFile.route) {
                ProfileScreen(navController, setTitle)
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun FindScreen(
    navController: NavController,
    setTitle: (String) -> Unit
) {
    setTitle(stringResource(id = R.string.tab_find))
    Find()
}

@ExperimentalFoundationApi
@Composable
fun StoreScreen(
    navController: NavController,
    setTitle: (String) -> Unit
) {
    setTitle(stringResource(id = R.string.tab_staore))
    Store()
}

@ExperimentalFoundationApi
@Composable
fun FavouriteScreen(
    navController: NavController,
    setTitle: (String) -> Unit
) {
    setTitle(stringResource(id = R.string.tab_favourite))
    Favourite()
}


@ExperimentalFoundationApi
@Composable
fun ProfileScreen(
    navController: NavController,
    setTitle: (String) -> Unit
) {
    setTitle(stringResource(id = R.string.tab_profile))
    ProFile()
}

val items = listOf(
    Screen.Find,
    Screen.Store,
    Screen.Favourite,
    Screen.ProFile
)

sealed class Screen(val route: String, val icon: ImageVector, @StringRes val resourceId: Int) {
    object Find : Screen(Constants.ROUTE_FIND, Icons.Default.Search, R.string.tab_find)
    object Store : Screen(Constants.ROUTE_STORE, Icons.Default.Store, R.string.tab_staore)
    object Favourite :
        Screen(Constants.ROUTE_FAVOURITE, Icons.Default.Favorite, R.string.tab_favourite)

    object ProFile :
        Screen(Constants.ROUTE_PROFILE, Icons.Default.AccountCircle, R.string.tab_profile)
}