/**
 * @author Erik Heinze-Milne
 */
package ca.qtechns.openweathermapdemo

import androidx.annotation.StringRes

/**
 * Utility to access resources globally without needing to pass context around.
 */
object Strings {
    /**
     * Get the string resource requested by ID
     *
     * @param stringRes The resource ID being requested
     * @return The string resource that was requested.
     */
    fun get(@StringRes stringRes: Int): String {
        return App.instance.getString(stringRes)
    }
}