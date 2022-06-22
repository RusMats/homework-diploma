package ru.netology.myapplication.db

import androidx.room.Embedded
import androidx.room.Relation
import ru.netology.myapplication.dto.Recipe
import ru.netology.myapplication.dto.Step

data class RecipeWithSteps(
    @Embedded val recipe: RecipeEntity,
    @Relation(
        parentColumn = "recipeId",
        entityColumn = "recipeIdStep"
    )
    val steps: List<StepEntity>
)
