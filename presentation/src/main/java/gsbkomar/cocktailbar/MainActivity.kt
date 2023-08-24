package gsbkomar.cocktailbar

import android.app.TaskStackBuilder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import dagger.hilt.android.AndroidEntryPoint
import gsbkomar.data.db.CocktailsDataBase

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

   /* private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            CocktailsDataBase::class.java,
            "cocktails.db"
        ).build()
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}