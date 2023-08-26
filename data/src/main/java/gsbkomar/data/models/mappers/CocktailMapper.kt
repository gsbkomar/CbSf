package gsbkomar.data.models.mappers

import gsbkomar.data.models.CocktailDto
import gsbkomar.domain.models.Cocktail

fun Cocktail.toDto() = CocktailDto(
    id = id,
    name = name,
    description = description,
    recipe = recipe,
    photo = photo
)

fun CocktailDto.toEntity(ingredients: List<String>) = Cocktail(
    id = id,
    name = name,
    ingredients = ingredients,
    description = description,
    photo = photo,
    recipe = recipe
)