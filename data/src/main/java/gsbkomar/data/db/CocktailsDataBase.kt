package gsbkomar.data.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
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

    companion object {
        private var INSTANCE: CocktailsDataBase? = null
        private const val DATABASE_NAME = "cocktails.db"

        fun getInstance(application: Application): CocktailsDataBase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    application,
                    CocktailsDataBase::class.java,
                    DATABASE_NAME
                ).build().also { INSTANCE = it }
            }
    }
}