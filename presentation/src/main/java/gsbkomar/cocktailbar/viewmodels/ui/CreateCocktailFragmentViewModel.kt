package gsbkomar.cocktailbar.viewmodels.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gsbkomar.cocktailbar.fragments.CreateCocktailFragment
import gsbkomar.data.models.CocktailDto
import gsbkomar.data.models.IngredientDto
import javax.inject.Inject

class CreateCocktailFragmentViewModel @Inject constructor(private val viewModel: TapeCocktailsViewModel) : ViewModel() {

    fun saveCocktail(cocktailDto: CocktailDto) = viewModel.saveCocktailsInfo(cocktailDto)

    fun saveIngredientsInfo(ingredientDto: IngredientDto) = CreateCocktailFragment().saveInfo(ingredientDto)

    fun deleteIngredient(ingredientDto: IngredientDto) = viewModel.deleteIngredientInfo(ingredientDto)

    fun getAllIngredientsById(cocktailDto: CocktailDto) : List<IngredientDto> {
       return viewModel.getIngredientInfo(cocktailDto)
    }

    fun getCocktailById() : CocktailDto? {
        return viewModel.getCocktailById()
    }
}