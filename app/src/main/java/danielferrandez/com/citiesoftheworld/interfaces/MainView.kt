package danielferrandez.com.citiesoftheworld.interfaces

interface MainView {

    fun getCities()
    fun getCitiesSuccess()
    fun getCitiesError()

    fun showLoading()
    fun hideLoading()
    
}