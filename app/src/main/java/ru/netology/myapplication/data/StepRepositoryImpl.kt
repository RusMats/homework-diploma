package ru.netology.myapplication.data

import androidx.lifecycle.map
import ru.netology.myapplication.db.StepsDao
import ru.netology.myapplication.db.toEntity
import ru.netology.myapplication.db.toModel
import ru.netology.myapplication.dto.Step

class StepRepositoryImpl(
    private val dao: StepsDao
):StepRepository {

    override val stepsData = dao.getSteps().map { entities ->
        entities.map { it.toModel() }
    }

    override fun getStepsByRecipeId(recipeId: Long) =
    dao.getStepsByRecipeId(recipeId).map {  it.toModel() }

    override fun delete(stepId: Long) {
        dao.deleteById(stepId)
    }

    override fun save(steps: List<Step>) {
        steps.map { step->
                if (step.stepId == StepRepository.NEW_STEP_ID) dao.insert(step.toEntity())
                else dao.updateById(step.stepId, step.stepOrder, step.stepText, step.stepImage)
        }
    }
}