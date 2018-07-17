package danielferrandez.com.citiesoftheworld.ui

import android.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import danielferrandez.com.citiesoftheworld.MainActivity
import danielferrandez.com.citiesoftheworld.R
import danielferrandez.com.citiesoftheworld.model.CityModel


class MapCitiesFragment : Fragment(), OnMapReadyCallback {

    private var mMap: GoogleMap? = null
    private var rootView: View? = null
    var citiesListApi: ArrayList<CityModel> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_map, container, false)
        var mapFragment = childFragmentManager.findFragmentById(R.id.map) as MapFragment
        mapFragment.getMapAsync(this)
        return rootView
    }

    // In java I can resume Map state, not working in Kotlin for unknown reason
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Add a marker in Sydney and move the camera
        setupMap()
    }

    fun setData(items: List<CityModel>) {
        citiesListApi.addAll(items)
    }

    fun setupMap() {
        // Add a marker in Sydney and move the camera
        for(element in citiesListApi) {
            mMap?.addMarker(MarkerOptions().position(LatLng(element.lat, element.lng)).title(element.name))
        }
    }
}