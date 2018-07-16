package danielferrandez.com.citiesoftheworld.interfaces

import danielferrandez.com.citiesoftheworld.api.RequestModel

interface MainPresenter{
    fun getCities()
    fun getCitiesSuccess(result: RequestModel)
    fun getCitiesError(error: Throwable)

}