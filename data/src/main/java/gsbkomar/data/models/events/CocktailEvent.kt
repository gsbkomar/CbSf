package gsbkomar.data.models.events

import android.net.Uri
import gsbkomar.data.models.CocktailDto

sealed interface CocktailEvent {
    object SaveCocktail: CocktailEvent
    data class SetPhoto(val photo: Uri): CocktailEvent
    data class SetName(val name: String): CocktailEvent
    data class SetDescription(val description: String): CocktailEvent
    data class SetIngredients(val ingredients: List<String>): CocktailEvent
    data class SetRecipe(val recipe: String): CocktailEvent
    object ShowDialog: CocktailEvent
    object HideDialog: CocktailEvent
    data class DeleteContact(val cocktail: CocktailDto): CocktailEvent
}