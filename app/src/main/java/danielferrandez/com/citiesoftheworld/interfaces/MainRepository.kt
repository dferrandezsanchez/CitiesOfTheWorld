package danielferrandez.com.citiesoftheworld.interfaces

import danielferrandez.com.citiesoftheworld.api.RequestModel

interface MainRepository{
    fun getCities()
    fun getCitiesSuccess(result: RequestModel)
    fun getCitiesError(error: Throwable)
}