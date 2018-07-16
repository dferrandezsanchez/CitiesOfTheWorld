package danielferrandez.com.citiesoftheworld.implementations

import danielferrandez.com.citiesoftheworld.MainActivity
import danielferrandez.com.citiesoftheworld.api.RequestModel
import danielferrandez.com.citiesoftheworld.interfaces.MainPresenter
import danielferrandez.com.citiesoftheworld.interfaces.MainRepository

class MainPresenterImpl(private var mainView: MainActivity) : MainPresenter {

    private var mRepository: MainRepository = MainRepositoryImpl(this)

    override fun getCities() {
        mRepository.getCities()
    }

    override fun getCitiesSuccess(result: RequestModel) {
        mainView.getCitiesSuccess()
    }

    override fun getCitiesError(error: Throwable) {
        mainView.getCitiesError()
    }
}