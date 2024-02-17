@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.maccabi_exam.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import coil.compose.AsyncImage
import com.example.maccabi_exam.data.repository.MaccabiRepositoryImpl
import com.example.maccabi_exam.domain.usecase.MaccabiProductsUsecaseImpl
import com.example.maccabi_exam.presentation.utils.shimmerBrush

/**
 * The list composable object.
 */
@Composable
fun ListScreen(
    listViewModel: ListViewModel = ListViewModel(
        useCase = MaccabiProductsUsecaseImpl(
            maccabiRepository = MaccabiRepositoryImpl()
        )
    ),
    lifecycleOwner: LifecycleOwner?,
    moveToDetailsScreenCallback: ((MoveToDetailsScreenParams) -> Unit)?
) {
    val listUiState by listViewModel.uiState.collectAsState()

    if (lifecycleOwner != null && moveToDetailsScreenCallback != null) {
        listViewModel.moveToDetailsScreenRequest.observe(lifecycleOwner) { data ->
            moveToDetailsScreenCallback(data)
        }
    }

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(text = "Maccabi exam", style = MaterialTheme.typography.titleLarge)
            }
        )
    }) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding),
        ) {
            when (listUiState.stateType) {
                ListUiStateType.empty ->
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        Box {
                            CircularProgressIndicator()
                        }
                    }

                ListUiStateType.loading -> Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    CircularProgressIndicator()
                    Text(text = "Please wait")
                }

                ListUiStateType.success -> AllCategories(
                    categoryList = (listUiState as ListUiSuccessState).categories,
                    listViewModel = listViewModel
                )

                ListUiStateType.error -> Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                ) {
                    Text(text = (listUiState as ListUiErrorState).errorMessage)
                }
            }
        }
    }
}

@Composable
fun AllCategories(categoryList: List<CategoryUiEntity>, listViewModel: ListViewModel) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(categoryList) { category ->
            CategoryCard(
                categoryUiEntity = category,
                listViewModel = listViewModel
            )
        }
    }
}

@Composable
fun CategoryCard(
    categoryUiEntity: CategoryUiEntity,
    listViewModel: ListViewModel
) {
    Card(
        modifier = Modifier.padding(10.dp),
        shape = MaterialTheme.shapes.medium,
        onClick = {
            listViewModel.onCardClicked(categoryUiEntity = categoryUiEntity)
        }
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
                    model = categoryUiEntity.firstProductThumbnail,
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
                    text = "Category name: ${categoryUiEntity.categoryName}",
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = "Total number of distinct products: ${categoryUiEntity.totalNumberOfDistinctProducts}",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = "Total sum of all products in stock: ${categoryUiEntity.totalSumOfAllProductsInStock}",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListScreenPreview() {
    ListScreen(lifecycleOwner = null, moveToDetailsScreenCallback = null)
}