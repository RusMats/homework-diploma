package ru.netology.myapplication.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.netology.myapplication.dto.Step

@Dao
interface StepsDao {
    @Query("SELECT*FROM steps ORDER BY stepOrder DESC")
    fun getSteps(): LiveData<List<StepEntity>>

    @Query("SELECT*FROM steps WHERE recipeIdStep = :recipeId ORDER BY stepOrder DESC")
    fun getStepsByRecipeId(recipeId: Long): List<StepEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(step: StepEntity)

    @Query(
        """UPDATE steps SET  
        stepOrder = :stepOrder, 
        stepText = :stepText,
        stepImage = :stepImage
        WHERE stepId = :stepId
    """
    )
    fun updateById(stepId:Long, stepOrder: Int, stepText: String, stepImage: String?)

    @Query("SELECT*FROM steps WHERE stepId = :stepId")
    fun getStepById(stepId: Long): Step?

    @Query("DELETE FROM steps WHERE stepId = :stepId")
    fun deleteById(stepId: Long)

    @Query("DELETE FROM steps WHERE recipeIdStep = :recipeId")
    fun deleteByRecipeId(recipeId: Long)

}