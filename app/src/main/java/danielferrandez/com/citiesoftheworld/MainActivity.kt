package danielferrandez.com.citiesoftheworld

import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.Toast
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
        // Work around to reset filter
        main_filter.setOnCloseListener {
            resetQuery()
            return@setOnCloseListener true
        }
        changeToList()
        getCities(false)
    }

    override fun getCities(fromScroll: Boolean) {
        if(fromScroll){
            firstLoading = false
        }
        mainPresenter.getCities(querySend, fromScroll)
    }

    override fun getCitiesSuccess(items: List<CityModel>) {
        isSearching = false
        getCitiesFromDB()
    }

    override fun getCitiesError() {
        isSearching = false
        showError("Ups, looks like something went wrong, try again please!")
    }

    override fun getCitiesFromDB() {
        mainPresenter.getCitiesFromDB()
    }

    override fun getCitiesFromDBSuccess(cities: ArrayList<CityModel>) {
        if (cities.size != 0) {
            citiesListFragment.setData(cities)
            mapFragment.setData(cities)
        }else if(firstLoading && cities.size == 0){
            citiesListFragment.showEmptyLayout(true)
            showError(getString(R.string.no_results))
        }
    }

    override fun getCitiesFromDBError() {
        showError("Ups, looks like something went wrong, try again please!")
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
        if (p0!!.isEmpty()) {
            resetQuery()
        }
        return true
    }

    fun resetQuery(){
        citiesListFragment.clearList()
        mapFragment.clearList()
        querySend = ""
        getCities(false)
    }

    fun showError(message:String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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
