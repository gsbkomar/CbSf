package gsbkomar.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import gsbkomar.data.models.CocktailDto
import gsbkomar.data.models.IngredientDto

@Dao
interface CocktailDao {

    @Upsert
    suspend fun upsert(cocktailDto: CocktailDto)

    @Delete
    suspend fun delete(cocktailDto: CocktailDto)

    @Query("SELECT * FROM CocktailDto")
    suspend fun getAll() : List<CocktailDto>

    @Query("SELECT * FROM CocktailDto WHERE id=:id")
    suspend fun getById(id: Int) : CocktailDto
}