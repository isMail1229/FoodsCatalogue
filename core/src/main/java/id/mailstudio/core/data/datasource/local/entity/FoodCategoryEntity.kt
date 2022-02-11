package id.mailstudio.core.data.datasource.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import id.mailstudio.core.utils.Constants

@Entity(tableName = Constants.FOOD_CATEGORY_TABLE_NAME)
data class FoodCategoryEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id : Long,

    @ColumnInfo(name = "name")
    val name : String
)