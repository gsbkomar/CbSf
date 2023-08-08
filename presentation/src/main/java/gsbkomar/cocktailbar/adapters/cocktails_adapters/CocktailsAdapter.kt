package gsbkomar.cocktailbar.adapters.cocktails_adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import gsbkomar.cocktailbar.databinding.CocktailItemBinding
import gsbkomar.cocktailbar.databinding.RecipeItemBinding
import gsbkomar.cocktailbar.fragments.TapeCocktailsFragment
import gsbkomar.data.models.Cocktails
import gsbkomar.data.models.Recipe
import javax.inject.Inject

class CocktailsAdapter @Inject constructor(private val context: TapeCocktailsFragment) : RecyclerView.Adapter<CocktailsViewHolder>() {
    private var cocktailsData: List<Cocktails> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailsViewHolder {
        return CocktailsViewHolder(
            CocktailItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = cocktailsData.size

    override fun onBindViewHolder(holder: CocktailsViewHolder, position: Int) {
        val cocktails = cocktailsData.getOrNull(position)!!
        with(holder.binding) {
            title.text = cocktails.name
            Picasso.with(context.requireContext())
                .load(cocktails.uri)
                .rotate(270F)
                .into(photo)
        }
    }
}

class CocktailsViewHolder(val binding: CocktailItemBinding) : RecyclerView.ViewHolder(binding.root)