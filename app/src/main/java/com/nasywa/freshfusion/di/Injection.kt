package com.nasywa.freshfusion.di

import com.nasywa.freshfusion.data.UserRepository

object Injection {
    fun provideRepository(): UserRepository {
        return UserRepository.getInstance()
    }
}