package com.nasywa.freshfusion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nasywa.freshfusion.data.UserRepository
import com.nasywa.freshfusion.freshfusionapp.detail.DetailViewModel
import com.nasywa.freshfusion.freshfusionapp.home.HomeViewModel
import com.nasywa.freshfusion.freshfusionapp.favorite.FavoriteViewModel

class ViewModelFactory(private val repository: UserRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}