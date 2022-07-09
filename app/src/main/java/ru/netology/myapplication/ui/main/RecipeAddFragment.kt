package ru.netology.myapplication.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import ru.netology.myapplication.adapter.StepEditAdapter
import ru.netology.myapplication.data.RecipeRepository
import ru.netology.myapplication.databinding.RecipeEditFragmentBinding
import ru.netology.myapplication.dto.Step
import ru.netology.myapplication.view_model.MainViewModel


class RecipeAddFragment : Fragment() {
    private val viewModel by viewModels<MainViewModel>(ownerProducer = ::requireParentFragment)

    private lateinit var listSteps: MutableList<Step>

    private fun onCloseButtonClicked() {
        findNavController().popBackStack()
    }

    private fun onSaveButtonClicked(binding: RecipeEditFragmentBinding) {
        val title = binding.titleTextEdit.text
        val author = binding.authorTextEdit.text
        val category = binding.chipGroupEdit.findViewById<Chip>(binding.chipGroupEdit.checkedChipId)
            .tag.toString()
        if (!title.isNullOrBlank() and !author.isNullOrBlank()) {
            val newRecipeId = viewModel.onSaveButtonClick(title.toString(), author.toString(), category)
            viewModel.saveSteps(listSteps.map {
                it.copy(recipeIdStep = newRecipeId)
            })
            viewModel.clearNewSteps()
            findNavController().popBackStack()
        } else onEmptyToast(title.isNullOrBlank(), author.isNullOrBlank())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = RecipeEditFragmentBinding.inflate(
        layoutInflater, container, false
    ).also { binding ->
        val recipeId = RecipeRepository.NEW_RECIPE_ID
        val adapter = StepEditAdapter(viewModel)
        binding.recipeStepsView.adapter = adapter

        if (recipeId != FeedFragment.NEW_RECIPE) {
            val recipe = viewModel.getRecipeById(recipeId)
            if (recipe != null) {
                binding.titleTextEdit.setText(recipe.title)
                binding.authorTextEdit.setText(recipe.author)
                binding.chipGroupEdit.findViewWithTag<Chip>(recipe.category).isChecked
            }
        }

        viewModel.stepsData.observe(viewLifecycleOwner) { steps ->
            val recipeSteps = steps.filter { it.recipeIdStep == recipeId }
            adapter.submitList(recipeSteps)
            listSteps = recipeSteps.toMutableList()
        }

        binding.fabAddStep.setOnClickListener {
            val newStep = Step(
                stepId = FeedFragment.NEW_STEP,
                recipeIdStep = recipeId,
                stepOrder = adapter.itemCount + 1,
                stepText = "",
                stepImage = null
            )
            onStepAddClicked(newStep)
        }

        binding.closeEdit.setOnClickListener {
            onCloseButtonClicked()
        }
        binding.saveEdit.setOnClickListener {
            onSaveButtonClicked(binding)
        }
    }.root

    private fun onStepAddClicked(newStep: Step) {
        viewModel.saveSteps(listOf(newStep))
    }

    private fun onEmptyToast(titleIsEmpty: Boolean, authorIsEmpty: Boolean) {
        val content = "is empty"
        val titleName = "Title"
        val authorName = "Author"
        if (titleIsEmpty) Toast.makeText(context, "$titleName $content", Toast.LENGTH_SHORT).show()
        else if (authorIsEmpty) Toast.makeText(context, "$authorName $content", Toast.LENGTH_SHORT)
            .show()
    }

    companion object {
        const val REQUEST_KEY = "requestKey"
        const val RESULT_TITLE = "title"
        const val RESULT_AUTHOR = "author"
        const val RESULT_CATEGORY = "category"
//        const val RESULT_STEPS = "steps"
    }
}
