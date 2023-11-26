package com.nasywa.freshfusion.menu

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nasywa.freshfusion.R
import com.nasywa.freshfusion.navigation.Item
import com.nasywa.freshfusion.navigation.FreshFusionMain
import com.nasywa.freshfusion.ui.theme.JosefinSans
import com.nasywa.freshfusion.ui.theme.background1
import com.nasywa.freshfusion.ui.theme.background2
import com.nasywa.freshfusion.ui.theme.firstColor
import com.nasywa.freshfusion.ui.theme.selectedItemColor


@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {

    NavigationBar(
        containerColor = background2,
        contentColor = firstColor,
        modifier = modifier.shadow(7.dp),
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val items = listOf(
            Item(
                title = stringResource(R.string.home),
                icon = R.drawable.home,
                freshFusionMain = FreshFusionMain.Home
            ),
            Item(
                title = stringResource(R.string.favorite),
                icon = R.drawable.favorite_save,
                freshFusionMain = FreshFusionMain.Favorite
            ),
            Item(
                title = stringResource(R.string.profile),
                icon = R.drawable.profile,
                freshFusionMain = FreshFusionMain.About
            ),
        )

        items.map { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(item.icon),
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title, fontFamily = JosefinSans) },
                selected = currentRoute == item.freshFusionMain.route,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = background1,
                    selectedTextColor = selectedItemColor,
                    indicatorColor = selectedItemColor,
                    unselectedIconColor = selectedItemColor,
                    unselectedTextColor = selectedItemColor
                ),
                onClick = {
                    navController.navigate(item.freshFusionMain.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}