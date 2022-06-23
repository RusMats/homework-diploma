package ru.netology.myapplication.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.chip.Chip
import ru.netology.myapplication.databinding.RecipeEditFragmentBinding
import ru.netology.myapplication.view_model.MainViewModel


class RecipeEditFragment : Fragment() {
    private val viewModel by viewModels<MainViewModel>(ownerProducer = ::requireParentFragment)
    private val args by navArgs<RecipeEditFragmentArgs>()

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
        findNavController().popBackStack()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = RecipeEditFragmentBinding.inflate(
        layoutInflater, container, false
    ).also { binding ->
        if (args.recipeId != FeedFragment.NEW_RECIPE){
            val recipe = viewModel.getRecipeById(args.recipeId)
            if (recipe != null) {
                binding.titleTextEdit.setText(recipe.title)
                binding.authorTextEdit.setText(recipe.author)
                binding.chipGroupEdit.findViewWithTag<Chip>(recipe.category).isChecked
            }

        }
//        binding.fabAddStep.setOnClickListener {
//            onAddClicked()
//        }

        binding.closeEdit.setOnClickListener {
            onCloseButtonClicked()
        }
        binding.saveEdit.setOnClickListener {
            onSaveButtonClicked(binding)
        }

    }.root

    private fun onAddClicked() {
        TODO("Not yet implemented")
    }

    companion object {
        const val REQUEST_KEY = "requestKey"
        const val RESULT_TITLE = "title"
        const val RESULT_AUTHOR = "author"
        const val RESULT_CATEGORY = "category"

    }
}