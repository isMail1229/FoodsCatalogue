package id.mailstudio.core.data.datasource.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import id.mailstudio.core.utils.Constants

@Entity(tableName = Constants.FOOD_INGREDIENT_TABLE_NAME)
data class FoodIngredient(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "food_id") val foodId: Long,
    @ColumnInfo(name = "food_ingredient") val foodIngredient: String?,
    @ColumnInfo(name = "food_measurement") val foodMeasurement: String?
)