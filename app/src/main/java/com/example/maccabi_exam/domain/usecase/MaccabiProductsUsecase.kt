package com.example.maccabi_exam.domain.usecase

import com.example.maccabi_exam.domain.entity.CategoryEntity

interface MaccabiProductsUsecase {
    suspend fun getUniqueMaccabiCategories(): Set<CategoryEntity>
}