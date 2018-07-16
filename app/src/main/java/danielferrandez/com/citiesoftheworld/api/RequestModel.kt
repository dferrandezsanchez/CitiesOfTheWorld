package danielferrandez.com.citiesoftheworld.api

import danielferrandez.com.citiesoftheworld.model.CityModel

data class RequestModel(val data: Data)

data class Data(val items:List<CityModel>, val pagination:Pagination)
data class Pagination(val current_page:Int, val last_page:Int, val per_page:Int, val total:Int)