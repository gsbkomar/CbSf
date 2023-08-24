package gsbkomar.cocktailbar.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import gsbkomar.cocktailbar.viewmodels.ui.AddIngredientDialogViewModel
import gsbkomar.cocktailbar.viewmodels.ui.TapeCocktailsViewModel
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class ViewModelFactory @Inject constructor(private val viewModel: AddIngredientDialogViewModel) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        if (modelClass.isAssignableFrom(AddIngredientDialogViewModel::class.java)
        ) viewModel as T else throw java.lang.IllegalArgumentException("Unknown class name")
}