package gsbkomar.cocktailbar.fragments.details_cocktail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import gsbkomar.cocktailbar.databinding.IngredientDetailsItemBinding
import gsbkomar.data.models.IngredientDto
import javax.inject.Inject

class IngredientDetailsListAdapter @Inject constructor() :
    ListAdapter<String, IngredientViewHolder>(DifUtilCallBack()) {

    var position: Int = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        return IngredientViewHolder(
            IngredientDetailsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val item = getItem(position)
        this.position = position
        with(holder.binding) {
            ingredient.text = item
        }
    }
}

class DifUtilCallBack : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(
        oldItem: String,
        newItem: String
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: String,
        newItem: String
    ): Boolean {
        return oldItem == newItem
    }
}
