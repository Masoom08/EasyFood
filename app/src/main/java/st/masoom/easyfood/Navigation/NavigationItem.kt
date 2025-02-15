package st.masoom.easyfood.Navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(var route: String, var icon: ImageVector?= null, var title: String="") {
    object Home : NavigationItem("home", Icons.Filled.Home, "Home")
    object Favorite : NavigationItem("favorite", Icons.Filled.Favorite, "Favorite")
    object Category : NavigationItem("category", Icons.Filled.Menu, "Category")
}