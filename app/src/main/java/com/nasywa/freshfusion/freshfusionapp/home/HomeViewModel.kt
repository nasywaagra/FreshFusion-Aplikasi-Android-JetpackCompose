package com.nasywa.freshfusion.freshfusionapp.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nasywa.freshfusion.data.UserRepository
import com.nasywa.freshfusion.model.Fusion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import com.nasywa.freshfusion.data.Result

class HomeViewModel(
    private val repository: UserRepository
) : ViewModel() {

    private val _result: MutableStateFlow<Result<List<Fusion>>> =
        MutableStateFlow(Result.Loading)

    val result: StateFlow<Result<List<Fusion>>>
        get() = _result

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun getAllFusion() {
        viewModelScope.launch {
            repository.getFusion()
                .catch {
                    _result.value = Result.Error(it.message.toString())
                }
                .collect { recipes ->
                    _result.value = Result.Success(recipes)

                }
        }
    }

    fun getSearchFusion(newQuery: String) {
        viewModelScope.launch {
            _query.value = newQuery
            repository.searchFusion(_query.value)
                .catch {
                    _result.value = Result.Error(it.message.toString())
                }
                .collect { recipes ->
                    _result.value = Result.Success(recipes)
                }
        }
    }

    fun saveFusion(fusionId: Long) {
        viewModelScope.launch {
            repository.updateSavedFusion(fusionId)
        }
    }
}