package st.masoom.easyfood.ViewModel

import kotlinx.coroutines.flow.StateFlow
import st.masoom.easyfood.Pojo.Meal

interface MealDetailProvider {
    val mealDetailState: StateFlow<Meal?>
    fun fetchMealDetails(mealId: String)
}
