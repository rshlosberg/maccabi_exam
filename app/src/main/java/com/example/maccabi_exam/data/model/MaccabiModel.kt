package com.example.maccabi_exam.data.model

data class MaccabiModel(
    val products: List<ProductModel>,
    val total: Int,
    val skip: Int,
    val limit: Int
)
