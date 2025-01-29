package st.masoom.easyfood.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import st.masoom.easyfood.Pojo.Meal
import st.masoom.easyfood.Retrofit.RetrofitInstance

class SearchViewModel : ViewModel() {
    private val _searchResults = MutableStateFlow<List<Meal>?>(null)
    val searchResults: StateFlow<List<Meal>?> = _searchResults

    fun searchMeals(query: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.searchMeals(query)
                _searchResults.value = response.meals
            } catch (e: Exception) {
                _searchResults.value = null
            }
        }
    }
}
