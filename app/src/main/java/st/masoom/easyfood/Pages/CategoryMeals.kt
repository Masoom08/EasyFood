package st.masoom.easyfood.Pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import st.masoom.easyfood.Pojo.Meal
import st.masoom.easyfood.R
import st.masoom.easyfood.ViewModel.CategoryMealsViewModel

@Composable
fun CategoryMealsPage(
    categoryName: String,
    navController: NavController,
    categoryMealsViewModel: CategoryMealsViewModel = viewModel()
) {
    val meals by categoryMealsViewModel.categoryMeals.collectAsState()

    LaunchedEffect(categoryName) {
        categoryMealsViewModel.getMealsByCategory(categoryName)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
            .padding(8.dp)
    ) {
        Text(
            text = "Recipes for $categoryName",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )

        LazyColumn {
            items(meals) { meal ->
                MealItem(meal, navController)
            }
        }
    }
}

@Composable
fun MealItem(meal: Meal, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate("random_meal_detail/${meal.idMeal}")
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(Color.Gray)
        ) {
            AsyncImage(
                model = meal.strMealThumb,
                contentDescription = meal.strMeal,
                modifier = Modifier.fillMaxSize(),
                placeholder = painterResource(id = R.drawable.logo), // Placeholder logo
                error = painterResource(id = R.drawable.logo) // Show logo on error
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = meal.strMeal,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}
