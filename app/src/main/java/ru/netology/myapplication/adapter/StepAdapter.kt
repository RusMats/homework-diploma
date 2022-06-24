package ru.netology.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.netology.myapplication.databinding.RecipeStepBinding
import ru.netology.myapplication.dto.Step


class StepAdapter(private val steps: List<Step>) : RecyclerView
.Adapter<StepAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecipeStepBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(steps[position])
    }

    inner class ViewHolder(
        private val binding: RecipeStepBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var step: Step

        fun bind(step: Step) {
            this.step = step
            with(binding) {
                stepText.text = step.stepText
//                stepImage TODO: stepImage
            }
        }
    }

    override fun getItemCount() = steps.size
}