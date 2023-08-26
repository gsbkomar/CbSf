package gsbkomar.cocktailbar.fragments.details_cocktail.adapter

import gsbkomar.cocktailbar.databinding.IngredientDetailsItemBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gsbkomar.data.models.IngredientDto
import javax.inject.Inject

class IngredientDetailsAdapter @Inject constructor() :
    RecyclerView.Adapter<IngredientViewHolder>() {
    private var ingredientDtoData: List<IngredientDto> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        return IngredientViewHolder(
            IngredientDetailsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = ingredientDtoData.size

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val recipe = ingredientDtoData.getOrNull(position)!!
        with(holder.binding) {
            ingredient.text = recipe.ingredient
        }
    }
}

class IngredientViewHolder(val binding: IngredientDetailsItemBinding) :
    RecyclerView.ViewHolder(binding.root)
