package gsbkomar.cocktailbar.fragments.tape_cocktails

import androidx.lifecycle.ViewModel
import gsbkomar.cocktailbar.State
import gsbkomar.data.impl.CocktailsRepositoryImpl
import gsbkomar.domain.models.Cocktail
import gsbkomar.domain.usecases.GetAllCocktailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class TapeCocktailsViewModel @Inject constructor(cocktailsRepositoryImpl: CocktailsRepositoryImpl) :
    ViewModel() {

    private var _state = MutableStateFlow<State>(State.Loading)
    var state = _state.asStateFlow()

    private val getAllCocktailsUseCase = GetAllCocktailsUseCase(cocktailsRepositoryImpl)
    suspend fun getAllCocktails(): List<Cocktail> {
        _state.value = State.Loading
        val list = getAllCocktailsUseCase.execute()
        _state.value = State.Success
        return list
    }

}

