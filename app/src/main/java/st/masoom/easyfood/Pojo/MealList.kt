package st.masoom.easyfood.Pojo

data class MealListResponse(
    val meals: List<Meal>
)

data class CategoryResponse(
    val categories: List<Category>
)

data class MealResponse(
    val meals: List<Meal>
)

data class PopularMealsResponse(
    val meals: List<Meal>
)