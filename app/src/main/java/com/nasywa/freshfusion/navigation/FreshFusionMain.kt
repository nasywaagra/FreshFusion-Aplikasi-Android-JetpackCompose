package com.nasywa.freshfusion.navigation

sealed class FreshFusionMain(val route: String) {
    object Home : FreshFusionMain("Home")
    object Favorite : FreshFusionMain("Favorite")
    object About : FreshFusionMain("About")
    object DetailFusion: FreshFusionMain("home/{fusionId}") {
        fun createRoute(fusionId: Long) = "home/$fusionId"
    }
}