package ru.netology.myapplication.use_case

import ru.netology.myapplication.data.RecipeRepository

class GetRecipes(
    private val repository: RecipeRepository
) {
//    operator fun invoke(
////        recipeOrder: RecipeOrder = RecipeOrder.Favorie()
//    ): LiveData<List<Recipe>> {
//        return repository.getRecipes().map{
//            it.sortedBy { it.likedByMe == true }
//        }
//    }
}