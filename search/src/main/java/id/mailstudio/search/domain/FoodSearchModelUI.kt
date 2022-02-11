package id.mailstudio.search.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class FoodCategoryUIModel(
    val id: Long,
    val name: String
) : Parcelable

@Parcelize
data class FoodAreaUIModel(
    val id: Long,
    val name: String
) : Parcelable

@Parcelize
data class FoodFilterUIModel(
    val category: FoodCategoryUIModel?,
    val area: FoodAreaUIModel?,
    val isFavorite: Boolean? = null
) : Parcelable

@Parcelize
data class FoodUIFilterDetailModel(
    val foodId: String,
    val foodName: String,
    val foodInstruction: String,
    val foodArea: String,
    val foodCategory: String,
    val foodImage: String,
    val isFavorite: Boolean = false
) : Parcelable