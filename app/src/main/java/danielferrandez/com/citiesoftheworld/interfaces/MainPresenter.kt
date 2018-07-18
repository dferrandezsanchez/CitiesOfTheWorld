package danielferrandez.com.citiesoftheworld.interfaces

import danielferrandez.com.citiesoftheworld.model.CityModel
import java.util.ArrayList

interface MainPresenter{
    fun getCities(filter:String?, fromScroll: Boolean)
    fun getCitiesSuccess(items: List<CityModel>)
    fun getCitiesError(error: Throwable)

    fun getCitiesFromDB();
    fun getCitiesFromDBSuccess(cities: ArrayList<CityModel>)
    fun getCitiesFromDBError()

}