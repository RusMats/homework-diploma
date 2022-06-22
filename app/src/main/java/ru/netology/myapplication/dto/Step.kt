package ru.netology.myapplication.dto


data class Step(
    val stepId: Long,
    val recipeIdStep: Long,
    val stepOrder: Long,
    val stepText: String,
    val stepImage: String? = null,
)