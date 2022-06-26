package ru.netology.myapplication.adapter

import ru.netology.myapplication.dto.Step

interface StepEditInteractionListener {
    fun onStepTextEdit(step: Step)
    fun onStepDeleteClicked(step: Step)
    fun onStepUpClicked(step: Step)
    fun onStepDownClicked(step: Step)
}