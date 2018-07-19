package danielferrandez.com.citiesoftheworld.ui

import android.app.Fragment
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.LinearLayout
import danielferrandez.com.citiesoftheworld.MainActivity
import danielferrandez.com.citiesoftheworld.R
import danielferrandez.com.citiesoftheworld.model.CityModel
import danielferrandez.com.citiesoftheworld.ui.adapter.CitiesListAdapter
import kotlinx.android.synthetic.main.fragment_list.*


class CitiesListFragment : Fragment() {

    private var rootView: View? = null
    var citiesListApi: ArrayList<CityModel> = ArrayList()
    var rv_cities: RecyclerView? = null
    var swiperefresh: SwipeRefreshLayout? = null
    var lyt_empty_results: LinearLayout? = null

    private var scrolling: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_list, container, false)
            complete(rootView)
        }
        return rootView
    }

    fun complete(rootView: View?) {
        rv_cities = rootView?.findViewById(R.id.rv_cities) as RecyclerView
        rv_cities!!.layoutManager = LinearLayoutManager(activity)
        rv_cities!!.adapter = CitiesListAdapter(citiesListApi, activity)
        rv_cities!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (scrolling && (rv_cities!!.layoutManager.childCount + (rv_cities!!.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                                >= rv_cities!!.layoutManager.itemCount - 5)) {
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

        //Swipe to refresh call to handleGetAllProducts setting the page var = 1
        swiperefresh = rootView.findViewById(R.id.swiperefresh) as SwipeRefreshLayout
        lyt_empty_results = rootView.findViewById(R.id.lyt_empty_results) as LinearLayout
        swiperefresh!!.setOnRefreshListener {
            (activity as MainActivity).getCities(false)
        }
    }

    fun setData(items: List<CityModel>) {
        swiperefresh?.isRefreshing = false
        showEmptyLayout(false)
        citiesListApi.addAll(items)
        if (rv_cities != null) {
            rv_cities!!.adapter.notifyDataSetChanged()
        }
    }

    fun clearList() {
        citiesListApi.clear()
        if (rv_cities != null) {
            rv_cities!!.adapter.notifyDataSetChanged()
        }
    }

    fun showEmptyLayout(show: Boolean) {
        if (show) {
            lyt_empty_results?.visibility = View.VISIBLE
            swiperefresh?.visibility = View.GONE
        } else {
            lyt_empty_results?.visibility = View.GONE
            swiperefresh?.visibility = View.VISIBLE
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