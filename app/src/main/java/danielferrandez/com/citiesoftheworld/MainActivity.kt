package danielferrandez.com.citiesoftheworld

import android.app.Fragment
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import danielferrandez.com.citiesoftheworld.implementations.MainPresenterImpl
import danielferrandez.com.citiesoftheworld.interfaces.MainView
import danielferrandez.com.citiesoftheworld.ui.ListFragment
import danielferrandez.com.citiesoftheworld.ui.MapFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_list.*

class MainActivity : AppCompatActivity(), MainView {
    private val listFragment:Fragment = ListFragment()
    private val mapFragment: Fragment = MapFragment()
    private lateinit var mPresenter: MainPresenterImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        fragmentManager.beginTransaction()
                .add(R.id.main_content, listFragment)
                .addToBackStack(null)
                .commit()
        initialize()
    }

    private fun initialize() {
        mPresenter = MainPresenterImpl(this)
        getCities()
    }

    override fun getCities() {
        mPresenter.getCities()
    }

    override fun getCitiesSuccess() {
        Log.i("SUCCESS","Data loaded")
    }

    override fun getCitiesError() {
        Log.e("ERROR","Data not loaded")
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
                .replace(R.id.main_content, listFragment)
                .addToBackStack(null)
                .commit()
    }

    private fun changeToMap(){
        fragmentManager.beginTransaction()
                .replace(R.id.main_content, mapFragment)
                .addToBackStack(null)
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
