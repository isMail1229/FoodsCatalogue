package id.mailstudio.core.utils.ext

import androidx.navigation.NavController
import androidx.navigation.NavDirections

fun NavController.navigateSafe(directions: NavDirections) {
    try {
        navigate(directions)
    } catch (ex: IllegalArgumentException) {
        // do not navigate
        ex.printStackTrace()
    }
}