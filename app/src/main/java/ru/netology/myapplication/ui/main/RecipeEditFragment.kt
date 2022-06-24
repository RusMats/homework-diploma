package ru.netology.myapplication.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.chip.Chip
import ru.netology.myapplication.adapter.RecipeAdapter
import ru.netology.myapplication.adapter.StepEditAdapter
import ru.netology.myapplication.databinding.RecipeEditFragmentBinding
import ru.netology.myapplication.dto.Step
import ru.netology.myapplication.view_model.MainViewModel


class RecipeEditFragment : Fragment() {
    private val viewModel by viewModels<MainViewModel>(ownerProducer = ::requireParentFragment)
    private val args by navArgs<RecipeEditFragmentArgs>()
    private lateinit var listSteps: MutableList<Step>

    private fun onCloseButtonClicked() {
        findNavController().popBackStack()
    }

    private fun onSaveButtonClicked(binding: RecipeEditFragmentBinding) {
        val title = binding.titleTextEdit.text
        val author = binding.authorTextEdit.text
        val category = binding.chipGroupEdit.findViewById<Chip>(binding.chipGroupEdit.checkedChipId)
            .tag.toString()
        if (!title.isNullOrBlank() || !author.isNullOrBlank() || !category.isNullOrBlank()) {
            val resultBundle = Bundle(3)
            resultBundle.putString(RESULT_TITLE, title.toString())
            resultBundle.putString(RESULT_AUTHOR, author.toString())
            resultBundle.putString(RESULT_CATEGORY, category)
            setFragmentResult(REQUEST_KEY, resultBundle)
        }
        viewModel.saveSteps(listSteps)
        findNavController().popBackStack()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = RecipeEditFragmentBinding.inflate(
        layoutInflater, container, false
    ).also { binding ->
        val recipeId = args.recipeId
        val adapter = StepEditAdapter(viewModel)
        binding.recipeStepsView.adapter = adapter

        viewModel.stepsData.observe(viewLifecycleOwner) { steps ->
            val recipeSteps = steps.filter { it.recipeIdStep == recipeId }
            adapter.submitList(recipeSteps)
            listSteps = recipeSteps.toMutableList()
        }

        if (recipeId != FeedFragment.NEW_RECIPE){
            val recipe = viewModel.getRecipeById(recipeId)
            if (recipe != null) {
                binding.titleTextEdit.setText(recipe.title)
                binding.authorTextEdit.setText(recipe.author)
                binding.chipGroupEdit.findViewWithTag<Chip>(recipe.category).isChecked
            }
        }

        binding.fabAddStep.setOnClickListener {
            val newStep = Step(
                stepId = 0L,
                recipeIdStep = recipeId,
                stepOrder = 1,
                stepText = "Helloy",
                stepImage = null
            )
            listSteps.add(newStep)
            onStepAddClicked()
        }

        binding.closeEdit.setOnClickListener {
            onCloseButtonClicked()
        }
        binding.saveEdit.setOnClickListener {
            onSaveButtonClicked(binding)
        }


    }.root

    private fun onStepAddClicked() {
//        viewModel.stepsData.setValue(listSteps)
    }

    companion object {
        const val REQUEST_KEY = "requestKey"
        const val RESULT_TITLE = "title"
        const val RESULT_AUTHOR = "author"
        const val RESULT_CATEGORY = "category"
        const val RESULT_STEPS = "steps"
    }
}