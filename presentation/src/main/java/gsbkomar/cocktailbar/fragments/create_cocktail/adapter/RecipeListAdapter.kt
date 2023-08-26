package gsbkomar.cocktailbar.fragments.create_cocktail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import gsbkomar.cocktailbar.databinding.RecipeItemBinding
import javax.inject.Inject

class RecipeListAdapter @Inject constructor(
    private val onClick: (item: Int) -> Unit
) : ListAdapter<String, RecipeViewHolder>(DifUtilCallBack()) {

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
            tvRecipe.text = item
            btnDeleteRecipe.setOnClickListener {
                onClick(position)
            }
        }

        // holder.binding.root.setOnClickListener { onClick(position) }
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
