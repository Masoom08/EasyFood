package st.masoom.easyfood.Retrofit

import st.masoom.easyfood.Pojo.CategoryResponse
import st.masoom.easyfood.Pojo.Meal
import st.masoom.easyfood.Pojo.MealListResponse
import st.masoom.easyfood.Pojo.PopularMealsResponse
import st.masoom.easyfood.Retrofit.RetrofitInstance

class MealRepository {
    suspend fun fetchRandomMeal(): List<Meal>? {
        return RetrofitInstance.api.getRandomMeal().meals
    }

    suspend fun fetchPopularMeals(category: String): PopularMealsResponse {
        return RetrofitInstance.api.getPopularMeals(category)
    }

    suspend fun fetchCategories(): CategoryResponse {
        return RetrofitInstance.api.getCategories()
    }


}