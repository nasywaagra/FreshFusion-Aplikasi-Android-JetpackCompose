package com.nasywa.freshfusion.freshfusionapp.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nasywa.freshfusion.data.UserRepository
import com.nasywa.freshfusion.model.Fusion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.nasywa.freshfusion.data.Result

class DetailViewModel(
    private val repository: UserRepository
) : ViewModel() {
    private val _result: MutableStateFlow<Result<Fusion>> =
        MutableStateFlow(Result.Loading)
    val result: StateFlow<Result<Fusion>>
        get() = _result

    fun getFusionById(fusionId: Long) {
        viewModelScope.launch {
            _result.value = Result.Loading
            _result.value = Result.Success(repository.getFusionById(fusionId))
        }
    }

}