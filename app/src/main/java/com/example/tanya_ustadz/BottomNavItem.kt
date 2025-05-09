package com.example.tanya_ustadz

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ListItemDefaults.contentColor
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState


data class BottomNavItems(
    val title: String,
    val route: String,
    val icon: @Composable () -> Unit
)

@Composable
fun bottomNavItems(): List<BottomNavItems> {
    val contentColor = contentColor
    return remember {


        listOf(
            BottomNavItems("", "jadwal") { Gambar(contentColor) },
            BottomNavItems("", "cari") { Icon(Icons.Default.Search) },
            BottomNavItems("", "tambah") { Icon(Icons.Default.Add) },
            BottomNavItems("", "akun") { Icon(Icons.Default.Person) },
            BottomNavItems("", "tambah_doa") { Icon(Icons.Default.Create) }


        )
    }
}

@Composable
fun Gambar(tint: Color = contentColor) {
    Image(
        painter = painterResource(id = R.drawable.iconshalat),
        contentDescription = "Deskripsi Gambar",
        colorFilter = ColorFilter.tint(tint)
        )
}

@Composable
fun Icon(iconVector: ImageVector, tint: Color = contentColor) {
    Image(
        imageVector = iconVector,
        contentDescription = "Icon",
        colorFilter = ColorFilter.tint(tint)
    )
}


@SuppressLint("SuspiciousIndentation")
@Composable
fun BottomBawah(navController: NavController) {

    val isDark = isSystemInDarkTheme()
    val cardColor = if (isDark) Color(0xFF1E1E1E) else Color.White
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val items = bottomNavItems()

        NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .shadow(8.dp, spotColor = Color.Gray),
        containerColor = cardColor
    ) {
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
                    selectedIconColor = contentColor,
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = contentColor,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color.Transparent
                ),
                interactionSource = remember { MutableInteractionSource() }
            )
        }
    }

}
