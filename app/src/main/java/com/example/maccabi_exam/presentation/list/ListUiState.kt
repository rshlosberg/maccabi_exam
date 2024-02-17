package com.example.maccabi_exam.presentation.list

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

// States of the list screen.
enum class ListUiStateType { empty, loading, success, error }

// An abstract class of all the states.
open class ListUiState(val stateType: ListUiStateType = ListUiStateType.empty)

class ListUiLoadingState : ListUiState(stateType = ListUiStateType.loading)

class ListUiSuccessState(val categories: List<CategoryUiEntity> = emptyList()) :
    ListUiState(stateType = ListUiStateType.success)

class ListUiErrorState(val errorMessage: String = "") :
    ListUiState(stateType = ListUiStateType.error)

data class CategoryUiEntity(
    val categoryName: String,
    val firstProductThumbnail: String,
    val totalNumberOfDistinctProducts: String,
    val totalSumOfAllProductsInStock: String,
    val products: List<ProductUiEntity>
)

// An object to hold required product details to send
@Parcelize
data class ProductUiEntity(
    val productName: String,
    val productImage: String,
    val productPrice: String,
    val availableStockQuantity: String
) : Parcelable

// Move to details screen action params
data class MoveToDetailsScreenParams(
    val categoryName: String,
    val products: List<ProductUiEntity>
)