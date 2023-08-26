package gsbkomar.domain.usecases

import gsbkomar.domain.models.Cocktail
import gsbkomar.domain.repository.CocktailsRepository
import javax.inject.Inject

class UpdateCocktailUseCase @Inject constructor(private val cocktailsRepository: CocktailsRepository) {
    suspend fun execute(cocktail: Cocktail, id: Long) =
        cocktailsRepository.updateCocktail(cocktail, id)
}