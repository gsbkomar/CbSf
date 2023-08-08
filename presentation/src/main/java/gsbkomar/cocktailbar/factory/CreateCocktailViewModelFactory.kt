package gsbkomar.cocktailbar.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import gsbkomar.cocktailbar.fragments.viewmodels.CreateCocktailFragmentViewModel
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class CreateCocktailViewModelFactory @Inject constructor(private val createCocktailsViewModel: CreateCocktailFragmentViewModel) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        if (modelClass.isAssignableFrom(CreateCocktailFragmentViewModel::class.java)
        ) createCocktailsViewModel as T else throw java.lang.IllegalArgumentException("Unknown class name")
}