package com.example.maccabi_exam.domain.entity

data class MaccabiProductsEntity(
    val products: List<ProductEntity>,
    val total: Int,
    val skip: Int,
    val limit: Int
)
