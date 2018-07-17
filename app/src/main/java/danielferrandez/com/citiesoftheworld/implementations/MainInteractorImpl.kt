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

    private lateinit var cities: ArrayList<CityModel>

    override fun getCities() {
//        getCitiesFromDB()
//        if(cities.size>0){
//            getCitiesFromDBSuccess()
//        }else{
//            mRepository.getCities(page)
//        }
        mRepository.getCities(page)
    }

    override fun getCitiesSuccess(result: RequestModel) {
        for (element in result.data.items) {
            citiesDBHelper.insertCity(element)
        }
        if(result.data.pagination.current_page != result.data.pagination.last_page){
            this.page++
        }
        mainPresenter.getCitiesSuccess(result.data.items)
    }

    override fun getCitiesError(error: Throwable) {
        mainPresenter.getCitiesError(error)
    }



    override fun getCitiesFromDB() {
        cities = citiesDBHelper.selectAllCities()
    }

    override fun getCitiesFromDBSuccess() {
        mainPresenter.getCitiesSuccess(cities)
    }

    override fun getCitiesFromDBError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}