package gsbkomar.cocktailbar.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import gsbkomar.cocktailbar.fragments.tape_cocktails.TapeCocktailsViewModel
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class TapeCocktailsViewModelFactory @Inject constructor(private val tapeCocktailsViewModel: TapeCocktailsViewModel) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        if (modelClass.isAssignableFrom(TapeCocktailsViewModel::class.java)
        ) tapeCocktailsViewModel as T else throw java.lang.IllegalArgumentException("Unknown class name")
}