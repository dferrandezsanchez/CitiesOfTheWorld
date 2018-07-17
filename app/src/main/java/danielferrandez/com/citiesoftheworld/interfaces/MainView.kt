package danielferrandez.com.citiesoftheworld.interfaces

import danielferrandez.com.citiesoftheworld.model.CityModel

interface MainView {

    fun getCities(fromScroll: Boolean)
    fun getCitiesSuccess(items:List<CityModel>)
    fun getCitiesError()

    fun showLoading()
    fun hideLoading()
    
}