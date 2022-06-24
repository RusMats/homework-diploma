package ru.netology.myapplication.data

import androidx.lifecycle.LiveData
import ru.netology.myapplication.dto.Step

interface StepRepository {
    val stepsData: LiveData<List<Step>>

    fun getStepsByRecipeId(recipeId: Long): LiveData<List<Step>>

    fun delete(stepId: Long)

    fun save(steps: List<Step>)

    companion object {
        const val NEW_STEP_ID = 0L
    }
}