package gsbkomar.cocktailbar.viewmodels.ui

import androidx.lifecycle.ViewModel
import gsbkomar.cocktailbar.fragments.CreateCocktailFragment
import gsbkomar.cocktailbar.fragments.TapeCocktailsFragment
import gsbkomar.data.models.CocktailDto
import gsbkomar.data.models.IngredientDto
import javax.inject.Inject

class AddIngredientDialogViewModel @Inject constructor(private val viewModel: CreateCocktailFragmentViewModel) : ViewModel() {
    fun saveIngredient(ingredientDto: IngredientDto) = viewModel.saveIngredientsInfo(ingredientDto)
}