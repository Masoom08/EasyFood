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
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import st.masoom.easyfood.R
import st.masoom.easyfood.ViewModel.RandomViewModel

@Composable
fun RandomMealDetailPage(mealId: String, navController: NavController, randomViewModel: RandomViewModel) {
    // Show a back button to navigate back to the home page
    LaunchedEffect(mealId) {
        randomViewModel.fetchMealDetails(mealId)
    }

    val meal = randomViewModel.mealDetails.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Back button
        IconButton(
            onClick = { navController.popBackStack() }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier.size(24.dp)
            )
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
