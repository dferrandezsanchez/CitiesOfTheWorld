package danielferrandez.com.citiesoftheworld.interfaces

import danielferrandez.com.citiesoftheworld.api.RequestModel


interface MainInteractor {
    fun getCities()
    fun getCitiesSuccess(result: RequestModel)
    fun getCitiesError(error: Throwable)

    fun getCitiesFromDB()
    fun getCitiesFromDBSuccess()
    fun getCitiesFromDBError()
}