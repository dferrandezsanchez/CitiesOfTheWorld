
# CitiesOfTheWorld
## Kotlin version

On this app our users will be able to see cities of the world with name country and their location.

## Goal

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

### Used in project

- Kotlin
- Retrofit
- RxJava
- Sqlite
- MVP pattern
- Recyclerview
- Dagger 2
- Espresso

### How it works?

#### General paged results list

- On the first run, app make request to API to obtain cities and store firs page locally. When user scrolls to retrieve more results, before arrive to bottom, apps lauchn new query to obtain next page results, and repeat this process each time user is near to lasts results until last page

#### Filtered paged results

- User can filter results from list and map, then if user scrolls down in list, app request for more results as in general paged scenario but with filtered data from API and stored in cache previously of updating data on list and map

- As SeachView component does not include "empty" queries, there is implemented a workaround to reset filters when user tap on cross icon or remove all text from search field

#### Empty Scenario

- If search does not generate any results, app shows Toast and empty layout on map to let user know there is no results for his request

#### Testing

- There is a simple test example using Espresso checking that clicking on map navigation option, app shows map view
