package gsbkomar.cocktailbar.adapters.cocktails_adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import gsbkomar.cocktailbar.databinding.CocktailItemBinding
import gsbkomar.cocktailbar.fragments.TapeCocktailsFragment
import gsbkomar.data.models.CocktailDto
import javax.inject.Inject

class CocktailsListAdapter @Inject constructor(
    private val context: TapeCocktailsFragment,
    private val onClick: (item: Int) -> Unit
) : ListAdapter<CocktailDto, CocktailsViewHolder>(DifUtilCallBack())    {

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

class DifUtilCallBack : DiffUtil.ItemCallback<CocktailDto>() {
    override fun areItemsTheSame(
        oldItem: CocktailDto,
        newItem: CocktailDto
    ): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(
        oldItem: CocktailDto,
        newItem: CocktailDto
    ): Boolean {
        return oldItem == newItem
    }
}