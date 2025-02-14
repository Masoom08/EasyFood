
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import st.masoom.easyfood.Categories
import st.masoom.easyfood.Favorite
import st.masoom.easyfood.Navigation.NavigationItem
import st.masoom.easyfood.Pages.CategoryMealsPage
import st.masoom.easyfood.Search
import st.masoom.easyfood.Pages.RandomMealDetailPage
import st.masoom.easyfood.ViewModel.CategoryViewModel
import st.masoom.easyfood.ViewModel.RandomViewModel
import st.masoom.easyfood.ViewModel.SearchViewModel

@Composable
fun NavigationGraph(
    navController: NavHostController,
    categoryViewModel: CategoryViewModel
) {
    val randomViewModel: RandomViewModel = viewModel()
    val searchViewModel: SearchViewModel = viewModel() // Added SearchViewModel
    //val categoryViewModel: CategoryViewModel= viewModel()

    NavHost(
        navController,
        startDestination = NavigationItem.Home.route
    ) {
        composable(NavigationItem.Home.route) { Home(navController=navController) }
        composable(NavigationItem.Favorite.route) { Favorite(navController=navController) }
        composable(NavigationItem.Category.route) { Categories(categoryViewModel = categoryViewModel, navController = navController) }
        composable("search") { Search(navController = navController) }

        composable(
            route = "random_meal_detail/{mealId}",
            arguments = listOf(navArgument("mealId") { type = NavType.StringType })
        ) { backStackEntry -> val mealId = backStackEntry.arguments?.getString("mealId")
        RandomMealDetailPage(mealId = mealId ?: "", navController = navController, randomViewModel )
        }

        composable(
            route = "search_meal_detail/{mealId}",
            arguments = listOf(navArgument("mealId") { type = NavType.StringType })
        ) { backStackEntry ->
            val mealId = backStackEntry.arguments?.getString("mealId") ?: ""
            RandomMealDetailPage(mealId = mealId, navController = navController, searchViewModel)
        }

        composable(
            route = "popular_meal_detail/{mealId}",
            arguments = listOf(navArgument("mealId") { type = NavType.StringType })
        ) { backStackEntry ->
            val mealId = backStackEntry.arguments?.getString("mealId") ?: ""
            RandomMealDetailPage(mealId = mealId, navController = navController, randomViewModel)
        }

        composable("category_meals/{categoryName}") { backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
            CategoryMealsPage(categoryName = categoryName, navController = navController)
        }

    }
}