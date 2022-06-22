package ru.netology.myapplication.db

import ru.netology.myapplication.dto.Recipe

internal fun RecipeEntity.toModel() = Recipe(
    recipeId = recipeId,
    author = author,
    title = title,
    category = category,
    likedByMe = likedByMe
)

internal fun Recipe.toEntity() = RecipeEntity(
    recipeId = recipeId,
    author = author,
    title = title,
    category = category,
    likedByMe = likedByMe
)