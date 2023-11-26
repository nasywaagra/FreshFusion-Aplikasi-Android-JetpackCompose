package com.nasywa.freshfusion.model

data class Fusion(
    val id: Long,
    val name: String,
    val photo: String,
    val desc: String,
    val materials: String,
    val steps: String,
    val isFavorite: Boolean = false,
)


