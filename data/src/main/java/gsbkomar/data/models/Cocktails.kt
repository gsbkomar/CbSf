package gsbkomar.data.models

import android.net.Uri
import java.io.Serializable

data class Cocktails(
    val uri: Uri?,
    val name: String,
    val description: String,
    val ingredients: MutableList<Recipe>,
    val recipe: String
) : Serializable