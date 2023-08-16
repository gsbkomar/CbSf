package gsbkomar.cocktailbar.viewmodels.ui

import androidx.lifecycle.ViewModel
import gsbkomar.data.models.CocktailDto
import javax.inject.Inject

class CreateCocktailFragmentViewModel @Inject constructor(private val viewModel: TapeCocktailsViewModel) : ViewModel() {
    fun saveCocktail(cocktailDto: CocktailDto) = viewModel.saveCocktailsInfo(cocktailDto)
}