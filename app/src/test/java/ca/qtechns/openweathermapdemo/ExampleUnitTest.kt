package ca.qtechns.openweathermapdemo

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun kelvinToCelsius_isCorrect() {
        assertEquals(0, TemperatureUtility.ConvertToCelsius(273.15))
        assertEquals(-273, TemperatureUtility.ConvertToCelsius(0.00))
    }

    @Test
    fun kelvinToFarenheit_isCorrect() {
        assertEquals(8, TemperatureUtility.ConvertToFarenheit(260.00))
        assertEquals(-460, TemperatureUtility.ConvertToFarenheit(0.00))
    }
}