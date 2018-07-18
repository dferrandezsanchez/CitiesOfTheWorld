
# CitiesOfTheWorld
## Kotlin version

On this app our users will be able to see cities of the world with name country and their location.

## Goal

The app should load data from the API, store it locally and then the user interface present it by fetching from the local storage. 

We want you to use some form of local caching of the data retrieved from the API, SQL Lite or Realm are both valid options.  
 
The app should load data from the API, store it locally and then the user interface present it by fetching from the local storage. 

### User Experience

Users will be able to see the data for these cities presented in a clean format.  Users can toggle between a list view and a map view.

On the list view the user can do the following: 
- Load multiple pages of results with an infinite scroll approach. 
- Filter results by city name using a search field. 
- Toggle between the list and a map view.

## Solution

App BottomNavigation Based with listview and mapview fragment to show data requested previously to app and stored in local cache

- Pagination will create new SQLITE regs and then, publish them on map and list
- General Filter applies into map and listview as well, so if user search something, SQLITE table is refresehd with this new results, and then publish new data from cache.

### Used for

- Kotlin
- Retrofit
- RxJava
- Glide
- Sqlite
- MVP pattern
- Recyclerview
