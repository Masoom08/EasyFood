package st.masoom.easyfood.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import st.masoom.easyfood.Local.MealDatabase
import st.masoom.easyfood.Local.MealEntity
import st.masoom.easyfood.Local.MealRepository

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MealRepository
    val allMeals: StateFlow<List<MealEntity>>

    init {
        val mealDao = MealDatabase.getDatabase(application).mealDao()
        repository = MealRepository(mealDao)
        // Convert Flow to LiveData using asLiveData()
        allMeals = repository.allMeals.stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            emptyList()
        )
    }

    fun insert(meal: MealEntity) = viewModelScope.launch {
        repository.insert(meal)
    }

    fun delete(meal: MealEntity) = viewModelScope.launch {
        repository.delete(meal)
    }

    suspend fun isFavorite(mealId: String): Boolean {
        return repository.getMealById(mealId) != null
    }
}
