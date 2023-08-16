package gsbkomar.data.models

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.annotations.SerializedName
import gsbkomar.domain.models.Cocktail

@Entity
data class CocktailDto(
    override val name: String,
    override val description: String,
    override val recipe: String,
    @PrimaryKey(autoGenerate = true)
    override val id: Int = 0
) : Cocktail {
    override var photo = ""
        get() = ""

    @get:SerializedName("ingredients")
    override var ingredients: List<String> = listOf()
    get() = listOf()

    @TypeConverter
    fun convertToString(value: String?): Uri? {
        return Uri.parse(value)
    }
}
