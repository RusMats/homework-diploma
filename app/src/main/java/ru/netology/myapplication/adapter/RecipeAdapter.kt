package ru.netology.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.myapplication.R
import ru.netology.myapplication.databinding.RecipeCardBinding
import ru.netology.myapplication.dto.Recipe

class RecipeAdapter(
    private val interactionsListener: RecipeInteractionsListener
) : ListAdapter<Recipe, RecipeAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecipeCardBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, interactionsListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: RecipeCardBinding,
        listener: RecipeInteractionsListener
    ) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var recipe: Recipe

        init {
            binding.cardFavCheckBox.setOnClickListener { listener.onLikeClicked(recipe) }
            binding.menu.setOnClickListener { popupMenu.show() }
            binding.recipeCard.setOnClickListener { listener.onRecipeClicked(recipe) }
        }

        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.menu).apply {
                inflate(R.menu.card_menu)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.remove -> {
                            listener.onDeleteClicked(recipe)
                            true
                        }
                        R.id.edit -> {
                            listener.onEditClicked(recipe)
                            true
                        }
                        else -> false
                    }
                }
            }
        }

        fun bind(recipe: Recipe) {
            this.recipe = recipe
            with(binding) {
                cardAuthor.text = recipe.author
                cardTitle.text = recipe.title
                cardCategory.text = recipe.category
                cardFavCheckBox.isChecked = recipe.likedByMe
            }
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<Recipe>() {

        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe) =
            oldItem.recipeId == newItem.recipeId

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe) = oldItem == newItem

    }
}
