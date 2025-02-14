package st.masoom.easyfood.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import st.masoom.easyfood.Pojo.Meal
import st.masoom.easyfood.Retrofit.RetrofitInstance

class PopularMealViewModel : ViewModel() {
    private val _popularMeals = MutableStateFlow<List<Meal>?>(null)
    val popularMeals: StateFlow<List<Meal>?> = _popularMeals

    init {
        getPopularMeals("Chicken")
    }


    fun getPopularMeals(category: String) {
        // Assuming you have a Retrofit service already set up
        viewModelScope.launch {
            try {
                val response =RetrofitInstance.api.getPopularMeals(category)
                _popularMeals.value = response.meals

            } catch (e: Exception) {
                _popularMeals.value = null
            }
        }
    }
}
