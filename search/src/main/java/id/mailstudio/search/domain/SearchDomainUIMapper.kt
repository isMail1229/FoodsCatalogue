package id.mailstudio.search.domain

import id.mailstudio.core.domain.model.FoodAreaModel
import id.mailstudio.core.domain.model.FoodCategoryModel
import id.mailstudio.core.domain.model.FoodFilterModel


fun List<FoodCategoryModel>.toListFoodCategoryUIModel(): List<FoodCategoryUIModel> {
    return this.map {
        it.toFoodCategoryUIModel()
    }
}

fun FoodCategoryModel.toFoodCategoryUIModel() =
    FoodCategoryUIModel(
        id,
        name
    )

fun List<FoodAreaModel>.toListFoodAreaUIModel(): List<FoodAreaUIModel> {
    return this.map {
        it.toFoodAreaUIModel()
    }
}

fun FoodAreaModel.toFoodAreaUIModel() =
    FoodAreaUIModel(
        id,
        name
    )

fun FoodFilterUIModel.toFoodFilterModel() =
    FoodFilterModel(
        category?.toFoodCategoryModel(),
        area?.toFoodAreaModel(),
        isFavorite
    )

fun FoodCategoryUIModel.toFoodCategoryModel() =
    FoodCategoryModel(
        id,
        name
    )

fun FoodAreaUIModel.toFoodAreaModel() =
    FoodAreaModel(
        id,
        name
    )