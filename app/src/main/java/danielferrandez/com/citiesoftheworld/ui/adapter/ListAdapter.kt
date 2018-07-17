package danielferrandez.com.citiesoftheworld.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import danielferrandez.com.citiesoftheworld.R
import danielferrandez.com.citiesoftheworld.model.CityModel
import kotlinx.android.synthetic.main.list_city_item.view.*

class CitiesListAdapter (val items: ArrayList<CityModel>, private val context: Context) : RecyclerView.Adapter<CitiesListAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_city_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindUsers(items.get(position), context)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindUsers(cityModel: CityModel, context: Context){
            itemView.tv_city.text = (cityModel.name)
            itemView.tv_country.text = cityModel.country.name
        }
    }

}