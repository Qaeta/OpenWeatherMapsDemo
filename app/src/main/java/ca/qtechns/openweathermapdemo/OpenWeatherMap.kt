/**
 * @author Erik Heinze-Milne
 */
package ca.qtechns.openweathermapdemo

import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitStringResponseResult
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Handles interaction with the Open Weather Maps API
 *
 * I realize having an entire object for this is overkill at this point, but if I were to expand it
 * beyond just current weather, having an object to encapsulate all the different interactions
 * would be useful.
 */
object OpenWeatherMap {
    /**
     * Retrieves the current weather for a city.
     *
     * @param city The city to retrieve weather for.
     * @return A JSON string response from the Open Weather Maps API,
     *      or an empty string if it fails.
     */
    suspend fun getCurrentWeather(city: String): String{
        var currentWeather = ""

        val endpoint = Strings.get(R.string.open_weather_map_endpoint)
        val apiKey = Strings.get(R.string.open_weather_map_api_key)

        val job = GlobalScope.launch {
            val (request, response, result) =
                Fuel.get("${endpoint}weather?q=${city}&appid=${apiKey}")
                    .awaitStringResponseResult()

            result.fold(
                { data ->
                    Log.d(
                        "getCurrentWeather",
                        data
                    )
                    currentWeather = data
                },
                { error ->
                    Log.d(
                        "getCurrentWeather",
                        "An error of type ${error.exception} occurred: ${error.message}"
                    )
                }
            )
        }
        job.join()


        return currentWeather
    }
}