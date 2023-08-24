package gsbkomar.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import gsbkomar.data.models.IngredientDto

@Dao
interface IngredientDao {

    @Insert
    suspend fun insertIngredients(data: List<IngredientDto>)

    /*@Upsert
    suspend fun insertIngredient(data: IngredientDto)*/

    @Query("SELECT * FROM IngredientDto WHERE id=:cocktailId")
    suspend fun getIngredientsByCocktailId(cocktailId: Long): List<IngredientDto>

    @Delete
    suspend fun delete(ingredientDto: IngredientDto)
}