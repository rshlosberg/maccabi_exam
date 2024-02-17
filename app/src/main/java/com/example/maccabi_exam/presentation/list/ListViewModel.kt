package com.example.maccabi_exam.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maccabi_exam.domain.entity.CategoryEntity
import com.example.maccabi_exam.domain.usecase.MaccabiProductsUsecaseImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode

class ListViewModel(private val useCase: MaccabiProductsUsecaseImpl) : ViewModel() {

    // Reference to ui state.
    private val _uiState = MutableStateFlow(ListUiState())
    val uiState: StateFlow<ListUiState> = _uiState.asStateFlow()

    // Reference to observer of moving to details screen requests.
    private val _moveToDetailsScreenRequest = MutableLiveData<MoveToDetailsScreenParams>()
    val moveToDetailsScreenRequest: LiveData<MoveToDetailsScreenParams> =
        _moveToDetailsScreenRequest

    init {
        initList()
    }

    // Get the data.
    private fun initList() {
        viewModelScope.launch {
            try {
                // Change state to loading state.
                _uiState.value = ListUiLoadingState()
                // Get the actual data.
                val maccabiUniqueCategories = useCase.getUniqueMaccabiCategories()

                // Change state to success state.
                _uiState.value =
                    ListUiSuccessState(categories = maccabiUniqueCategories.sortedBy { it.categoryName }
                        .map { categoryEntity ->
                            CategoryUiEntity(
                                categoryName = categoryEntity.categoryName,
                                firstProductThumbnail = categoryEntity.products.first().thumbnail,
                                totalNumberOfDistinctProducts = getTotalNumberOfDistinctProducts(
                                    categoryEntity = categoryEntity
                                ).toString(),
                                totalSumOfAllProductsInStock = getTotalSumOfAllProductsInStock(
                                    categoryEntity = categoryEntity
                                ).toString(),
                                products = categoryEntity.products.map { productEntity ->
                                    ProductUiEntity(
                                        productName = productEntity.title,
                                        productImage = productEntity.thumbnail,
                                        productPrice = productEntity.price.toString(),
                                        availableStockQuantity = productEntity.stock.toString(),
                                    )
                                },
                            )
                        })
            } catch (e: Exception) {
                // Change state to error state.
                _uiState.value = ListUiErrorState(errorMessage = "Error occurred: ${e.message}")
            }
        }
    }

    // Handle card clicks.
    fun onCardClicked(categoryUiEntity: CategoryUiEntity) {
        _moveToDetailsScreenRequest.postValue(
            MoveToDetailsScreenParams(
                categoryName = categoryUiEntity.categoryName,
                products = categoryUiEntity.products
            )
        )
    }

    // Calculate total number of distinct products.
    private fun getTotalNumberOfDistinctProducts(categoryEntity: CategoryEntity): Int {
        return categoryEntity.products.distinctBy { it.title }.count()
    }

    // Calculate total sum of all products in stock.
    private fun getTotalSumOfAllProductsInStock(categoryEntity: CategoryEntity): Double {
        return BigDecimal(categoryEntity.products.sumOf { it.stock * it.price * ((100 - it.discountPercentage) / 100) }).setScale(
            2,
            RoundingMode.HALF_DOWN
        ).toDouble()
    }
}
