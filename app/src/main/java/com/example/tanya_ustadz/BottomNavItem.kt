package com.example.tanya_ustadz

import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.tanya_ustadz.ui.theme.AppColors.surfaceBrightDark
import com.example.tanya_ustadz.ui.theme.AppColors.surfaceDimDark


data class BottomNavItems(
    val title: String,
    val route: String,
    val icon: @Composable () -> Unit
)

@Composable
fun bottomNavItems(): List<BottomNavItems> {
    return remember {


        listOf(
            BottomNavItems("", "jadwal") { Gambar() },
            BottomNavItems("", "") { Icon(Icons.Default.Search) },
            BottomNavItems("", "") { Icon(Icons.Default.Add) }

        )
    }
}

@Composable
fun Gambar() {
    Image(
        painter = painterResource(id = R.drawable.iconshalat),
        contentDescription = "Deskripsi Gambar",

        )
}

@Composable
fun Icon(iconVector: ImageVector) {
    Image(
        imageVector = iconVector,
        contentDescription = "Icon"
    )
}


@Composable
fun BottomBawah(navController: NavController) {

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .shadow(8.dp, spotColor = Color.Gray),
        containerColor = Color.White,
        contentColor = Color.Black

    ) {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        val items = bottomNavItems()

        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    item.icon()
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = surfaceDimDark,
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = surfaceBrightDark,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color.Transparent
                ),
                interactionSource = remember { MutableInteractionSource() }
            )
        }
    }

}