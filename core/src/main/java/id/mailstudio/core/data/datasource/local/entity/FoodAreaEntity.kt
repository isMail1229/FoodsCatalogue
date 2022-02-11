package id.mailstudio.core.data.datasource.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import id.mailstudio.core.utils.Constants

@Entity(tableName = Constants.FOOD_AREA_TABLE_NAME)
data class FoodAreaEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,

    @ColumnInfo(name = "name")
    val name: String
)