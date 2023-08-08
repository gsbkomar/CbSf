package gsbkomar.cocktailbar.fragments.viewmodels

import androidx.lifecycle.ViewModel
import gsbkomar.cocktailbar.fragments.TapeCocktailsFragment
import gsbkomar.data.models.Cocktails
import javax.inject.Inject

class TapeCocktailsViewModel @Inject constructor(): ViewModel() {
    fun saveCocktailsInfo(cocktails: Cocktails) = TapeCocktailsFragment().saveInfo(cocktails)
}