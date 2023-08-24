package gsbkomar.cocktailbar.adapters.recipe_adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import gsbkomar.cocktailbar.databinding.RecipeItemBinding
import gsbkomar.data.models.IngredientDto
import javax.inject.Inject


class RecipeListAdapter @Inject constructor(
    private val onClick: (item: Int) -> Unit
) : ListAdapter<IngredientDto, RecipeViewHolder>(DifUtilCallBack())    {

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
            tvRecipe.text = item.ingredient
            btnDeleteRecipe.setOnClickListener {
                onClick(position)
            }
        }

       // holder.binding.root.setOnClickListener { onClick(position) }
    }
}

class DifUtilCallBack : DiffUtil.ItemCallback<IngredientDto>() {
    override fun areItemsTheSame(
        oldItem: IngredientDto,
        newItem: IngredientDto
    ): Boolean {
        return oldItem.ingredient == newItem.ingredient
    }

    override fun areContentsTheSame(
        oldItem: IngredientDto,
        newItem: IngredientDto
    ): Boolean {
        return oldItem == newItem
    }
}
