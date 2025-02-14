package st.masoom.easyfood.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import st.masoom.easyfood.Pojo.Meal
import st.masoom.easyfood.Retrofit.RetrofitInstance

class RandomViewModel : ViewModel(), MealDetailProvider {
    private val _randomMeal = MutableStateFlow<Meal?>(null)
    val randomMeal: StateFlow<Meal?> get() = _randomMeal

    private val _mealDetails = MutableStateFlow<Meal?>(null)
    override val mealDetailState: StateFlow<Meal?> get() = _mealDetails

    init {
        fetchRandomMeal()
    }

    private fun fetchRandomMeal() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getRandomMeal()
                _randomMeal.value = response.meals?.firstOrNull() ?: null
            } catch (e: Exception) {
                _randomMeal.value = null
            }
        }
    }
    override fun fetchMealDetails(mealId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.getMealDetails(mealId)
                _mealDetails.value = response.meals?.firstOrNull()
            } catch (e: Exception) {
                _mealDetails.value = null
            }
        }
    }
}