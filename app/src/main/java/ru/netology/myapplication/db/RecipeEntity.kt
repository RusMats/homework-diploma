package ru.netology.myapplication.db

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.myapplication.dto.Step

@Entity(tableName = "recipes")
class RecipeEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "recipeId")
    val recipeId: Long,
    @ColumnInfo(name = "author")
    val author: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "category")
    val category: String,
    @ColumnInfo(name = "likedByMe")
    val likedByMe: Boolean = false
)