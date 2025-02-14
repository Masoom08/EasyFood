package st.masoom.easyfood

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import st.masoom.easyfood.Pojo.Meal
import st.masoom.easyfood.ViewModel.SearchViewModel
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.draw.clip


@OptIn(ExperimentalMaterial3Api::class) // Suppress experimental API warning
@Composable
fun Search(navController: NavController,viewModel: SearchViewModel = viewModel()) {
    var query by remember { mutableStateOf(TextFieldValue("")) }
    val searchResults by viewModel.searchResults.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(2.dp)) {
        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
                if (it.text.isNotBlank()) {
                    viewModel.searchMeals(it.text)
                }
            },
            placeholder = { Text("Search Meals", fontSize = 12.sp, color = Color.Gray) },
            modifier = Modifier.fillMaxWidth()
                .height(48.dp) // Slimmer height for the search bar
                .clip(RoundedCornerShape(24.dp)) // Rounded corners
                .background(Color(0xFFF5F5F5)), // Light gray background similar to the image
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color.Transparent, // Use the background modifier color
                focusedBorderColor = Color.Transparent, // Remove focused border
                unfocusedBorderColor = Color.Transparent, // Remove unfocused border
                cursorColor = MaterialTheme.colorScheme.primary, // Primary color for the cursor

            ),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    if (query.text.isNotBlank()) {
                        viewModel.searchMeals(query.text)
                    }
                }
            ),
            leadingIcon = {
                Icon(
                imageVector = Icons.Default.Search, // Add the search icon
                contentDescription = "Search Icon",
                tint = Color.Gray // Match the icon color
                )
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (searchResults == null) {
            Text("No results found.", style = MaterialTheme.typography.bodyMedium)
        } else {
            LazyColumn {
                items(searchResults ?: emptyList()) { meal ->
                    MealItem(meal,navController)
                }
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
                navController.navigate(
                    "search_meal_detail/${meal.idMeal}"
                )
            }
    ) {
        Image(
            painter = rememberImagePainter(data = meal.strMealThumb),
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(meal.strMeal, style = MaterialTheme.typography.bodyMedium)
    }
}
