package danielferrandez.com.citiesoftheworld.api

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiServiceInterface {

    @GET("city?include=country&per_page=20")
    fun getCities(): Observable<RequestModel>

    companion object {
        fun create(): ApiServiceInterface {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://connect-demo.mobile1.io/square1/connect/v1/")
                    .build()

            return retrofit.create(ApiServiceInterface::class.java)
        }
    }
}