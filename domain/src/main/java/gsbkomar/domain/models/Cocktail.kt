package gsbkomar.domain.models

import android.net.Uri

interface Cocktail {
    val id: Int
    val photo: String
    val name: String
    val description: String
    val ingredients: List<String>
    val recipe: String
}

