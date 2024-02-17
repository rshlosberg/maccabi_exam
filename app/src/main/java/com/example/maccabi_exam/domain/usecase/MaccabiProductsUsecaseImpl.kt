package com.example.maccabi_exam.domain.usecase

import com.example.maccabi_exam.domain.entity.CategoryEntity
import com.example.maccabi_exam.domain.repository.MaccabiRepository

class MaccabiProductsUsecaseImpl(private val maccabiRepository: MaccabiRepository) :
    MaccabiProductsUsecase {
    // Create set of categories, based on the response from the repository.
    override suspend fun getUniqueMaccabiCategories(): Set<CategoryEntity> {
        val maccabiProductsEntity = maccabiRepository.getMaccabiResponse()
        val categories = mutableMapOf<String, CategoryEntity>()

        // Loop over the products and add the product to its category cell.
        for (maccabiProduct in maccabiProductsEntity.products) {
            if (categories.containsKey(maccabiProduct.category)) {
                categories[maccabiProduct.category]!!.products.add(maccabiProduct)
            } else {
                categories[maccabiProduct.category] = CategoryEntity(
                    categoryName = maccabiProduct.category,
                    products = mutableListOf(maccabiProduct)
                )
            }
        }

        return categories.values.toSet()
    }
}