package com.example.maccabi_exam.domain.entity

data class CategoryEntity(
    val categoryName: String,
    val products: MutableList<ProductEntity>
)
