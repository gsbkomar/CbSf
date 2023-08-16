package gsbkomar.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import gsbkomar.data.db.dao.CocktailDao
import gsbkomar.data.models.CocktailDto

@Database(
    entities = [CocktailDto::class],
    version = 1
)
abstract class CocktailsDataBase : RoomDatabase() {
    abstract val dao: CocktailDao
}