package gsbkomar.data.models.state

import android.net.Uri
import gsbkomar.data.models.CocktailDto

data class CocktailState(
    val cocktails: List<CocktailDto> = emptyList(),
    val photo: Uri? = null,
    val name: String = "",
    val description: String = "",
    val ingredients: List<String> = emptyList(),
    val recipe: String = "",
    val isAddingCocktails: Boolean = false
)