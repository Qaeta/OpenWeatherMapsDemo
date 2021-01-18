/**
 * @author Erik Heinze-Milne
 */
package ca.qtechns.openweathermapdemo

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * Weather retrieval and display activity.
 *
 * @constructor Initializes the activity.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var weatherJson: OpenWeatherMapResponse
    private var tempType = "C"

    /**
     * On activity creation, set up spinner and button listeners
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup the dropdown using our defined string-array resource.
        ArrayAdapter.createFromResource(
                this,
                R.array.favourite_cities,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerFavouriteCities.adapter = adapter
        }

        // Implement our button click listeners.
        findViewById<Button>(R.id.buttonGetWeather)
            .setOnClickListener {
                val city: String = if (editTextCity.text.isNotEmpty()) {
                    val input = editTextCity.text.toString()
                    editTextCity.text.clear()
                    input
                } else {
                    spinnerFavouriteCities.selectedItem.toString()
                }

                fetchWeather(city)
            }

        findViewById<Button>(R.id.btnCorF)
            .setOnClickListener {
                if (this::weatherJson.isInitialized) {
                    if (tempType == "C") {
                        val temperatureString = "Temperature: ${TemperatureUtility
                            .ConvertToFarenheit(weatherJson.main.temp)}° F"
                        txtTemp.text = temperatureString
                        tempType = "F"
                    } else if (tempType == "F") {
                        val temperatureString = "Temperature: ${TemperatureUtility
                            .ConvertToCelsius(weatherJson.main.temp)}° C"
                        txtTemp.text = temperatureString
                        tempType = "C"
                    }
                }
            }

        // Set listener for submission via keyboard
        editTextCity.setOnEditorActionListener{ v, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                val city = editTextCity.text.toString()
                editTextCity.text.clear()
                // Hide the keyboard explicitly, as some devices were leaving it up on submission.
                editTextCity.hideKeyboard()

                fetchWeather(city)
                true
            } else {
                false
            }
        }
    }

    /**
     * Start the weather fetching process.
     *
     * @param city The city to fetch weather data about.
     */
    private fun fetchWeather(city: String) {
        val weatherJob = GlobalScope.async {
            OpenWeatherMap.getCurrentWeather(city)
        }

        GlobalScope.launch {
            runOnUiThread { progressBar.visibility = View.VISIBLE }
            val weather = weatherJob.await()
            runOnUiThread {
                updateWeather(weather)
                progressBar.visibility = View.GONE
            }
        }
    }

    /**
     * Parses the JSON string response, then updates the weather UI.
     *
     * @param weather JSON string response from Open Weather Maps.
     * @exception e JSON parsing can fail due to lacking data from the API, or due to a change
     *      in API response format.
     */
    private fun updateWeather(weather: String){
        // Try to parse the response
        try {
            weatherJson = Gson().fromJson(
                weather,
                OpenWeatherMapResponse::class.java
            )
        } catch (e: Exception) {
            Log.d(
                "updateWeather",
                "Error parsing response: ${e.message}"
            )
            failedToGetWeather()
            return
        }

        // Generate weather strings for passing to the UI
        val city = "City: ${weatherJson.name}"
        val temperatureString = "${TemperatureUtility
            .ConvertToCelsius(weatherJson.main.temp)}° C"
        tempType = "C"
        val temperature = "Temperature: $temperatureString"
        val weatherDescription = "Weather: ${weatherJson.weather[0].main} > " +
                weatherJson.weather[0].description
        val humidity = "Humidity: ${weatherJson.main.humidity}%"

        // Update the UI
        txtWeatherCity.text = city
        txtTemp.text = temperature
        txtWeather.text = weatherDescription
        txtHumidity.text = humidity
    }

    /**
     * Displays help dialog in the event of failure to retrieve or display
     * the weather in the UI.
     */
    private fun failedToGetWeather(){
        progressBar.visibility = View.GONE
        val alertDialog: AlertDialog = this.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton(R.string.ok) { dialog, _ -> dialog.dismiss() }
            }
            builder.setTitle(getString(R.string.fail_dialog_title))
            builder.setMessage(getString(R.string.fail_dialog_message))

            builder.create()
        }

        alertDialog.show()
    }

    /**
     * Hides the keyboard.
     */
    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}