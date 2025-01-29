package st.masoom.easyfood.Pages

import BottomNavigationBar
//import GlideImage
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.ImageView
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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

@SuppressLint("SuspiciousIndentation")
@Composable
fun Meal(
    mealId: String?,
    mealImageUrl: String?, // URL for the meal image
    mealName: String?, // Meal name
    mealInstructions: String?, // Recipe instructions
    youtubeLink: String? // YouTube video link
) {
    val context = LocalContext.current
        Box(modifier = Modifier.fillMaxSize()) {
            // Scrollable content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 64.dp) // Space for the fixed button
            ) {
                Spacer(modifier = Modifier.height(200.dp)) // Space for the fixed image

                // Meal Name
                mealName?.let {
                    Text(
                        text = it,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                // Recipe Instructions
                mealInstructions?.let {
                    Text(
                        text = "Recipe:",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                    )
                    Text(
                        text = it,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
            }

            // Fixed Meal Image
            mealImageUrl?.let {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .align(Alignment.TopCenter),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    GlideImage(
                        imageUrl = it,
                        contentDescription = "Meal Image",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            // Fixed YouTube Button
            youtubeLink?.let {
                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .align(Alignment.BottomCenter)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF0000) // YouTube red color
                    )
                ) {
                    Text(
                        text = "Watch on YouTube",
                        fontSize = 18.sp,
                        color = Color.White // White text on red button
                    )
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
    // Use AndroidView to integrate ImageView
    Box(
        modifier = modifier
            .border(1.dp, Color.Gray) // Adding a border while image is loading
            .padding(0.dp) // Padding can be adjusted to control border spacing
    ) {
        AndroidView(
            factory = { context: Context ->
                ImageView(context).apply {
                    // Set default image or placeholder if needed
                    setImageResource(R.drawable.food) // Placeholder resource
                }
            },
            update = { imageView: ImageView ->
                // Load image with Glide
                Glide.with(imageView.context)
                    .load(imageUrl)
                    .apply(RequestOptions().centerCrop()) // Optionally apply any Glide options here
                    .into(imageView)

                imageView.contentDescription = contentDescription // Set content description
            },
            modifier = modifier
        )
    }
}