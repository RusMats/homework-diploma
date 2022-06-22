package ru.netology.myapplication.adapter

import ru.netology.myapplication.dto.Step

interface StepEditInteractionListener {
    fun onDeleteClicked(step: Step)
    fun onUpClicked(step: Step)
    fun onDownClicked(step: Step)
}