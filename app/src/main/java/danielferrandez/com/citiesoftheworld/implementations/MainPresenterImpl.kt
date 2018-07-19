package danielferrandez.com.citiesoftheworld.implementations

import danielferrandez.com.citiesoftheworld.MainActivity
import danielferrandez.com.citiesoftheworld.interfaces.MainInteractor
import danielferrandez.com.citiesoftheworld.interfaces.MainPresenter
import danielferrandez.com.citiesoftheworld.model.CityModel
import java.util.ArrayList


class MainPresenterImpl(var mainView: MainActivity) : MainPresenter {

    private var mInteractor: MainInteractor = MainInteractorImpl(this, mainView)

    override fun getCities(filter:String?, fromScroll: Boolean) {
        mInteractor.getCities(filter, fromScroll)
        mainView.showLoading()
    }

    override fun getCitiesSuccess(items: List<CityModel>) {
        mainView.getCitiesSuccess(items)
    }

    override fun getCitiesError(error: Throwable) {
        mainView.getCitiesError()
    }

    override fun getCitiesFromDB() {
        mInteractor.getCitiesFromDB()
    }

    override fun getCitiesFromDBSuccess(cities: ArrayList<CityModel>) {
        mainView.getCitiesFromDBSuccess(cities)
        mainView.hideLoading()
    }

    override fun getCitiesFromDBError() {
        mainView.getCitiesFromDBError()
        mainView.hideLoading()
    }
}