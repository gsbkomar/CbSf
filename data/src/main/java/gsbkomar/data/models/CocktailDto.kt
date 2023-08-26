package gsbkomar.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CocktailDto(
    val name: String,
    val description: String,
    val photo: String,
    val recipe: String,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)
