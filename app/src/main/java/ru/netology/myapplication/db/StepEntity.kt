package ru.netology.myapplication.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "steps")
class StepEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "stepId")
    val stepId:Long,
    @ColumnInfo(name = "recipeIdStep")
    val recipeIdStep: Long,
    @ColumnInfo(name = "stepOrder")
    val stepOrder: Long,
    @ColumnInfo(name = "stepText")
    val stepText: String,
    @ColumnInfo(name = "stepImage")
    val stepImage:String?
)