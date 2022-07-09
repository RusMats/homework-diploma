package ru.netology.myapplication.data

import androidx.lifecycle.LiveData
import ru.netology.myapplication.dto.Recipe

interface RecipeRepository {
    val recipeData: LiveData<List<Recipe>>

    fun getRecipeById(recipeId: Long):Recipe?

    fun like(recipeId: Long)

    fun delete(recipeId: Long)

    fun save(recipe: Recipe):Long

    fun getRecipes(): LiveData<List<Recipe>>

    companion object {
        const val NEW_RECIPE_ID = 0L
        const val OLD_RECIPE_ID = -1L
    }
}
