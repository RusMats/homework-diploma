package ru.netology.myapplication.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.myapplication.adapter.RecipeInteractionsListener
import ru.netology.myapplication.data.RecipeRepository
import ru.netology.myapplication.data.RecipeRepositoryImpl
import ru.netology.myapplication.db.AppDatabase
import ru.netology.myapplication.dto.Recipe
import ru.netology.myapplication.util.SingleLiveEvent

class RecipeViewModel(application: Application) : AndroidViewModel(application),
    RecipeInteractionsListener {

    private val recipeRepository: RecipeRepository = RecipeRepositoryImpl(
        dao = AppDatabase.getInstance(
            context = application
        ).recipeDao
    )

    val data by recipeRepository::recipeData

    val navigateToRecipeFragment = SingleLiveEvent<Long>()
    val navigateToRecipeEditFragment = SingleLiveEvent<Long>()
    private val currentPost = MutableLiveData<Recipe?>(null)

    fun getRecipeById(recipeId: Long) =
        recipeRepository.getRecipeById(recipeId)

    fun onSaveButtonClick(title: String, author: String, category: String) {
        if (title.isBlank() or author.isBlank()) return
        val newRecipe = currentPost.value?.copy(
            author = author,
            title = title,
            category = category
        ) ?: Recipe(
            recipeId = RecipeRepository.NEW_RECIPE_ID,
            author = author,
            title = title,
            category = category
        )
        recipeRepository.save(newRecipe)
        currentPost.value = null
    }

    fun onAddClicked() {
        navigateToRecipeEditFragment.call()
    }

    override fun onRecipeClicked(recipe: Recipe) {
        navigateToRecipeFragment.value = recipe.recipeId
    }

    // region PostInteractionsListener
    override fun onLikeClicked(recipe: Recipe) {
        return recipeRepository.like(recipe.recipeId)
    }

    override fun onDeleteClicked(recipe: Recipe) {
        return recipeRepository.delete(recipe.recipeId)
    }

    override fun onEditClicked(recipe: Recipe) {
        currentPost.value = recipe
        navigateToRecipeEditFragment.value = recipe.recipeId
    }
    // endregion PostInteractionsListener

}