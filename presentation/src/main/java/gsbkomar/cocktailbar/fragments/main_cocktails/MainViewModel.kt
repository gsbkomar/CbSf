package gsbkomar.cocktailbar.fragments.main_cocktails

import androidx.lifecycle.ViewModel
import gsbkomar.data.impl.CocktailsRepositoryImpl
import gsbkomar.domain.usecases.GetAllCocktailsUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(repositoryImpl: CocktailsRepositoryImpl) : ViewModel() {

    private val getAllCocktailsUseCase = GetAllCocktailsUseCase(repositoryImpl)
    suspend fun isContainsCocktailsInDataBase(): Boolean =
        getAllCocktailsUseCase.execute().isNotEmpty()

}