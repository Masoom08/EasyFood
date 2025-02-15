package st.masoom.easyfood.Navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Favorite,
        NavigationItem.Category
    )
    val currentRoute = navController.currentBackStackEntryAsState()?.value?.destination?.route
    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = { item.icon?.let{Icon(it, contentDescription = item.title)} },
                label = { Text(item.title) },
                selected = currentRoute?.startsWith(item.route) == true, // Improved selection logic
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(NavigationItem.Home.route) { inclusive = false } // Ensuring proper backstack behavior
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}
