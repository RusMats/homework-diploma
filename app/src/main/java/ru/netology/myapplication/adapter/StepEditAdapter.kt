package ru.netology.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.myapplication.databinding.RecipeStepEditBinding
import ru.netology.myapplication.dto.Step


class StepEditAdapter(
    private val interactionsListener: StepEditInteractionListener
) : ListAdapter<Step, StepEditAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecipeStepEditBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, interactionsListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: RecipeStepEditBinding,
        listener: StepEditInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var step: Step

        init {
            binding.fabDeleteStep.setOnClickListener { listener.onStepDeleteClicked(step) }
            binding.imageButtonUp.setOnClickListener { listener.onStepUpClicked(step) }
            binding.imageButtonDown.setOnClickListener { listener.onStepDownClicked(step) }
//            binding.fabAddImage.setOnClickListener { listener.onAddClicked() } TODO: stepImage
        }

        fun bind(step: Step) {
            this.step = step
            with(binding) {
                stepText.setText(step.stepText)
//                stepImage TODO: stepImage
            }
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<Step>() {

        override fun areItemsTheSame(oldItem: Step, newItem: Step) =
            oldItem.recipeIdStep == newItem.recipeIdStep

        override fun areContentsTheSame(oldItem: Step, newItem: Step) = oldItem == newItem

    }
}