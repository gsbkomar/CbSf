package gsbkomar.cocktailbar.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import gsbkomar.cocktailbar.fragments.details_cocktail.CocktailDetailsViewModel
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class CocktailDetailsViewModelFactory @Inject constructor(private val cocktailDetailsViewModel: CocktailDetailsViewModel) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        if (modelClass.isAssignableFrom(CocktailDetailsViewModel::class.java)
        ) cocktailDetailsViewModel as T else throw java.lang.IllegalArgumentException("Unknown class name")
}