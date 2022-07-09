package ru.netology.myapplication.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import ru.netology.myapplication.db.RecipeDao
import ru.netology.myapplication.db.toEntity
import ru.netology.myapplication.db.toModel
import ru.netology.myapplication.dto.Recipe

class RecipeRepositoryImpl(
    private val dao: RecipeDao
):RecipeRepository {

    override val recipeData = dao.getRecipes().map { entities ->
        entities.map { it.toModel() }
    }

    override fun getRecipeById(recipeId: Long) =
        dao.getRecipeById(recipeId)

    override fun getRecipes(): LiveData<List<Recipe>> = recipeData

    override fun save(recipe: Recipe) =
        if (recipe.recipeId == RecipeRepository.NEW_RECIPE_ID) dao.insert(recipe.toEntity())
        else {
            dao.updateById(recipe.recipeId, recipe.title, recipe.author, recipe.category)
            recipe.recipeId
    }

    override fun like(recipeId: Long) {
        dao.likeById(recipeId)
    }

    override fun delete(recipeId: Long) {
        dao.deleteById(recipeId)
    }

}