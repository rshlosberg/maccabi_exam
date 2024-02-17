package com.example.maccabi_exam.data.repository

import com.example.maccabi_exam.data.data_source.ApiClient
import com.example.maccabi_exam.data.transformer.MaccabiTransformer
import com.example.maccabi_exam.domain.entity.MaccabiProductsEntity
import com.example.maccabi_exam.domain.repository.MaccabiRepository

class MaccabiRepositoryImpl : MaccabiRepository {
    private val apiService = ApiClient.apiService
    private val transformer = MaccabiTransformer

    override suspend fun getMaccabiResponse() : MaccabiProductsEntity {
        try {
            // Make API request/
            val response = apiService.fetchMaccabiModel()

            if (response.isSuccessful) {
                // Transform response data.
                return transformer.transformMaccabiModel(response.body()!!)
            } else {
                // Throw response error code.
                throw Exception("Failed to fetch data from API: ${response.code()}")
            }
        } catch (e: Exception) {
            // Throw error message.
            throw Exception("Error occurred: ${e.message}")
        }
    }
}