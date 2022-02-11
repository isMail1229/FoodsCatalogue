package id.mailstudio.core.utils.preferences


interface FoodSharedPreference {

    fun getInt(keyName: String, defaultValue: Int): Int

    fun putInt(keyName: String, value: Int)

    fun getLong(keyName: String, defaultValue: Long): Long

    fun putLong(keyName: String, value: Long)

    fun getFloat(keyName: String, defaultValue: Float): Float

    fun getBoolean(keyName: String, defaultValue: Boolean): Boolean

    fun getString(keyName: String, defaultValue: String): String

    fun putString(keyName: String, value: String)

    fun removeValue(keyName: String)

    fun clearAll()
}