/**
 * @author Erik Heinze-Milne
 */
package ca.qtechns.openweathermapdemo

import kotlin.math.roundToInt

/**
 * Utility for temperature conversions
 */
object TemperatureUtility {
    /**
     * Converts Kelvin temperature to Celsius
     *
     * @param tempInKelvin The temperature in Kelvin
     * @return Rounded int in Celsius
     */
    fun ConvertToCelsius(tempInKelvin: Double): Int {
        return (tempInKelvin-273.15).roundToInt()
    }

    /**
     * Converts Kelvin temperature to Farenheit
     *
     * @param tempInKelvin The temperature in Kelvin
     * @return Rounded int in Farenheit
     */
    fun ConvertToFarenheit(tempInKelvin: Double): Int {
        return (tempInKelvin*9/5-459.67).roundToInt()
    }
}