package st.masoom.easyfood.Retrofit

import retrofit2.http.GET
import retrofit2.http.Query
import st.masoom.easyfood.Pojo.CategoryResponse
import st.masoom.easyfood.Pojo.MealListResponse
import st.masoom.easyfood.Pojo.MealResponse
import st.masoom.easyfood.Pojo.PopularMealsResponse

interface MealApi {
    @GET("random.php")
    suspend fun getRandomMeal(): MealResponse

    @GET("filter.php") // Example category for popular items
    suspend fun getPopularMeals(@Query("c") category: String): PopularMealsResponse  // Category parameter for popular meals

    @GET("categories.php")
    suspend fun getCategories(): CategoryResponse

    @GET("search.php")
    suspend fun searchMeals(@Query("s") query: String): MealResponse

    @GET("lookup.php")
    suspend fun getMealDetails(@Query("i") mealId: String): MealResponse

}