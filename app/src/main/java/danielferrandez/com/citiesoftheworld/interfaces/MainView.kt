package danielferrandez.com.citiesoftheworld.interfaces

import danielferrandez.com.citiesoftheworld.model.CityModel
import java.util.ArrayList

interface MainView {

    fun getCities(fromScroll: Boolean)
    fun getCitiesSuccess(items:List<CityModel>)
    fun getCitiesError()

    fun getCitiesFromDB()
    fun getCitiesFromDBSuccess(cities: ArrayList<CityModel>)
    fun getCitiesFromDBError()

    fun showLoading()
    fun hideLoading()
    
}