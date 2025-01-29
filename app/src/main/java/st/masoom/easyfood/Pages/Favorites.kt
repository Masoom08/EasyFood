package st.masoom.easyfood

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun Favorite() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "Favorite")
    }
}