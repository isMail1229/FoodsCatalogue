package id.mailstudio.foodcatalogue.domain

import id.mailstudio.core.domain.model.FoodIngredientModel
import id.mailstudio.core.domain.model.FoodModel

fun List<FoodModel>.toListFoodUIModel(): List<FoodUIModel> {
    return this.map {
        it.toFoodUIModel()
    }
}

fun FoodModel.toFoodUIModel() =
    FoodUIModel(
        foodId,
        foodName,
        foodInstruction,
        foodArea,
        foodCategory,
        foodImage,
        isFavorite
    )

fun List<FoodIngredientModel>.toListFoodIngredientUIModel(): List<FoodIngredientUIModel> {
    return this.map {
        it.toFoodIngredientUIModel()
    }
}

fun FoodIngredientModel.toFoodIngredientUIModel() =
    FoodIngredientUIModel(
        foodIngredient,
        foodMeasure
    )

fun FoodUIModel.toFoodModel(isFavorite: Boolean = false) =
    FoodModel(
        foodId,
        foodName,
        foodInstruction,
        foodArea,
        foodCategory,
        foodImage,
        "",
        null,
        isFavorite
    )