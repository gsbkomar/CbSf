package gsbkomar.cocktailbar.fragments.create_cocktail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gsbkomar.cocktailbar.databinding.RecipeItemBinding
import javax.inject.Inject

class RecipeAdapter @Inject constructor() : RecyclerView.Adapter<RecipeViewHolder>() {
    private var ingredientDtoData: List<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(
            RecipeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = ingredientDtoData.size

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = ingredientDtoData.getOrNull(position)!!
        with(holder.binding) {
            tvRecipe.text = recipe
        }
    }
}

class RecipeViewHolder(val binding: RecipeItemBinding) : RecyclerView.ViewHolder(binding.root)
