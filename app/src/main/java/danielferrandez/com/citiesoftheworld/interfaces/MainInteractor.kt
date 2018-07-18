package danielferrandez.com.citiesoftheworld.interfaces

import danielferrandez.com.citiesoftheworld.api.RequestModel
import danielferrandez.com.citiesoftheworld.model.CityModel
import java.util.ArrayList


interface MainInteractor {
    fun getCities(filter:String?, fromScroll: Boolean)
    fun getCitiesSuccess(result: RequestModel)
    fun getCitiesError(error: Throwable)

    fun getCitiesFromDB()
    fun getCitiesFromDBSuccess(cities: ArrayList<CityModel>)
    fun getCitiesFromDBError()
}