import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import android.content.Context
import android.widget.ImageView
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import st.masoom.easyfood.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import st.masoom.easyfood.Navigation.NavigationItem
import st.masoom.easyfood.ViewModel.CategoryViewModel
import st.masoom.easyfood.ViewModel.PopularMealViewModel
import st.masoom.easyfood.ViewModel.RandomViewModel
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip


@Composable
fun Home(randomViewModel: RandomViewModel = viewModel(),
         categoryViewModel: CategoryViewModel = viewModel(),
         popularMealViewModel: PopularMealViewModel = viewModel(),
         navController: NavController
) {
    val randomMeal = randomViewModel.randomMeal.collectAsState().value
    val categories = categoryViewModel.categories.observeAsState()
    val popularMeals = popularMealViewModel.popularMeals.collectAsState().value

    // List of available meal categories
    val mealCategories = listOf("Beef", "Chicken", "Dessert", "Lamb", "Miscellaneous", "Pasta",
        "Pork", "Seafood", "Side", "Starter", "Vegan", "Vegetarian",
        "Breakfast", "Goat")

    // State to store selected category
    var selectedCategory by remember { mutableStateOf(mealCategories[0]) }
    var expanded by remember { mutableStateOf(false) }

    // Fetch meals when category changes
    LaunchedEffect(selectedCategory) {
        popularMealViewModel.getPopularMeals(selectedCategory)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
    ) {
        // Title: What would you like to eat
        item {
            Text(
                text = "What would you like to eat?",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                ),
            )
            Spacer(modifier = Modifier.padding(16.dp))
        }
        // Rectangular card for random image
        item {
            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clickable {
                        // Handle click event
                        navController.navigate("random_meal_detail/${randomMeal?.idMeal ?: ""}")
                    },
                colors = CardDefaults.cardColors(containerColor = Color.LightGray)
            ) {
                if (randomMeal != null) {
                    Image(
                        painter = rememberImagePainter(data = randomMeal.strMealThumb),
                        contentDescription = randomMeal.strMeal,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Random Recipe Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            Spacer(modifier = Modifier.padding(16.dp))
        }
        // Title: Our popular items
        item {
            Text(
                text = "Our popular items",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.SansSerif,
                ),
            )
            Spacer(modifier = Modifier.padding(8.dp))
        }
        // Dropdown Menu
        item {
            CategoryDropdown(
                selectedCategory = selectedCategory,
                onCategorySelected = { newCategory ->
                    selectedCategory = newCategory
                    popularMealViewModel.getPopularMeals(newCategory)
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Horizontal scrollable popular items
        item {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                popularMeals?.let { meals ->
                    items(meals) { meal ->
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .width(150.dp)
                                .height(100.dp)
                                .clickable {
                                    // Navigate to meal detail page using meal ID
                                    navController.navigate("popular_meal_detail/${meal.idMeal}")
                                },
                            colors = CardDefaults.cardColors(containerColor = Color.LightGray)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Image(
                                    painter = rememberImagePainter(
                                        data = meal.strMealThumb ?: R.drawable.logo

                                    ),
                                    contentDescription = meal.strMeal ?:"Unknown Meal",
                                    modifier = Modifier
                                        .fillMaxWidth() // Make the image fill the full width of the card
                                        .height(180.dp) ,
                                    contentScale = ContentScale.Crop
                                )
                                // Display the meal's name
                                Text(
                                    text = meal.strMeal ?: "Unknown Meal",
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }
                    }
                }?: run {
                    // Show a placeholder or message if no meals are available
                    item {
                        Text(
                            text = "No popular meals available",
                            style = TextStyle(
                                color = Color.Gray,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.padding(16.dp))
        }
        // Title: Category
        item {
            Text(
                text = "Category",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.SansSerif,
                ),
            )
            Spacer(modifier = Modifier.padding(16.dp))
        }
        // Rectangular categories
        items(categories.value?.take(4) ?: emptyList()) { category ->
            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(vertical = 8.dp)
                    .clickable {
                        // Navigate to CategoryMeals screen and pass the selected category
                        navController.navigate("category_meals/${category.strCategory}")
                    },
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Show category image or default logo
                    Image(
                        painter = rememberImagePainter(data = category.strCategoryThumb ?: R.drawable.logo),
                        contentDescription = category.strCategory ?: "Default Category",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(100.dp)
                            .padding(8.dp)
                    )
                    // Show category name
                    Text(
                        text = category.strCategory ?: "Category Name",
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }
        }

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { navController.navigate(NavigationItem.Category.route) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "More Categories",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif,
                    ),
                    modifier = Modifier
                        .padding(12.dp)
                        .background(Color(0xFFF1062A), RoundedCornerShape(12.dp))
                        .padding(horizontal = 24.dp, vertical = 8.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropdown(selectedCategory: String, onCategorySelected: (String) -> Unit) {
    val mealCategories = listOf("Beef", "Chicken", "Dessert", "Lamb", "Miscellaneous", "Pasta",
        "Pork", "Seafood", "Side", "Starter", "Vegan", "Vegetarian",
        "Breakfast", "Goat")
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedCategory,
            onValueChange = { },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0xFFF5F5F5))
                .clickable { expanded = true },
            placeholder = { Text("Select Category", fontSize = 12.sp, color = Color.Gray) },
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.primary
            ),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown",
                    tint = Color.Gray,
                    modifier = Modifier.clickable { expanded = true }
                )
            }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }) {
            mealCategories.forEach { category ->
                DropdownMenuItem(
                    text = { Text(text = category) },
                    onClick = {
                        onCategorySelected(category)
                        expanded = false
                    }
                )
            }
        }
    }
}
