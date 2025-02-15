package st.masoom.easyfood

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import st.masoom.easyfood.Local.MealEntity
import st.masoom.easyfood.ViewModel.FavoritesViewModel
import st.masoom.easyfood.databinding.ActivityMealDetailBinding

class MealDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMealDetailBinding
    private val viewModel: FavoritesViewModel by viewModels()  // Using ViewModel correctly

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve meal data from intent
        val mealId = intent.getStringExtra("MEAL_ID") ?: ""
        val mealName = intent.getStringExtra("MEAL_NAME") ?: ""
        val mealThumb = intent.getStringExtra("MEAL_THUMB") ?: ""
        val mealInstructions = intent.getStringExtra("MEAL_INSTRUCTIONS") ?: ""
        val youtubeLink = intent.getStringExtra("YOUTUBE_LINK") ?: ""

        val mealEntity = MealEntity(mealId, mealName, mealThumb, mealInstructions, youtubeLink)

        // Check if the meal is a favorite and update UI
        lifecycleScope.launch {
            if (viewModel.isFavorite(mealId)) {
                binding.btnSave.text = "Saved"
            } else {
                binding.btnSave.text = "Save"
            }
        }

        // Save/Delete favorite meal on button click
        binding.btnSave.setOnClickListener {
            lifecycleScope.launch {
                if (viewModel.isFavorite(mealId)) {
                    viewModel.delete(mealEntity)
                    binding.btnSave.text = "Save"
                } else {
                    viewModel.insert(mealEntity)
                    binding.btnSave.text = "Saved"
                }
            }
        }
    }
}
