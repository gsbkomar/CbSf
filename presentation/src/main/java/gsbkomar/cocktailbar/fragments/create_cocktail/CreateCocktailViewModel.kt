package gsbkomar.cocktailbar.fragments.create_cocktail

import androidx.lifecycle.ViewModel
import gsbkomar.cocktailbar.State
import gsbkomar.data.impl.CocktailsRepositoryImpl
import gsbkomar.domain.models.Cocktail
import gsbkomar.domain.usecases.GetAllCocktailsUseCase
import gsbkomar.domain.usecases.UpdateCocktailUseCase
import gsbkomar.domain.usecases.UpsertCocktailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class CreateCocktailViewModel @Inject constructor(
    cocktailsRepositoryImpl: CocktailsRepositoryImpl
) : ViewModel() {

    private var _state = MutableStateFlow<State>(State.Loading)
    var state = _state.asStateFlow()

    private val upsertCocktailUseCase = UpsertCocktailUseCase(cocktailsRepositoryImpl)
    private val getAllCocktailsUseCase = GetAllCocktailsUseCase(cocktailsRepositoryImpl)
    private val updateCocktailUseCase = UpdateCocktailUseCase(cocktailsRepositoryImpl)

    suspend fun upsertCocktailData(cocktailDto: Cocktail) {
        _state.value = State.Loading
        upsertCocktailUseCase.execute(cocktailDto)
        _state.value = State.Success
    }

    suspend fun getAllCocktails(): List<Cocktail> {
        _state.value = State.Loading
        val list = getAllCocktailsUseCase.execute()
        _state.value = State.Success
        return list
    }

    suspend fun updateCocktail(cocktail: Cocktail, id: Long) {
        _state.value = State.Loading
        updateCocktailUseCase.execute(cocktail, id)
        _state.value = State.Success
    }
}

