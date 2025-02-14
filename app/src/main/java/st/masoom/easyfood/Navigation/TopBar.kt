package st.masoom.easyfood.Navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(currentRoute: String?,navController: NavController) {
    val title = when (currentRoute) {
        NavigationItem.Home.route -> "Home"
        NavigationItem.Favorite.route -> "Favorite"
        NavigationItem.Category.route -> "Categories"
        "search" -> "Search"
        else -> "Easy Food"
    }

    TopAppBar(
        title = {
            Text(
                text = title,
                style = TextStyle(
                    color = Color(0xFFF1062A),
                    fontSize = 28.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,

                    ),
                modifier = Modifier.padding(vertical = 0.dp)
            )
        },
        actions = {
            if (currentRoute == NavigationItem.Home.route) {
                IconButton(onClick = { navController.navigate("search") }) {
                    Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors()
    )
}