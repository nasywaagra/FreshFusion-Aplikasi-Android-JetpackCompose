package com.nasywa.freshfusion.data


import com.nasywa.freshfusion.model.Fusion
import com.nasywa.freshfusion.model.FusionData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class UserRepository {
    private val fusion = mutableListOf<Fusion>()

    init {
        if (fusion.isEmpty()) {
            FusionData.fusion.forEach {
                fusion.add(it)
            }
        }
    }

    fun getFusion(): Flow<List<Fusion>> {
        return flowOf(fusion)
    }

    fun searchFusion(query: String): Flow<List<Fusion>> {
        return flowOf(
            fusion.filter {
                it.name.contains(query, ignoreCase = true)
            }
        )
    }

    fun getFusionById(fusionId: Long): Fusion {
        return fusion.first {
            it.id == fusionId
        }
    }

    fun updateSavedFusion(fusionId: Long): Flow<Boolean> {
        val index = fusion.indexOfFirst { it.id == fusionId }
        val result = if (index >= 0) {
            fusion[index] = fusion[index].copy(isFavorite = !fusion[index].isFavorite)
            true
        } else {
            false
        }
        return flowOf(result).onEach { isFavorite ->
            if (isFavorite) {
                notifySavedFusionChanged()
            }
        }
    }
    private val savedFusionListeners = mutableListOf<() -> Unit>()

    fun observeSavedFusionChanges(listener: () -> Unit) {
        savedFusionListeners.add(listener)
    }

    private fun notifySavedFusionChanged() {
        savedFusionListeners.forEach { it.invoke() }
    }

    fun getSavedFusion(): Flow<List<Fusion>> {
        return getFusion()
            .map { fusion ->
                fusion.filter { theFusion ->
                    theFusion.isFavorite
                }
            }
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(): UserRepository =
            instance ?: synchronized(this) {
                UserRepository().apply {
                    instance = this
                }
            }
    }
}