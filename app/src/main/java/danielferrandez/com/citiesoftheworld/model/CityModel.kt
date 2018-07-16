package danielferrandez.com.citiesoftheworld.model

data class CityModel(val id: Int,
                     val name : String,
                     val local_name: String,
                     val lat: Double,
                     val lng: Double,
                     val created_at: String,
                     val updated_at:String,
                     val country_id: Int,
                     val country: Country)

data class Country(val id: Int,
                   val name: String,
                   val created_at: String,
                   val updated_at:String,
                   val country_id: Int)