package gsbkomar.domain.models

data class Cocktail (
    val id: Long = 0,
    val photo: String,
    val name: String,
    val description: String,
    val ingredients: List<String>,
    val recipe: String
)

