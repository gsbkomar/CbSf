package gsbkomar.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class IngredientDto(
    val cocktailId: Long,
    val ingredient: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)