package ru.netology.myapplication.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.myapplication.adapter.RecipeAdapter
import ru.netology.myapplication.adapter.StepAdapter
import ru.netology.myapplication.databinding.RecipeFragmentBinding

class RecipeFragment : Fragment() {
    private val viewModel by viewModels<MainViewModel>(ownerProducer = ::requireParentFragment)
    private val args by navArgs<RecipeFragmentArgs>()

    private fun onCloseButtonClicked() {
        findNavController().popBackStack()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = RecipeFragmentBinding.inflate(
        layoutInflater, container, false
    ).also { binding ->
        val recipe = viewModel.getRecipeById(args.recipeId)
        if (recipe != null) {
            binding.titleText.text = recipe.title
            binding.authorText.text = recipe.author
            binding.content.text = recipe.category
            binding.favorite.isChecked = recipe.likedByMe
        }

        binding.favorite.setOnCheckedChangeListener { _, _ ->
            if (recipe != null) {
                viewModel.onLikeClicked(recipe)
            }
        }

        binding.close.setOnClickListener {
            onCloseButtonClicked()
        }
        /**
        /adapter Не требует обновлений
         */
        val adapter = StepAdapter(viewModel.liveDataSteps.value?: emptyList())
        binding.recipeStepsView.adapter = adapter

    }.root


}