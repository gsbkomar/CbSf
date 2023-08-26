package gsbkomar.domain.repository

import gsbkomar.domain.models.Cocktail

interface CocktailsRepository {
    suspend fun getAllCocktails() : List<Cocktail>

    suspend fun getCocktailById(id: Long) : Cocktail

    suspend fun upsertCocktail(cocktail: Cocktail)

    suspend fun updateCocktail(cocktail: Cocktail, id: Long)

}