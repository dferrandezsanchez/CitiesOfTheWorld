package danielferrandez.com.citiesoftheworld.di

import dagger.Component
import danielferrandez.com.citiesoftheworld.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}