package ru.netology.myapplication.db

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.netology.myapplication.dto.Recipe

@Dao
interface RecipeDao {
    @Query("SELECT*FROM recipes ORDER BY recipeId DESC")
    fun getRecipes(): LiveData<List<RecipeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipe: RecipeEntity):Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipeWithSteps(recipe: RecipeEntity, steps: List<StepEntity>)

    @Query(
        """UPDATE recipes SET  
        author = :author, 
        title = :title,
        category = :category
        WHERE recipeId = :recipeId
    """
    )
    fun updateById(recipeId: Long, title: String, author: String, category: String)

    @Query("SELECT*FROM recipes WHERE recipeId = :recipeId")
    fun getRecipeById(recipeId: Long): Recipe?

    @Query(
        """
        UPDATE recipes SET likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END 
        WHERE recipeId = :recipeId
        """
    )
    fun likeById(recipeId: Long)

    @Query("DELETE FROM recipes WHERE recipeId = :recipeId")
    fun deleteById(recipeId: Long)

    @Transaction
    @Query("SELECT * FROM recipes")
    fun getRecipeWithSteps(): List<RecipeWithSteps>
}