package gsbkomar.cocktailbar.adapters.recipe_adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import gsbkomar.cocktailbar.databinding.RecipeItemBinding
import javax.inject.Inject

/*
class RecipeListAdapter @Inject constructor(
    private val onClick: (item: Int) -> Unit
) : ListAdapter<RecipeDto, RecipeViewHolder>(DifUtilCallBack())    {

    var position: Int = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(
            RecipeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val item = getItem(position)
        this.position = position

        with(holder.binding) {
            tvRecipe.text = item.name
        }

        holder.binding.root.setOnClickListener { onClick(position) }
    }
}

class DifUtilCallBack : DiffUtil.ItemCallback<Recipe>() {
    override fun areItemsTheSame(
        oldItem: Recipe,
        newItem: Recipe
    ): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(
        oldItem: Recipe,
        newItem: Recipe
    ): Boolean {
        return oldItem == newItem
    }
}*/
