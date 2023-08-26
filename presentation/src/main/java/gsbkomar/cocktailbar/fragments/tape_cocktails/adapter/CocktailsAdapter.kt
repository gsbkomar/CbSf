package gsbkomar.cocktailbar.fragments.tape_cocktails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import gsbkomar.cocktailbar.databinding.CocktailItemBinding
import gsbkomar.cocktailbar.fragments.tape_cocktails.TapeCocktailsFragment
import gsbkomar.data.models.CocktailDto
import gsbkomar.domain.models.Cocktail
import javax.inject.Inject

class CocktailsAdapter @Inject constructor(private val context: TapeCocktailsFragment) :
    RecyclerView.Adapter<CocktailsViewHolder>() {
    private var cocktailDtoData: List<Cocktail> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailsViewHolder {
        return CocktailsViewHolder(
            CocktailItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = cocktailDtoData.size

    override fun onBindViewHolder(holder: CocktailsViewHolder, position: Int) {
        val cocktails = cocktailDtoData.getOrNull(position)!!
        val photoUri = cocktails.photo.toUri()
        with(holder.binding) {
            title.text = cocktails.name
            Glide.with(context.requireContext())
                .load(photoUri)
                .into(photo)
        }
    }
}

class CocktailsViewHolder(val binding: CocktailItemBinding) : RecyclerView.ViewHolder(binding.root)