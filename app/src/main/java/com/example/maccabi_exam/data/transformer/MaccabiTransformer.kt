package com.example.maccabi_exam.data.transformer

import com.example.maccabi_exam.data.model.MaccabiModel
import com.example.maccabi_exam.data.model.ProductModel
import com.example.maccabi_exam.domain.entity.MaccabiProductsEntity
import com.example.maccabi_exam.domain.entity.ProductEntity

class MaccabiTransformer {
    companion object {
        // Transform response model to entity.
        fun transformMaccabiModel(maccabiModel: MaccabiModel) : MaccabiProductsEntity =
            MaccabiProductsEntity(
                products = maccabiModel.products.map { transformProductModel(it) },
                total = maccabiModel.total,
                skip = maccabiModel.skip,
                limit = maccabiModel.limit)

        // Transform product model to entity.
        private fun transformProductModel(productModel: ProductModel) : ProductEntity =
            ProductEntity(
                _id = productModel._id,
                title = productModel.title,
                description = productModel.description,
                price = productModel.price,
                discountPercentage = productModel.discountPercentage,
                rating = productModel.rating,
                stock = productModel.stock,
                brand = productModel.brand,
                category = productModel.category,
                thumbnail = productModel.thumbnail,
                images = productModel.images)
    }
}