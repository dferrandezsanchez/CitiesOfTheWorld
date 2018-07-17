package danielferrandez.com.citiesoftheworld.ui

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import danielferrandez.com.citiesoftheworld.MainActivity
import danielferrandez.com.citiesoftheworld.R
import danielferrandez.com.citiesoftheworld.model.CityModel
import danielferrandez.com.citiesoftheworld.ui.adapter.CitiesListAdapter
import kotlinx.android.synthetic.main.fragment_list.*


class CitiesListFragment : Fragment() {

    private var rootView: View? = null
    var citiesListApi: ArrayList<CityModel> = ArrayList()

    private var scrolling: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_list, container, false)
            complete(rootView)
        }
        return rootView
    }

    fun complete(rootView: View?) {
        var rv_cities = rootView?.findViewById(R.id.rv_cities) as RecyclerView
        rv_cities.layoutManager = LinearLayoutManager(activity)
        rv_cities.adapter = CitiesListAdapter(citiesListApi, activity)
        rv_cities.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (scrolling && (rv_cities.layoutManager.childCount + (rv_cities.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                                >= rv_cities.layoutManager.itemCount - 5)) {
                    scrolling = false
                    (activity as MainActivity).getCities(true)
                }

            }

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                    scrolling = true
            }
        })
    }

    fun setData(items: List<CityModel>) {
        citiesListApi.addAll(items)
        if (rv_cities != null) {
            rv_cities.adapter.notifyDataSetChanged()
            Log.e("AÑADIDOS ELEMENTOS", "TOTAL " + rv_cities.adapter.itemCount)
        }
    }

    fun clearList(){
        citiesListApi.clear()
        if (rv_cities != null) {
            rv_cities.adapter.notifyDataSetChanged()
        }
    }

    fun showProgress(show: Boolean) {
        if (show) {
            pb_loading?.visibility = View.VISIBLE
        } else {
            pb_loading?.visibility = View.GONE
        }

    }
}