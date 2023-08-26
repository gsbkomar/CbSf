package gsbkomar.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import gsbkomar.data.models.CocktailDto
import gsbkomar.data.models.IngredientDto
import gsbkomar.domain.models.Cocktail

@Dao
interface CocktailDao {

    @Upsert
    suspend fun upsert(cocktail: CocktailDto): Long

    @Delete
    suspend fun delete(cocktailDto: CocktailDto)

    @Query("SELECT * FROM CocktailDto")
    suspend fun getAll(): List<CocktailDto>

    @Query("SELECT * FROM CocktailDto WHERE id=:id")
    suspend fun getById(id: Long): CocktailDto

    @Update
    suspend fun update(cocktailDto: CocktailDto)
}