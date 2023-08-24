package gsbkomar.data.models

import android.net.Uri
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.annotations.SerializedName
import gsbkomar.domain.models.Cocktail

@Entity
data class CocktailDto(
    override val name: String,
    override val description: String,
    override val photo: String,
    override val recipe: String,
    @PrimaryKey(autoGenerate = true)
    override val id: Int = 0
) : Cocktail
