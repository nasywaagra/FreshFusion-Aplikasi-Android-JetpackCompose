package com.nasywa.freshfusion

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nasywa.freshfusion.freshfusionapp.about.CustomProfileScreen
import com.nasywa.freshfusion.menu.BottomBar
import com.nasywa.freshfusion.navigation.FreshFusionMain
import com.nasywa.freshfusion.freshfusionapp.detail.DetailScreen
import com.nasywa.freshfusion.freshfusionapp.favorite.FavoriteScreen
import com.nasywa.freshfusion.freshfusionapp.home.HomeScreen

@Composable
fun FreshFusionAPP(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != FreshFusionMain.DetailFusion.route) {
                BottomBar(navController = navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = FreshFusionMain.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(FreshFusionMain.Home.route) {
                HomeScreen(
                    navigateToDetail = { fusionId ->
                        navController.navigate(FreshFusionMain.DetailFusion.createRoute(fusionId))
                    }
                )
            }
            composable(FreshFusionMain.Favorite.route) {
                FavoriteScreen(
                    navigateToDetail = { fusionId ->
                        navController.navigate(FreshFusionMain.DetailFusion.createRoute(fusionId))
                    }
                )
            }
            composable(FreshFusionMain.About.route) {
                CustomProfileScreen()
            }
            composable(
                route = FreshFusionMain.DetailFusion.route,
                arguments = listOf(navArgument("fusionId") { type = NavType.LongType }),
            ) {
                val id = it.arguments?.getLong("fusionId") ?: -1L
                DetailScreen(
                    fusionId = id,
                    navigateBack = {
                        navController.navigateUp()
                    },
                )
            }
        }
    }
}