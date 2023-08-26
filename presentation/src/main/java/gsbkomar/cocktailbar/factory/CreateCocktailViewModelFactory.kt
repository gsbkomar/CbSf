package gsbkomar.cocktailbar.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import gsbkomar.cocktailbar.fragments.create_cocktail.CreateCocktailViewModel
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class CreateCocktailViewModelFactory @Inject constructor(private val createCocktailsViewModel: CreateCocktailViewModel) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        if (modelClass.isAssignableFrom(CreateCocktailViewModel::class.java)
        ) createCocktailsViewModel as T else throw java.lang.IllegalArgumentException("Unknown class name")
}