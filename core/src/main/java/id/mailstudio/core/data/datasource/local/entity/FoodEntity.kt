package id.mailstudio.core.data.datasource.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import id.mailstudio.core.utils.Constants


@Entity(tableName = Constants.FOOD_TABLE_NAME)
data class FoodEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "food_id")
    var foodId: String,

    @ColumnInfo(name = "food_name")
    var foodName: String,

    @ColumnInfo(name = "food_instruction")
    var foodInstruction: String,

    @ColumnInfo(name = "food_image")
    var foodImage: String,

    @ColumnInfo(name = "food_category")
    var foodCategoryName: String,

    @ColumnInfo(name = "food_area")
    var foodArea: String,

    @ColumnInfo(name = "food_tags")
    var foodTags: String? = null,

    @ColumnInfo(name = "food_source")
    var foodSource: String,

    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = false
)