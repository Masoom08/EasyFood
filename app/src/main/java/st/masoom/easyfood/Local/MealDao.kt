package st.masoom.easyfood.Local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: MealEntity)

    @Delete
    suspend fun deleteMeal(meal: MealEntity)

    @Query("SELECT * FROM favorites WHERE mealId = :mealId LIMIT 1")
    suspend fun getMealById(mealId: String): MealEntity?

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE mealId = :mealId)")
    suspend fun isMealFavorite(mealId: String): Boolean

    @Query("SELECT * FROM favorites")
    fun getAllMeals(): Flow<List<MealEntity>>
}

