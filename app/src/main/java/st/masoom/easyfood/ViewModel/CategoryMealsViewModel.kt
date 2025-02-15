package st.masoom.easyfood.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import st.masoom.easyfood.Pojo.Meal
import st.masoom.easyfood.Retrofit.MealRepository

class CategoryMealsViewModel : ViewModel() {
    private val _categoryMeals = MutableStateFlow<List<Meal>>(emptyList())
    val categoryMeals: StateFlow<List<Meal>> = _categoryMeals

    private val repository = MealRepository() // ✅ Create an instance

    fun getMealsByCategory(category: String) {
        viewModelScope.launch {
            val response = repository.getMealsByCategory(category)
            Log.d("CategoryMealsViewModel", "Fetched meals: $response") // ✅ Log meals
            _categoryMeals.value = response ?: emptyList()
        }
    }
}