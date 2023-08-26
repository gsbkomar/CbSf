package gsbkomar.cocktailbar.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import gsbkomar.cocktailbar.fragments.main_cocktails.MainViewModel
import gsbkomar.cocktailbar.fragments.tape_cocktails.TapeCocktailsViewModel
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class MainFragmentViewModelFactory @Inject constructor(private val mainViewModel: MainViewModel) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        if (modelClass.isAssignableFrom(MainViewModel::class.java)
        ) mainViewModel as T else throw java.lang.IllegalArgumentException("Unknown class name")
}