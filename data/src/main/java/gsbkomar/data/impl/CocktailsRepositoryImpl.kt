package gsbkomar.data.impl

import android.app.Application
import android.util.Log
import gsbkomar.data.db.CocktailsDataBase
import gsbkomar.data.models.IngredientDto
import gsbkomar.data.models.mappers.toDto
import gsbkomar.data.models.mappers.toEntity
import gsbkomar.domain.models.Cocktail
import gsbkomar.domain.repository.CocktailsRepository
import javax.inject.Inject

class CocktailsRepositoryImpl @Inject constructor(app: Application) : CocktailsRepository {

    private val db = CocktailsDataBase.getInstance(app)
    override suspend fun getAllCocktails(): List<Cocktail>  {
        var list = db.cocktailDao.getAll().map {
                val ingredient = db.ingredientDao.getIngredientsByCocktailId(it.id).map { ingredientDto ->  ingredientDto.ingredient }
                it.toEntity(ingredient)
            }
        return list
    }
    override suspend fun getCocktailById(id: Long): Cocktail {
        val cocktail = db.cocktailDao.getById(id)
        val ingredients = db.ingredientDao.getIngredientsByCocktailId(cocktail.id)
            .map { it.ingredient }
        return cocktail.toEntity(ingredients)
    }
    override suspend fun upsertCocktail(cocktail: Cocktail) {
        val id = db.cocktailDao.upsert(cocktail.toDto())
        val ingredients = cocktail.ingredients.map {
            IngredientDto(
                cocktailId = id,
                ingredient = it
            )
        }
        db.ingredientDao.insertIngredients(ingredients)
    }

    override suspend fun updateCocktail(cocktail: Cocktail, id: Long) {
        val newCocktail = cocktail.toDto()
        db.ingredientDao.getIngredientsByCocktailId(cocktail.id).map {
            db.ingredientDao.delete(it)
        }
        val ingredients = cocktail.ingredients.map {
            IngredientDto(
                cocktailId = cocktail.id,
                ingredient = it
            )
        }
        db.cocktailDao.update(newCocktail)
        db.ingredientDao.insertIngredients(ingredients)
    }
}
