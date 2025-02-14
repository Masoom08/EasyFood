
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import st.masoom.easyfood.Categories
import st.masoom.easyfood.Favorite
import st.masoom.easyfood.Search
import st.masoom.easyfood.Pages.Meal
import st.masoom.easyfood.Pages.RandomMealDetailPage
import st.masoom.easyfood.ViewModel.CategoryViewModel
import st.masoom.easyfood.ViewModel.RandomViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument


sealed class NavigationItem(var route: String, var icon: ImageVector?= null, var title: String="") {
    object Home : NavigationItem("home", Icons.Filled.Home, "Home")
    object Favorite : NavigationItem("favorite", Icons.Filled.Favorite, "Favorite")
    object Category : NavigationItem("category", Icons.Filled.Menu, "Category")
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val categoryViewModel: CategoryViewModel = viewModel()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        topBar = { CustomTopBar(currentRoute,navController) },
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            NavigationGraph(navController = navController,
                padding = Modifier.padding(paddingValues),
                categoryViewModel = categoryViewModel // Pass ViewModel here
            )
        }
    }
}

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
                    color =Color(0xFFF1062A),
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

@Composable
fun NavigationGraph(navController: NavHostController, padding: Modifier,categoryViewModel: CategoryViewModel) {
    val randomViewModel: RandomViewModel = viewModel()
    NavHost(
        navController,
        startDestination = NavigationItem.Home.route
    ) {
        composable(NavigationItem.Home.route) { Home(navController=navController) }
        composable(NavigationItem.Favorite.route) { Favorite() }
        composable(NavigationItem.Category.route) { Categories(categoryViewModel = categoryViewModel) }
        composable("search") { Search(navController = navController) }

        composable(
            route = "random_meal_detail/{mealId}",
            arguments = listOf(navArgument("mealId") { type = NavType.StringType })
        ) { backStackEntry -> val mealId = backStackEntry.arguments?.getString("mealId")
        RandomMealDetailPage(mealId = mealId ?: "", navController = navController, randomViewModel = randomViewModel)
        }

        composable(
            route = "meal/{mealId}?imageUrl={imageUrl}&mealName={mealName}&instructions={instructions}&youtubeLink={youtubeLink}",
            arguments = listOf(
                navArgument("mealId") { type = NavType.StringType },
                navArgument("imageUrl") { type = NavType.StringType },
                navArgument("mealName") { type = NavType.StringType },
                navArgument("instructions") { type = NavType.StringType },
                navArgument("youtubeLink") { type = NavType.StringType }

            )
        ) { backStackEntry ->
            val mealId = backStackEntry.arguments?.getString("mealId")
            val imageUrl = backStackEntry.arguments?.getString("imageUrl")
            val mealName = backStackEntry.arguments?.getString("mealName")
            val instructions = backStackEntry.arguments?.getString("instructions")
            val youtubeLink = backStackEntry.arguments?.getString("youtubeLink")

            Meal(
                mealId = mealId ,
                mealImageUrl = imageUrl ?:"URL for the meal image",
                mealName = mealName ?:"Meal name",
                mealInstructions = instructions ?:"Recipe instructions",
                youtubeLink = youtubeLink ?: "https://www.youtube.com/",
            )
        }
    }
}