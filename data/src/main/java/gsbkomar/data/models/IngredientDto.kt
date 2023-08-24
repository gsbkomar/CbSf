package gsbkomar.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import gsbkomar.domain.models.Ingredient

@Entity
data class IngredientDto (
    override val ingredient: String,
    @PrimaryKey(autoGenerate = true)
    override val id: Int = 0
) : Ingredient