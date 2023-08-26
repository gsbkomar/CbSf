package gsbkomar.domain.usecases

import gsbkomar.domain.repository.CocktailsRepository
import javax.inject.Inject

class GetCocktailByIdUseCase @Inject constructor(private val cocktailsRepository: CocktailsRepository) {
    suspend fun execute(id: Long) = cocktailsRepository.getCocktailById(id)
}