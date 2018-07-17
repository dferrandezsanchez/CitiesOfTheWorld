package danielferrandez.com.citiesoftheworld

import android.app.Fragment
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import danielferrandez.com.citiesoftheworld.implementations.MainPresenterImpl
import danielferrandez.com.citiesoftheworld.interfaces.MainView
import danielferrandez.com.citiesoftheworld.model.CityModel
import danielferrandez.com.citiesoftheworld.ui.CitiesListFragment
import danielferrandez.com.citiesoftheworld.ui.MapCitiesFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView {

    private lateinit var citiesListFragment: CitiesListFragment
    private lateinit var mapFragment: MapCitiesFragment
    private lateinit var mPresenter: MainPresenterImpl
    private var firstLoading: Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        initialize()
    }

    private fun initialize() {
        citiesListFragment = CitiesListFragment()
        mapFragment = MapCitiesFragment()
        mPresenter = MainPresenterImpl(this)
        fragmentManager.beginTransaction()
                .replace(R.id.main_content, citiesListFragment)
                .commit()
        getCities()
    }

    override fun getCities() {
        mPresenter.getCities()
    }

    override fun getCitiesSuccess(items: List<CityModel>) {
        citiesListFragment.setData(items)
        mapFragment.setData(items)
    }

    override fun getCitiesError() {
        Log.e("ERROR", "Data not loaded")
    }

    override fun showLoading() {
        if(firstLoading) {
            lyt_progress.visibility = View.VISIBLE
            lyt_main_content.visibility = View.GONE
        }else{
            citiesListFragment.showProgress(true)
        }
    }

    override fun hideLoading() {
        if(firstLoading) {
            lyt_progress.visibility = View.GONE
            lyt_main_content.visibility = View.VISIBLE
            firstLoading = false
        }else{
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
