package danielferrandez.com.citiesoftheworld.implementations

import danielferrandez.com.citiesoftheworld.MainActivity
import danielferrandez.com.citiesoftheworld.api.RequestModel
import danielferrandez.com.citiesoftheworld.db.CitiesDBHelper
import danielferrandez.com.citiesoftheworld.interfaces.MainInteractor
import danielferrandez.com.citiesoftheworld.interfaces.MainRepository
import danielferrandez.com.citiesoftheworld.model.CityModel
import java.util.ArrayList

class MainInteractorImpl(private var mainPresenter: MainPresenterImpl, mainView: MainActivity) : MainInteractor {

    private var mRepository: MainRepository = MainRepositoryImpl(this)
    private var page:Int = 1
    private var citiesDBHelper: CitiesDBHelper = CitiesDBHelper(mainView)

    private var cities: ArrayList<CityModel> = ArrayList()

    override fun getCities(filter:String?, fromScroll: Boolean) {
        if(fromScroll){
            page++
        }else{
            page = 1
            citiesDBHelper.truncateTable()
            cities.clear()
        }
        mRepository.getCities(page, filter)
    }

    override fun getCitiesSuccess(result: RequestModel) {
        for (element in result.data.items) {
            citiesDBHelper.insertCity(element)
        }
        mainPresenter.getCitiesSuccess(result.data.items)
    }

    override fun getCitiesError(error: Throwable) {
        mainPresenter.getCitiesError(error)
    }



    override fun getCitiesFromDB() {
        try {
            cities = citiesDBHelper.selectAllCities(cities.size)
            getCitiesFromDBSuccess(cities)
        } catch (e: Exception) {
            getCitiesFromDBError()
        }
    }

    override fun getCitiesFromDBSuccess(cities: ArrayList<CityModel>) {
        mainPresenter.getCitiesFromDBSuccess(cities)
    }

    override fun getCitiesFromDBError() {
        mainPresenter.getCitiesFromDBError()
    }

}