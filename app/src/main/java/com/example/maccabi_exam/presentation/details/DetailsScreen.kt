@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.maccabi_exam.presentation.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.maccabi_exam.presentation.list.ProductUiEntity
import com.example.maccabi_exam.presentation.utils.shimmerBrush

/**
 * The details composable object.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(categoryName: String, productList: List<ProductUiEntity>) {
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "$categoryName details",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        )
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
        ) {
            AllProducts(productList = productList)
        }
    }
}

@Composable
fun AllProducts(productList: List<ProductUiEntity>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(productList) { product ->
            ProductCard(
                productName = product.productName,
                productImage = product.productImage,
                productPrice = product.productPrice,
                availableStockQuantity = product.availableStockQuantity,
            )
        }
    }
}

@Composable
fun ProductCard(
    productName: String,
    productImage: String,
    productPrice: String,
    availableStockQuantity: String
) {
    Card(
        modifier = Modifier.padding(10.dp),
        shape = MaterialTheme.shapes.medium,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                val showShimmer = remember { mutableStateOf(true) }
                AsyncImage(
                    model = productImage,
                    contentDescription = "First product thumbnail",
                    modifier = Modifier
                        .background(
                            shimmerBrush(
                                targetValue = 1300f,
                                showShimmer = showShimmer.value
                            )
                        )
                        .fillMaxWidth()
                        .heightIn(min = 220.dp)
                        .align(Alignment.CenterHorizontally),
                    onSuccess = { showShimmer.value = false },
                    contentScale = ContentScale.Fit
                )
                Text(
                    text = "Product name: $productName",
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    text = "Product price: $productPrice",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = "Available stock quantity: $availableStockQuantity",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
    DetailsScreen(categoryName = "No category name", productList = emptyList())
}