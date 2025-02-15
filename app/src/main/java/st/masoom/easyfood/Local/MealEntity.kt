package st.masoom.easyfood.Local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class MealEntity(
    @PrimaryKey val mealId: String,
    val mealName: String,
    val mealThumb: String,
    val mealInstructions: String,
    val youtubeLink: String
)
