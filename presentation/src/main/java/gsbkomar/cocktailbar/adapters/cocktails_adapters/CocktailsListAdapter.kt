package gsbkomar.cocktailbar.adapters.cocktails_adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.squareup.picasso.Picasso
import gsbkomar.cocktailbar.adapters.recipe_adapters.RecipeViewHolder
import gsbkomar.cocktailbar.databinding.CocktailItemBinding
import gsbkomar.cocktailbar.databinding.RecipeItemBinding
import gsbkomar.cocktailbar.fragments.TapeCocktailsFragment
import gsbkomar.data.models.Cocktails
import gsbkomar.data.models.Recipe
import javax.inject.Inject

class CocktailsListAdapter @Inject constructor(
    private val context: TapeCocktailsFragment,
    private val onClick: (item: Int) -> Unit
) : ListAdapter<Cocktails, CocktailsViewHolder>(DifUtilCallBack())    {

    var position: Int = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailsViewHolder {
        return CocktailsViewHolder(
            CocktailItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CocktailsViewHolder, position: Int) {
        val item = getItem(position)
        this.position = position

        with(holder.binding) {
            title.text = item.name
            Picasso.with(context.requireContext())
                .load(item.uri)
                .rotate(270F)
                .into(photo)
        }

        holder.binding.root.setOnClickListener { onClick(position) }
    }
}

class DifUtilCallBack : DiffUtil.ItemCallback<Cocktails>() {
    override fun areItemsTheSame(
        oldItem: Cocktails,
        newItem: Cocktails
    ): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(
        oldItem: Cocktails,
        newItem: Cocktails
    ): Boolean {
        return oldItem == newItem
    }
}