package ru.netology.myapplication.adapter

import ru.netology.myapplication.dto.Recipe

interface RecipeInteractionsListener {
    fun onLikeClicked(recipe: Recipe)
    fun onDeleteClicked(recipe: Recipe)
    fun onEditClicked(recipe: Recipe)
    fun onRecipeClicked(recipe: Recipe)
    fun onFilterClicked(recipes:List<Recipe>, categories:List<String>):List<Recipe>
}
