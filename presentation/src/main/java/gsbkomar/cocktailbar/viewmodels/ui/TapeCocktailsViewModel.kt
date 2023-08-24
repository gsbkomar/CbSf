package gsbkomar.cocktailbar.viewmodels.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import gsbkomar.cocktailbar.fragments.TapeCocktailsFragment
import gsbkomar.data.models.CocktailDto
import gsbkomar.data.models.IngredientDto
import kotlinx.coroutines.launch
import javax.inject.Inject

class TapeCocktailsViewModel @Inject constructor(): ViewModel() {
    fun saveCocktailsInfo(cocktailDto: CocktailDto) = TapeCocktailsFragment().saveInfo(cocktailDto)
   fun deleteIngredientInfo(ingredientDto: IngredientDto) = TapeCocktailsFragment().deleteInfo(ingredientDto)

    fun getIngredientInfo(cocktailDto: CocktailDto) = TapeCocktailsFragment().getInfo(cocktailDto)

    fun getCocktailById() : CocktailDto? {
        return TapeCocktailsFragment().getCocktailById()
    }
}