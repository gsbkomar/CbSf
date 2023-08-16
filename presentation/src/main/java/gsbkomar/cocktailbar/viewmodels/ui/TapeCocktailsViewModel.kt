package gsbkomar.cocktailbar.viewmodels.ui

import androidx.lifecycle.ViewModel
import gsbkomar.cocktailbar.fragments.TapeCocktailsFragment
import gsbkomar.data.models.CocktailDto
import javax.inject.Inject

class TapeCocktailsViewModel @Inject constructor(): ViewModel() {
    fun saveCocktailsInfo(cocktailDto: CocktailDto) = TapeCocktailsFragment().saveInfo(cocktailDto)
}