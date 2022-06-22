package ru.netology.myapplication.db

import ru.netology.myapplication.dto.Step

internal fun StepEntity.toModel() = Step(
    stepId = stepId,
    recipeIdStep = recipeIdStep,
    stepOrder = stepOrder,
    stepText = stepText,
    stepImage = stepImage
)

internal fun Step.toEntity() = StepEntity(
    stepId = stepId,
    recipeIdStep = recipeIdStep,
    stepOrder = stepOrder,
    stepText = stepText,
    stepImage = stepImage
)
