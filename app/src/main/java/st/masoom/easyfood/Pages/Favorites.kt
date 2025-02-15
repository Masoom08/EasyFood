package st.masoom.easyfood

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import st.masoom.easyfood.Local.MealEntity
import st.masoom.easyfood.ViewModel.FavoritesViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController

@Composable
fun Favorite(
    navController : NavController,
    viewModel: FavoritesViewModel = viewModel()
) {
    val favoriteMeals by viewModel.allMeals.collectAsState(initial = emptyList())

    Column(modifier = Modifier.fillMaxSize()) {


        if (favoriteMeals.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No favorites yet!")
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(favoriteMeals) { meal ->
                    FavoriteMealItem(meal, viewModel,navController)
                }
            }
        }
    }
}

@Composable
fun FavoriteMealItem(meal: MealEntity, viewModel: FavoritesViewModel, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                // Navigate to RandomMealDetailPage with meal ID
                navController.navigate("random_meal_detail/${meal.mealId}")
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberImagePainter(meal.mealThumb),
            contentDescription = meal.mealName,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(70.dp)
                .padding(8.dp)
        )

        Text(
            text = meal.mealName,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
        )

        IconButton(
            onClick = { viewModel.delete(meal) }
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Unfavorite",
                tint = Color.Red
            )
        }
    }
}
