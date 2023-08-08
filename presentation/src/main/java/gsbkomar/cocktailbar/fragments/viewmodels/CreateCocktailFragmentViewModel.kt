package gsbkomar.cocktailbar.fragments.viewmodels

import androidx.lifecycle.ViewModel
import gsbkomar.data.models.Cocktails
import javax.inject.Inject

class CreateCocktailFragmentViewModel @Inject constructor(private val viewModel: TapeCocktailsViewModel) : ViewModel() {
    fun saveCocktail(cocktails: Cocktails) = viewModel.saveCocktailsInfo(cocktails)
}