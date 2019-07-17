# github-user-search
Search Github user profiles in a better way.(MVVM, Dagger2, Kotlin, Navigation Controller etc.)

### Prerequisites
 - Android Studio 3 and above(Recommended: 3.4.2)
 - Gradle version: 3 and above(3.4.2 recommended)
 - Kotlin 1.3.41
 
### Programming Language
 - Kotlin

### Libraries
 - Retrofit REST Client
 - Dagger 2
 - RxJava2
 - Architecture Components(ViewModel, LiveData, Navigation Controller)
 - Glide Image Loading.
 
 ### Features and Screens
  - Search: Users would be able to search a person on Github by entering their username on Github. 
  - Pagination on Search Screen: Pagination applied to the search screen, where if user reach the last element of the page, then new page would be called and the result would be added to the existing list.
  - User Profile: This page can be opened by selecting any user from the searched list. following details would be displayed here: 
      - Avatar image
      - User name
      - Public Gist count
      - Public repos count
  - Collapsing Toolbar: It has a material designed collapsing toolbar with paralax effect.
  - Followers and Following List: List of followers is also there with count.

### Screenshot of the application
<img src = "screenshots/search_result_page.png" width=170 height=280/><img src = "screenshots/user_profile_page.png" width=170 height=280/><img src = "screenshots/share_intent_popup.png" width=170 height=280/><img src = "screenshots/search_empty_page.png" width=170 height=280/><img src = "screenshots/paralax_tab.png" width=170 height=280/>

### Setup
   - Clone the Repo
   - Use Android Studio 3.4.2(If don't want to degrade the gradle and support versions), else use versions according to Android Studio version.
   - Build and Run.
   
