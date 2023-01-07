package org.jcastrejon.features.overview.ui

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.jcastrejon.features.overview.ui.OverviewSection.*
import org.jcastrejon.features.overview.ui.components.OverviewBottomBar
import org.jcastrejon.features.overview.ui.components.OverviewTopBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun OverviewScreen(
    modifier: Modifier = Modifier,
    onAddTransactionClicked: () -> Unit,
    onTransactionClicked: (Int) -> Unit,
    onSearchActionClicked: () -> Unit,
) {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = modifier
            .navigationBarsPadding(),
        topBar = {
            OverviewTopBar(
                onActionClick = onSearchActionClicked,
                navigationIcon = {
                    AnimatedVisibility(
                        visible = false,
                        enter = fadeIn() + expandIn(expandFrom = Alignment.CenterStart),
                        exit = shrinkOut(shrinkTowards = Alignment.CenterStart) + fadeOut(),
                    ) {
                        IconButton(
                            onClick = { }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Close,
                                contentDescription = null
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            OverviewBottomBar(
                currentRoute = currentDestination,
                onSectionSelected = { section ->
                    if (section.route != currentDestination) {
                        navController.navigate(section.route) {
                            launchSingleTop = true
                            popUpTo(Summary.route) {
                                inclusive = section == Summary
                            }
                        }
                    }
                },
                onButtonClicked = onAddTransactionClicked
            )
        }
    ) {
        NavHost(
            modifier = Modifier.padding(bottom = 56.dp),
            navController = navController,
            startDestination = Summary.route
        ) {
            composable(Summary.route) {
                Box(
                    modifier = Modifier
                        .background(Color.Red)
                        .fillMaxSize()
                )
            }

            composable(Stats.route) {
                Box(
                    modifier = Modifier
                        .background(Color.Blue)
                        .fillMaxSize()
                )
            }

            composable(Balance.route) {
                Box(
                    modifier = Modifier
                        .background(Color.Green)
                        .fillMaxSize()
                )
            }

            composable(Settings.route) {
                Box(
                    modifier = Modifier
                        .background(Color.Yellow)
                        .fillMaxSize()
                )
            }
        }
    }
}