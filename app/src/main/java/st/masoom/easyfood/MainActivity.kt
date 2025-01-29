package st.masoom.easyfood

import MainScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import st.masoom.easyfood.ui.theme.EasyFoodTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EasyFoodTheme {
                MainScreen()
                /*Meal( "1174","https://www.pexels.com/search/food/","Tasty Food","Rinse and soak the black lentils and kidney beans in water overnight. Drain the water and pressure cook them with 3-4 cups of water, a pinch of salt, and turmeric for about 25–30 minutes until soft and creamy.\n" +
                        "\n" +
                        "Heat 2 tablespoons of butter in a deep pan. Add cumin seeds and let them splutter. Add the finely chopped onions and sauté until golden brown. Add the ginger-garlic paste and green chilies. Cook for 1–2 minutes until aromatic.\n" +
                        "\n" +
                        "Stir in turmeric, red chili powder, coriander powder, and garam masala. Mix well. Add the tomato puree and cook until the butter separates from the mixture.\n" +
                        "\n" +
                        "Add the cooked lentils and kidney beans along with their water to the gravy. Stir well, adjusting the consistency with more water if needed.\n" +
                        "\n" +
                        "Simmer the mixture on low heat for 25–30 minutes, stirring occasionally to prevent sticking.\n" +
                        "\n" +
                        "Add 1 tablespoon of butter, cream, and crushed kasuri methi. Stir well and let it cook for another 5 minutes.\n" +
                        "\n" +
                        "Garnish with fresh coriander leaves and an extra drizzle of cream or butter. Serve hot with naan, jeera rice, or paratha.\n" +
                        "\n","https://www.youtube.com/")
                        */

                //Search()
            }
        }
    }
}




