package gsbkomar.cocktailbar.viewmodels.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gsbkomar.data.db.dao.CocktailDao
import gsbkomar.data.models.CocktailDto
import gsbkomar.data.models.events.CocktailEvent
import gsbkomar.data.models.state.CocktailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CocktailViewModel(
    private val dao: CocktailDao
) : ViewModel() {

    private val _state = MutableStateFlow(CocktailState())

    fun onEvent(event: CocktailEvent) {
        when (event) {

            CocktailEvent.HideDialog -> {
                _state.update {
                    it.copy(
                        isAddingCocktails = false
                    )
                }
            }

            CocktailEvent.SaveCocktail -> {
                val name = _state.value.name
                val description = _state.value.description
                val ingredients = _state.value.ingredients
                val photo = _state.value.photo
                val recipe = _state.value.recipe

                // Позже добавить проверку на наиличие фото
                if (name.isBlank() || description.isBlank() || ingredients.isEmpty() || recipe.isBlank()) return

                val cocktail = CocktailDto(
                    name = name,
                    description = description,
                    ingredients = ingredients,
                    photo = photo,
                    recipe = recipe
                )

                viewModelScope.launch {
                    dao.upsert(cocktail)
                }
            }

            is CocktailEvent.SetDescription -> {
                _state.update {
                    it.copy(
                        description = event.description
                    )
                }
            }

            is CocktailEvent.SetIngredients -> {
                _state.update {
                    it.copy(
                        ingredients = event.ingredients
                    )
                }
            }

            is CocktailEvent.SetName -> {
                _state.update {
                    it.copy(
                        name = event.name
                    )
                }
            }

            is CocktailEvent.SetPhoto -> {
                _state.update {
                    it.copy(
                        photo = event.photo
                    )
                }
            }

            is CocktailEvent.SetRecipe -> {
                _state.update {
                    it.copy(
                        recipe = event.recipe
                    )
                }
            }

            CocktailEvent.ShowDialog -> {
                _state.update {
                    it.copy(
                        isAddingCocktails = true
                    )
                }
            }

            is CocktailEvent.DeleteContact -> {
                viewModelScope.launch {
                    dao.delete(event.cocktail)
                }
            }
        }
    }
}