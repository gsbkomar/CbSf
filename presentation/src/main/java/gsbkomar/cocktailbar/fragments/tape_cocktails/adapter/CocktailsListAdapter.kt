package gsbkomar.cocktailbar.fragments.tape_cocktails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import gsbkomar.cocktailbar.databinding.CocktailItemBinding
import gsbkomar.cocktailbar.fragments.tape_cocktails.TapeCocktailsFragment
import gsbkomar.data.models.CocktailDto
import gsbkomar.domain.models.Cocktail
import javax.inject.Inject

class CocktailsListAdapter @Inject constructor(
    private val context: TapeCocktailsFragment,
    private val onClick: (item: Int) -> Unit
) : ListAdapter<Cocktail, CocktailsViewHolder>(DifUtilCallBack()) {

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
        val photoUri = item.photo.toUri()

        with(holder.binding) {
            title.text = item.name
            Glide.with(context.requireContext())
                .load(photoUri)
                .into(photo)
        }

        holder.binding.root.setOnClickListener { onClick(position) }
    }
}

class DifUtilCallBack : DiffUtil.ItemCallback<Cocktail>() {
    override fun areItemsTheSame(
        oldItem: Cocktail,
        newItem: Cocktail
    ): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(
        oldItem: Cocktail,
        newItem: Cocktail
    ): Boolean {
        return oldItem == newItem
    }
}