package st.masoom.easyfood.Local

import kotlinx.coroutines.flow.Flow

class MealRepository(private val mealDao: MealDao) {

    val allMeals: Flow<List<MealEntity>> = mealDao.getAllMeals()

    suspend fun insert(meal: MealEntity) {
        mealDao.insertMeal(meal)
    }

    suspend fun delete(meal: MealEntity) {
        mealDao.deleteMeal(meal)
    }

    suspend fun getMealById(id: String): MealEntity? {
        return mealDao.getMealById(id)
    }

    suspend fun isMealFavorite(mealId: String): Boolean {
        return mealDao.isMealFavorite(mealId)
    }
}

