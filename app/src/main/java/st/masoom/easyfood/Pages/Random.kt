package st.masoom.easyfood.Pages

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.ImageView
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import st.masoom.easyfood.R

@Composable
fun Meal(
    mealId: String?,
    mealImageUrl: String?,
    mealName: String?,
    mealInstructions: String?,
    youtubeLink: String?
) {
    val context = LocalContext.current
    var isFavorite by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Meal Image + Floating Favorite Button
            item {
                Box(modifier = Modifier.fillMaxWidth()) {
/*
                    GlideImage(
                        imageUrl = mealImageUrl ?: "https://via.placeholder.com/400",
                        contentDescription = mealName ?: "Meal Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                    )



                    IconButton(
                        onClick = { isFavorite = !isFavorite },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp)
                            .size(48.dp)
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = if (isFavorite) Color.Red else Color.White
                        )
                    }

 */
                }
            }

            // Meal Name
            item {
                Text(
                    text = mealName ?: "Meal Name Not Available",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }

            // Recipe Instructions
            item {
                Text(
                    text = "📖 Instructions:",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = mealInstructions ?: "No instructions available.",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(16.dp)
                )
            }

            // YouTube Button (Only if link exists)
            item {
                youtubeLink?.let {
                    IconButton(
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                            context.startActivity(intent)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .size(50.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Watch on YouTube",
                            tint = Color.Red,
                            modifier = Modifier.size(50.dp)
                        )
                    }
                }
            }

        }
    }
}


@Composable
fun GlideImage(
    imageUrl: String,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    AndroidView(
        factory = { context: Context -> ImageView(context) },
        update = { imageView: ImageView ->
            Glide.with(imageView.context)
                .load(imageUrl)
                .apply(RequestOptions().centerCrop())
                .into(imageView)

            imageView.contentDescription = contentDescription
        },
        modifier = modifier.verticalScroll(rememberScrollState())
    )
}
