package ru.netology.myapplication.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.myapplication.adapter.RecipeAdapter
import ru.netology.myapplication.databinding.FeedFragmentBinding
import ru.netology.myapplication.view_model.MainViewModel
import ru.netology.myapplication.view_model.RecipeViewModel

class FeedFragment : Fragment() {

    private val viewModel by viewModels<MainViewModel>(ownerProducer = ::requireParentFragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(
            requestKey = RecipeEditFragment.REQUEST_KEY
        ) { requestKey, bundle ->
            if (requestKey != RecipeEditFragment.REQUEST_KEY) return@setFragmentResultListener
            val title = bundle.getString(
                RecipeEditFragment.RESULT_TITLE
            ) ?: return@setFragmentResultListener
            val author = bundle.getString(
                RecipeEditFragment.RESULT_AUTHOR
            ) ?: return@setFragmentResultListener
            val category = bundle.getString(
                RecipeEditFragment.RESULT_CATEGORY
            ) ?: return@setFragmentResultListener
            viewModel.onSaveButtonClick(title, author, category)
        }

        viewModel.navigateToRecipeEditFragment.observe(this) { recipeId ->
            val recipe:Long = recipeId?: NEW_RECIPE
//            arguments? как лучше передать аргумент не null
            val direction = FeedFragmentDirections.actionFeedFragmentToRecipeEditFragment(recipe)
            findNavController().navigate(direction)
        }

        viewModel.navigateToRecipeFragment.observe(this) {recipeId ->
            val direction = FeedFragmentDirections.actionFeedFragmentToRecipeFragment(recipeId)
            findNavController().navigate(direction)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FeedFragmentBinding.inflate(layoutInflater, container, false).also { binding ->
        val adapter = RecipeAdapter(viewModel)
        binding.recipesRecyclerView.adapter = adapter
        viewModel.recipeData.observe(viewLifecycleOwner) { recipes ->
//            val recipes = pair.first?: emptyList()
            adapter.submitList(recipes)

            // region setDummyOnScreen
            if (recipes.isNotEmpty()) {
                binding.dummyText.visibility = View.GONE
                binding.dummyImage.visibility = View.GONE
            } else {
                binding.dummyText.visibility = View.VISIBLE
                binding.dummyImage.visibility = View.VISIBLE
            }
            // endregion setDummyOnScreen

            binding.fabFav.setOnClickListener {
                if (FAB_STATE) {
                    val favorites = recipes.filter {
                        it.likedByMe == true
                    }
                    FAB_STATE = false
                    adapter.submitList(favorites)
                } else {
                    adapter.submitList(recipes)
                    FAB_STATE = true
                }
            }
        }

        binding.fabAdd.setOnClickListener {
            viewModel.onAddClicked()
        }


    }.root

    companion object{
        const val NEW_RECIPE = 0L
        var FAB_STATE = true
    }
}