package gsbkomar.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.TypeAdapter

@Entity
data class CocktailDto(
    val name: String,
    val description: String,
    val photo: String,
    val recipe: String,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)