package st.masoom.easyfood

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import st.masoom.easyfood.Navigation.MainScreen
import st.masoom.easyfood.ui.theme.EasyFoodTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EasyFoodTheme {
                MainScreen()
            }
        }
    }
}