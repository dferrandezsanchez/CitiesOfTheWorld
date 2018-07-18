package danielferrandez.com.citiesoftheworld.di

import dagger.Module
import dagger.Provides
import danielferrandez.com.citiesoftheworld.MainActivity
import danielferrandez.com.citiesoftheworld.implementations.MainPresenterImpl

@Module
class AppModule(var mainActivity: MainActivity) {
    @Provides
    fun providePresenter(): MainPresenterImpl = MainPresenterImpl(mainActivity)
}