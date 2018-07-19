package danielferrandez.com.citiesoftheworld.interfaces

import danielferrandez.com.citiesoftheworld.api.RequestModel

interface MainRepository{
    fun getCities(page: Int,perPage: Int, filter:String?)
    fun getCitiesSuccess(result: RequestModel)
    fun getCitiesError(error: Throwable)
}