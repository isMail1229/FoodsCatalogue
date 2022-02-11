package id.mailstudio.foodcatalogue.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FoodUIModel(
    val foodId: String,
    val foodName: String,
    val foodInstruction: String,
    val foodArea: String,
    val foodCategory: String,
    val foodImage: String,
    val isFavorite: Boolean = false
) : Parcelable

@Parcelize
data class FoodIngredientUIModel(
    val foodIngredient: String,
    val foodMeasure: String
) : Parcelable