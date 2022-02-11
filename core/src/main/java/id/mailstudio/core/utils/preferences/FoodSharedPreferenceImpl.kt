package id.mailstudio.core.utils.preferences

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FoodSharedPreferenceImpl @Inject constructor(private val sharedPreferences: SharedPreferences) :
    FoodSharedPreference {
    override fun getInt(keyName: String, defaultValue: Int) =
        sharedPreferences.getInt(keyName, defaultValue)

    override fun putInt(keyName: String, value: Int) {
        sharedPreferences.edit().putInt(keyName, value).apply()
    }

    override fun getLong(keyName: String, defaultValue: Long) =
        sharedPreferences.getLong(keyName, defaultValue)

    override fun putLong(keyName: String, value: Long) {
        sharedPreferences.edit().putLong(keyName, value).apply()
    }

    override fun getFloat(keyName: String, defaultValue: Float) =
        sharedPreferences.getFloat(keyName, defaultValue)

    override fun getBoolean(keyName: String, defaultValue: Boolean) =
        sharedPreferences.getBoolean(keyName, defaultValue)

    override fun getString(keyName: String, defaultValue: String) =
        sharedPreferences.getString(keyName, defaultValue) ?: defaultValue

    override fun putString(keyName: String, value: String) {
        sharedPreferences.edit().putString(keyName, value).apply()
    }

    override fun removeValue(keyName: String) {
        sharedPreferences.edit().remove(keyName).apply()
    }

    override fun clearAll() {
        sharedPreferences.edit().clear().apply()
    }

}