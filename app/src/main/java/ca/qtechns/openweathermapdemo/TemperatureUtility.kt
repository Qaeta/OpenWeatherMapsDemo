package ca.qtechns.openweathermapdemo

import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.roundToInt

object TemperatureUtility {
    fun ConvertToCelsius(tempInKelvin: Double): Int {
        return (tempInKelvin-273.15).roundToInt()
    }

    fun ConvertToFarenheit(tempInKelvin: Double): Int {
        return (tempInKelvin*9/5-459.67).roundToInt()
    }
}