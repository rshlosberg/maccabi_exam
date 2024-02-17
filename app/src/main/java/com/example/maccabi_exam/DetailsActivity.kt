package com.example.maccabi_exam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.IntentCompat
import com.example.maccabi_exam.presentation.details.DetailsScreen
import com.example.maccabi_exam.presentation.list.ProductUiEntity

/**
 * This is the second activity which presents the category details: name and products.
 */
class DetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
            ) {
                // Grab intent data and send it to the details screen.
                val categoryName = intent.getStringExtra("categoryName")
                val products = IntentCompat.getParcelableArrayListExtra(
                    intent,
                    "products",
                    ProductUiEntity::class.java
                )

                // If everything is fine, then show the details screen.
                if (categoryName != null && products != null) {
                    DetailsScreen(categoryName = categoryName, productList = products)
                }
            }
        }
    }
}