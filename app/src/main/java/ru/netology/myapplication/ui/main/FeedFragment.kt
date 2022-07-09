package ru.netology.myapplication.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.chip.Chip
import ru.netology.myapplication.adapter.RecipeAdapter
import ru.netology.myapplication.data.RecipeRepository
import ru.netology.myapplication.data.StepRepository
import ru.netology.myapplication.databinding.FeedFragmentBinding
import ru.netology.myapplication.util.ItemTouchMoveCallback
import ru.netology.myapplication.view_model.MainViewModel


class FeedFragment : Fragment() {

    private val viewModel by viewModels<MainViewModel>(ownerProducer = ::requireParentFragment)
//    private val adapter = RecipeAdapter(viewModel)
//    private val dragCallback = ItemTouchMoveCallback(adapter)
//    private val touchHelper =ItemTouchHelper(dragCallback)

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
            val recipe: Long = recipeId ?: NEW_RECIPE
//            bundle? safe arguments - Long not null
            val direction = FeedFragmentDirections.actionFeedFragmentToRecipeEditFragment(recipe)
            findNavController().navigate(direction)
        }

        viewModel.navigateToRecipeFragment.observe(this) { recipeId ->
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
        val dragCallback = ItemTouchMoveCallback(adapter)
        val touchHelper = ItemTouchHelper(dragCallback)
        touchHelper.attachToRecyclerView(binding.recipesRecyclerView)
        viewModel.recipeData.observe(viewLifecycleOwner) { recipes ->
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

            binding.chipGroupContent.setOnCheckedStateChangeListener { _, checkedIds ->
                val categories: List<String> = checkedIds.map {
                    view?.findViewById<Chip>(it)?.tag.toString()
                }
                adapter.submitList(viewModel.onFilterClicked(recipes, categories))
            }

            binding.searchBar.setOnQueryTextListener(object :
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null) {
                        adapter.submitList(recipes.filter {
                            it.title.contains(newText)
                        })
                    } else { adapter.submitList(recipes) }
                    return false
                }
            })

        }

        binding.closeNavigationView.setOnClickListener {
            if (binding.navigationView.isVisible) {
                binding.navigationView.visibility = View.GONE
            }
        }

        binding.filter.setOnClickListener {
            if (!binding.navigationView.isVisible) {
                binding.navigationView.visibility = View.VISIBLE
            } else {
                binding.navigationView.visibility = View.GONE
            }
        }

    }.root

    companion object {
        const val NEW_RECIPE = RecipeRepository.NEW_RECIPE_ID
        const val NEW_STEP = StepRepository.NEW_STEP_ID
    }
}
