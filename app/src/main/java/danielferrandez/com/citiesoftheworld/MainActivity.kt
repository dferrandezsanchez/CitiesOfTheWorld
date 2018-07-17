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

    private lateinit var citiesListFragment: Fragment
    private lateinit var mapFragment: Fragment
    private lateinit var mPresenter: MainPresenterImpl


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
                .addToBackStack("List")
                .commit()
        getCities()
    }

    override fun getCities() {
        mPresenter.getCities()
    }

    override fun getCitiesSuccess(items: List<CityModel>) {
        (citiesListFragment as CitiesListFragment).setData(items)
    }

    override fun getCitiesError() {
        Log.e("ERROR", "Data not loaded")
    }

    override fun showLoading() {
        lyt_progress.visibility = View.VISIBLE
        lyt_main_content.visibility = View.GONE
    }

    override fun hideLoading() {
        lyt_progress.visibility = View.GONE
        lyt_main_content.visibility = View.VISIBLE
    }

    private fun changeToList() {
        fragmentManager.beginTransaction()
                .replace(R.id.main_content, citiesListFragment)
                .addToBackStack("List")
                .commit()
    }

    private fun changeToMap() {
        fragmentManager.beginTransaction()
                .replace(R.id.main_content, mapFragment)
                .addToBackStack("Map")
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
