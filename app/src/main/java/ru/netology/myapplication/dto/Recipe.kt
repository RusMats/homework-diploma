package ru.netology.myapplication.dto

data class Recipe(
    val recipeId: Long,
    val author: String,
    val title: String,
    /**
     * Доступные категории кухни:
     * Европейская
     * Азиатская
     * Паназиатская
     * Восточная
     * Американская
     * Русская
     * Средиземноморская
     */
    val category: String,
    var likedByMe: Boolean = false
){
    companion object{
        lateinit var content: ArrayList<Step>
    }
}