package gsbkomar.data.db.dao

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import gsbkomar.data.models.IngredientDto

@Dao
interface IngredientDao {

    @Upsert
    suspend fun insertIngredients(data: List<IngredientDto>)

    @Query("SELECT * FROM IngredientDto WHERE cocktailId=:cocktailId")
    suspend fun getIngredientsByCocktailId(cocktailId: Long): List<IngredientDto>

    @Delete
    suspend fun delete(ingredientDto: IngredientDto)

    /* @Query("UPDATE IngredientDto SET ingredient=:ingredientDto WHERE cocktailId =:cocktailId")
     suspend fun updateIngredients(cocktailId: Long, ingredientDto: List<IngredientDto>)*/
    @Update
    suspend fun updateIngredients(ingredientDto: List<IngredientDto>)

}