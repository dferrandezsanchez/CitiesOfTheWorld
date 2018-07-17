package danielferrandez.com.citiesoftheworld.interfaces

import danielferrandez.com.citiesoftheworld.model.CityModel

interface MainPresenter{
    fun getCities(filter:String?, fromScroll: Boolean)
    fun getCitiesSuccess(items: List<CityModel>)
    fun getCitiesError(error: Throwable)

}