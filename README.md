App Name: Lemondo Movies
Version: 1.0
Description:
Lemondo Movies is an Android app that fetches data from TMDB and displays it to the user. App has functionality to save favorite movies or view movie details.


Multi-Module Application Structure
This application is structured as a multi-module project, utilizing convention plugins to manage dependencies and module relationships. The project consists of core and feature modules, each serving specific purposes:

Core Modules:
Data: Contains data access logic, repositories, and data sources.
Network: Provides network connectivity and API communication via retrofit.
Domain: Encapsulates business logic and domain-specific models.
Common: Includes shared utilities.
UI: Defines common UI elements and styles.

Feature Modules:
Movie Collection: Displays paginated movie posters from themoviedb.org.
Movie Details: Lists details of the selected movie from the movie collection feature.
Favorites: Displays the user's favorite movie posters.
Module Dependencies
The app module depends on each feature module, ensuring that all necessary components are available. This is achieved through the following dependencies in the app module's build.gradle file:

dependencies {
    implementation project(":feature:movie-collection")
    implementation project(":feature:movie-details")
    implementation project(":feature:favorites")
}
