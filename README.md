# weather_app
Weather Application

This application displays weather information returned by the API http://api.openweathermap.org/ using the endpoint 'Cities in cycle' and passing it San Jose, Costa Rica coordinates. In order for this application to work you must set an API KEY inside WeatherService.

Features:
* Uses material design components such as CoordinatorLayout, DrawerLayout, Appbar, Toolbar, CardView, RecyclerView, Snackbar.
* Retrieves the weather information into the UI using Retrofit/Rx libraries.
* Displays wheather icons using Glide.
* Displays a master/detail flow using fragments, and it binds both views in tablets with more than 600dp.
* Provides user feedback when there is no connectivity or the API does not respond.
* Provides a swipe to refresh functionality.

TO DO's:
* Store weather information in a database and change WeatherService into a Service with an alarm to auto sync data.
* Handle saved instance state to prevent losing the app's state.
* Add a settings activity to allow the user to choose the API parameters to use.
* Improve UI design.
