package danielferrandez.com.citiesoftheworld

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.SearchView
import danielferrandez.com.citiesoftheworld.di.AppModule
import danielferrandez.com.citiesoftheworld.di.DaggerAppComponent
import danielferrandez.com.citiesoftheworld.implementations.MainPresenterImpl
import danielferrandez.com.citiesoftheworld.interfaces.MainView
import danielferrandez.com.citiesoftheworld.model.CityModel
import danielferrandez.com.citiesoftheworld.ui.CitiesListFragment
import danielferrandez.com.citiesoftheworld.ui.MapCitiesFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(), MainView, SearchView.OnQueryTextListener {

    private lateinit var citiesListFragment: CitiesListFragment
    private lateinit var mapFragment: MapCitiesFragment
    private var firstLoading: Boolean = true
    private var querySend: String = ""
    private var isSearching = false

    @Inject
    lateinit var mainPresenter: MainPresenterImpl


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        initialize()
    }

    fun initialize() {
        citiesListFragment = CitiesListFragment()
        mapFragment = MapCitiesFragment()
        DaggerAppComponent.builder().appModule(AppModule(this)).build().inject(this)
        main_filter.setOnQueryTextListener(this)
        fragmentManager.beginTransaction()
                .replace(R.id.main_content, citiesListFragment)
                .commit()
        getCities(false)
    }

    override fun getCities(fromScroll: Boolean) {
        mainPresenter.getCities(querySend, fromScroll)
    }

    override fun getCitiesSuccess(items: List<CityModel>) {
        isSearching = false
//        citiesListFragment.setData(items)
//        mapFragment.setData(items)
        getCitiesFromDB()
    }

    override fun getCitiesError() {
        isSearching = false
        Log.e("ERROR", "Data not loaded")
    }

    override fun getCitiesFromDB() {
        mainPresenter.getCitiesFromDB()
    }

    override fun getCitiesFromDBSuccess(cities: ArrayList<CityModel>) {
        citiesListFragment.setData(cities)
        mapFragment.setData(cities)
    }

    override fun getCitiesFromDBError() {
        Log.e("ERROR", "Data not loaded from DB")
    }


    override fun showLoading() {
        if (firstLoading) {
            lyt_progress.visibility = View.VISIBLE
            lyt_main_content.visibility = View.GONE
        } else {
            citiesListFragment.showProgress(true)
        }
    }

    override fun hideLoading() {
        if (firstLoading) {
            lyt_progress.visibility = View.GONE
            lyt_main_content.visibility = View.VISIBLE
            firstLoading = false
        } else {
            citiesListFragment.showProgress(false)
        }
    }

    private fun changeToList() {
        fragmentManager.beginTransaction()
                .replace(R.id.main_content, citiesListFragment)
                .commit()
    }

    private fun changeToMap() {
        fragmentManager.beginTransaction()
                .replace(R.id.main_content, mapFragment)
                .commit()
    }

    override fun onQueryTextSubmit(newText: String): Boolean {
        querySend = newText
        if (!isSearching) {
            isSearching = true
            citiesListFragment.clearList()
            mapFragment.clearList()
            getCities(false)
        }
        return false
    }

    override fun onQueryTextChange(p0: String?): Boolean {

        Log.i("Texto", p0)
        return true
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_list -> {
                changeToList()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_map -> {
                changeToMap()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


}
