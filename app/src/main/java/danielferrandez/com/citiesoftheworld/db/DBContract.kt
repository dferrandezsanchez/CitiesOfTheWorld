package danielferrandez.com.citiesoftheworld.db

import android.provider.BaseColumns

object DBContract {

    /* Inner class that defines the table contents */
    class CityEntity : BaseColumns {
        companion object {
            val TABLE_NAME = "cities"
            val COLUMN_CITY_ID = "cityId"
            val COLUMN_CITY_NAME = "cityName"
            val COLUMN_CITY_LOCAL_NAME = "cityLocalName"
            val COLUMN_CITY_LAT = "cityLat"
            val COLUMN_CITY_LNG = "cityLng"
            val COLUMN_CITY_CREATED = "cityCreated"
            val COLUMN_CITY_UPDATED = "cityUpdated"
            val COLUMN_COUNTRY_ID = "cityCountryId"
            val COLUMN_COUNTRY_NAME = "cityCountryName"
            val COLUMN_COUNTRY_CREATED = "cityCountryCreated"
            val COLUMN_COUNTRY_UPDATED = "cityCountryUpdated"
            val COLUMN_COUNTRY_CONTINENT_ID = "cityCountryContinentId"
        }
    }
}