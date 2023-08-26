package gsbkomar.domain.usecases

import gsbkomar.domain.models.Cocktail
import gsbkomar.domain.repository.CocktailsRepository
import javax.inject.Inject

class GetAllCocktailsUseCase @Inject constructor(private val cocktailsRepository: CocktailsRepository) {
    suspend fun execute(): List<Cocktail> = cocktailsRepository.getAllCocktails()
}