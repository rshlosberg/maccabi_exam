package com.example.maccabi_exam.domain.repository

import com.example.maccabi_exam.domain.entity.MaccabiProductsEntity

interface MaccabiRepository {
    suspend fun getMaccabiResponse() : MaccabiProductsEntity
}