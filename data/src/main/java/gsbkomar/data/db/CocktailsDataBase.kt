package gsbkomar.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import gsbkomar.data.db.dao.CocktailDao
import gsbkomar.data.db.dao.IngredientDao
import gsbkomar.data.models.CocktailDto
import gsbkomar.data.models.IngredientDto

@Database(
    entities = [CocktailDto::class, IngredientDto::class],
    version = 1,
    exportSchema = false
)
abstract class CocktailsDataBase : RoomDatabase() {
    abstract val cocktailDao: CocktailDao
    abstract val ingredientDao: IngredientDao
}