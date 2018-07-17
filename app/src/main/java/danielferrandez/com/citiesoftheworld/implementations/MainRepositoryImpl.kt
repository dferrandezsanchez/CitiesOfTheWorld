package danielferrandez.com.citiesoftheworld.implementations

import danielferrandez.com.citiesoftheworld.api.ApiServiceInterface
import danielferrandez.com.citiesoftheworld.api.RequestModel
import danielferrandez.com.citiesoftheworld.interfaces.MainRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainRepositoryImpl(private var mainInteractorImpl: MainInteractorImpl) : MainRepository {

    private var disposable: Disposable? = null
    private val apiServiceInterface by lazy {
        ApiServiceInterface.create()
    }

    override fun getCities(page:Int) {
        disposable =
                apiServiceInterface.getCities(page)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { result -> getCitiesSuccess(result) },
                                { error -> getCitiesError(error) }
                        )
    }

    override fun getCitiesSuccess(result: RequestModel) {
        mainInteractorImpl.getCitiesSuccess(result)
    }

    override fun getCitiesError(error: Throwable) {
        mainInteractorImpl.getCitiesError(error)
    }
}