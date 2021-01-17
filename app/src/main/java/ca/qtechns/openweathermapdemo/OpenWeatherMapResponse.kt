package ca.qtechns.openweathermapdemo


data class OpenWeatherMapResponse(
    var coord: Coord,
    var weather: Array<Weather>,
    var base: String,
    var main: Main,
    var visibility: String,
    var wind: Wind,
    var clouds: Clouds,
    var dt: Int,
    var sys: Sys,
    var timezone: Int,
    var id: Int,
    var name: String,
    var cod: Int
)

data class Coord(
    var lon: Double,
    var lat: Double
)

data class Weather(
    var id: Int,
    var main: String,
    var description: String,
    var icon: String
)

data class Main(
    var temp: Double,
    var feelsLike: Double,
    var tempMin: Double,
    var tempMax: Double,
    var pressure: Int,
    var humidity: Int
)

data class Wind(
    var speed: Double,
    var deg: Int
)

data class Clouds(
   var all: Int
)

data class Sys(
    var type: Int,
    var id: Int,
    var country: String,
    var sunrise: Int,
    var sunset: Int
)