package ru.netology.myapplication.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.myapplication.adapter.RecipeInteractionsListener
import ru.netology.myapplication.adapter.StepEditInteractionListener
import ru.netology.myapplication.data.RecipeRepository
import ru.netology.myapplication.data.RecipeRepositoryImpl
import ru.netology.myapplication.data.StepRepository
import ru.netology.myapplication.data.StepRepositoryImpl
import ru.netology.myapplication.db.AppDatabase
import ru.netology.myapplication.dto.Recipe
import ru.netology.myapplication.dto.Step
import ru.netology.myapplication.util.PairMediatorLiveData
import ru.netology.myapplication.util.SingleLiveEvent

class MainViewModel(application: Application) : AndroidViewModel(application),
    RecipeInteractionsListener, StepEditInteractionListener {

    private val recipeRepository: RecipeRepository = RecipeRepositoryImpl(
        dao = AppDatabase.getInstance(
            context = application
        ).recipeDao
    )

    private val stepsRepository: StepRepository = StepRepositoryImpl(
        dao = AppDatabase.getInstance(
            context = application
        ).stepsDao
    )

    val recipeData by recipeRepository::recipeData
    val stepsData by stepsRepository::stepsData

//    private val _liveDataOne = MutableLiveData<List<Recipe>>()
//    val liveDataRecipes: LiveData<List<Recipe>> = _liveDataOne
//
//    private val _liveDataTwo = MutableLiveData<List<Step>>()
//    val liveDataSteps: LiveData<List<Step>> = _liveDataTwo

    val data = PairMediatorLiveData(recipeData, stepsData)

    val navigateToRecipeFragment = SingleLiveEvent<Long>()
    val navigateToRecipeEditFragment = SingleLiveEvent<Long>()
    private val currentRecipe = MutableLiveData<Recipe?>(null)
    private val currentStep = MutableLiveData<Step?>(null)

    fun getRecipeById(recipeId: Long) =
        recipeRepository.getRecipeById(recipeId)

    fun onSaveButtonClick(title: String, author: String, category: String) {
        if (title.isBlank() or author.isBlank()) return
        val newRecipe = currentRecipe.value?.copy(
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
        currentRecipe.value = null
    }

    fun saveSteps(steps: List<Step>) {
//        steps.forEach{ step ->
//
//        }
//        val newStep = currentStep.value?.copy(
//            recipeIdStep = recipeIdStep,
//            stepOrder = stepOrder,
//            stepText = stepText,
//            stepImage = null,
//        ) ?: Recipe(
//            recipeId = StepRepository.NEW_STEP_ID,
//            author = author,
//            title = title,
//            category = category
//        )
        stepsRepository.save(steps)
    }

    fun onAddClicked() {
        navigateToRecipeEditFragment.call()
    }

    override fun onRecipeClicked(recipe: Recipe) {
        navigateToRecipeFragment.value = recipe.recipeId
    }

    // region RecipeInteractionsListener
    override fun onLikeClicked(recipe: Recipe) {
        return recipeRepository.like(recipe.recipeId)
    }

    override fun onDeleteClicked(recipe: Recipe) {
        return recipeRepository.delete(recipe.recipeId)
    }

    override fun onEditClicked(recipe: Recipe) {
        currentRecipe.value = recipe
        navigateToRecipeEditFragment.value = recipe.recipeId
    }
    // endregion RecipeInteractionsListener

    // region StepsInteractionsListener
    fun getStepsByRecipeId(recipeId: Long) =
        stepsRepository.getStepsByRecipeId(recipeId)

    override fun onStepDeleteClicked(step: Step) {
        stepsRepository.delete(step.stepId)
    }

    override fun onStepUpClicked(step: Step) {
        TODO("Not yet implemented")
    }

    override fun onStepDownClicked(step: Step) {
        TODO("Not yet implemented")
    }
    // endregion StepsInteractionsListener
}