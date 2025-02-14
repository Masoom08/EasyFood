package st.masoom.easyfood.Pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import st.masoom.easyfood.Pojo.Meal
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import st.masoom.easyfood.Local.MealDatabase
import st.masoom.easyfood.Local.MealEntity
import st.masoom.easyfood.Local.MealRepository
import st.masoom.easyfood.R
import st.masoom.easyfood.ViewModel.RandomViewModel

@Composable
fun RandomMealDetailPage(
    mealId: String,
    navController: NavController,
    randomViewModel: RandomViewModel
) {

    val context = LocalContext.current
    val database = MealDatabase.getDatabase(context)
    val mealRepository = remember { MealRepository(database.mealDao()) }
    // Show a back button to navigate back to the home page
    LaunchedEffect(mealId) {
        randomViewModel.fetchMealDetails(mealId)
    }

    val meal = randomViewModel.mealDetails.collectAsState().value
    // State to track favorite status
    var isFavorite by remember { mutableStateOf(false) }

    // Check if meal exists in favorites
    LaunchedEffect(meal) {
        meal?.let {
            isFavorite = mealRepository.isMealFavorite(it.idMeal ?: "")
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Back button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = { navController.popBackStack() }
            ) {
                Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier.size(24.dp)
                )
            }
            // Favorite button
            IconButton(
                onClick = {
                    meal?.let {
                        CoroutineScope(Dispatchers.IO).launch {
                            isFavorite = if (isFavorite) {
                                mealRepository.delete(
                                    MealEntity(
                                        mealId = it.idMeal ?: "",
                                        mealName = it.strMeal ?: "",
                                        mealThumb = it.strMealThumb ?: "",
                                        mealInstructions = it.strInstructions ?: "",
                                        youtubeLink = it.strYoutube ?: "" // Added missing value
                                    )
                                )
                                false
                            } else {
                                mealRepository.insert(
                                    MealEntity(
                                        mealId = it.idMeal ?: "",
                                        mealName = it.strMeal ?: "",
                                        mealThumb = it.strMealThumb ?: "",
                                        mealInstructions = it.strInstructions ?: "",
                                        youtubeLink = it.strYoutube ?: "" // Added missing value
                                    )
                                )
                                true
                            }
                        }
                    }
                }
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (isFavorite) Color.Red else Color.Gray
                )
            }
        }

        // Meal Image
        meal?.let {
            Image(
                painter = rememberImagePainter(data = it.strMealThumb),
                contentDescription = it.strMeal,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(bottom = 16.dp)
            )

            // Meal Name
            Text(
                text = it.strMeal ?: "Unknown Meal",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Recipe Instructions
            Text(
                text = it.strInstructions ?: "No instructions available.",
                style = TextStyle(
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            )
        } ?: run {
            // If no meal data is provided
            Text(text = "No details available")
        }
    }
}
