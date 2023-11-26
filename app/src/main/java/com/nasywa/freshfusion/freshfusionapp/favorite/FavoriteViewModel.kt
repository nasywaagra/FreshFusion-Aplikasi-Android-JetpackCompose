package com.nasywa.freshfusion.freshfusionapp.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nasywa.freshfusion.data.UserRepository
import com.nasywa.freshfusion.model.Fusion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import com.nasywa.freshfusion.data.Result

class FavoriteViewModel(
    private val repository: UserRepository
) : ViewModel() {
    private val _result: MutableStateFlow<Result<List<Fusion>>> = MutableStateFlow(
        Result.Loading
    )

    val result: StateFlow<Result<List<Fusion>>>
        get() = _result

    fun getSavedFusion() {
        viewModelScope.launch {
            repository.getSavedFusion()
                .catch {
                    _result.value = Result.Error(it.message.toString())
                }
                .collect { fusion ->
                    _result.value = Result.Success(fusion)

                }
        }
    }

    fun saveFusion(fusionId: Long) {
        viewModelScope.launch {
            repository.updateSavedFusion(fusionId)
        }
    }
    fun observeSavedFusionChanges(listener: () -> Unit) {
        repository.observeSavedFusionChanges(listener)
    }
}