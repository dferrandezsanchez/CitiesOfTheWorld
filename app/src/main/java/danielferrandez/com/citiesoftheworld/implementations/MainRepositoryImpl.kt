package danielferrandez.com.citiesoftheworld.implementations

import danielferrandez.com.citiesoftheworld.api.ApiServiceInterface
import danielferrandez.com.citiesoftheworld.api.RequestModel
import danielferrandez.com.citiesoftheworld.interfaces.MainRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainRepositoryImpl(private var mainPresenterImpl: MainPresenterImpl) : MainRepository {

    private var disposable: Disposable? = null
    private val apiServiceInterface by lazy {
        ApiServiceInterface.create()
    }

    override fun getCities() {
        disposable =
                apiServiceInterface.getCities()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { result -> getCitiesSuccess(result)},
                                { error ->  getCitiesError(error)}
                        )
    }

    override fun getCitiesSuccess(result: RequestModel) {
        mainPresenterImpl.getCitiesSuccess(result)
    }

    override fun getCitiesError(error: Throwable) {
        mainPresenterImpl.getCitiesError(error)
    }
}