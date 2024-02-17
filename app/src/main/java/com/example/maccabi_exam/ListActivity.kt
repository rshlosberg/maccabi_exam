package com.example.maccabi_exam

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.maccabi_exam.presentation.list.ListScreen
import com.example.maccabi_exam.presentation.list.MoveToDetailsScreenParams

/**
 * This is the first activity which presents the category list.
 */
class ListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
            ) {
                ListScreen(lifecycleOwner = this, moveToDetailsScreenCallback = ::moveToDetailsPage)
            }
        }
    }

    /*
        Move to category details page, base on the params of category name and category products.
     */
    private fun moveToDetailsPage(moveToDetailsPageParams: MoveToDetailsScreenParams) {
        startActivity(Intent(this, DetailsActivity::class.java).apply {
            putExtra("categoryName", moveToDetailsPageParams.categoryName)
            putParcelableArrayListExtra("products", ArrayList(moveToDetailsPageParams.products))
        })
    }
}