# 📌 ArtCover – Track Management
## 🏗 Architecture & Patterns
The project follows the Clean Architecture + MVVM approach, ensuring a clear separation of concerns and improved testability.

## MVVM (Model-View-ViewModel)
- View (UI Layer): Displays data and interacts with the user. (e.g., Fragment/Activity)
- ViewModel (Presentation Layer): Handles business logic for the view and exposes state via StateFlow. (e.g., TrackViewModel)
- Model (Domain/Data Layer): Contains business logic and retrieves data via UseCases and repositories.

## Clean Architecture
- Domain Layer: Defines use cases (UseCase) and data models.
- Data Layer: Implements repositories and data sources (API, Room DB).
- Presentation Layer: Contains ViewModels and UI logic.
  
## 🛠 Technologies & Libraries
We have selected these libraries to ensure performance, maintainability, and testability.

- Jetpack ViewModel	: Manages UI data while surviving configuration changes.
- Kotlin Coroutines & Flow	: Efficient asynchronous handling for data retrieval and state management.
- Hilt (Dagger)	: Simplified and efficient dependency injection.
- Retrofit + OkHttp	: Fast and flexible network communication with request handling and caching.
- Room Database	: Optimized local storage using SQLite for Android.
- Mockito & JUnit	: Unit testing and mocks to ensure code reliability.
- StateFlow	: Reactive state management to avoid LiveData issues (e.g., re-emission after rotation).

## 🎯 Why These Choices?
- MVVM + Clean Architecture: Strict separation of concerns, improved testability, and scalability.
- StateFlow instead of LiveData: Better lifecycle management and state handling in ViewModels.
- Coroutines & Flow: Simplifies asynchronous code with a clean syntax and avoids complex callbacks.
- Hilt (instead of classic Dagger): Easier dependency injection, seamlessly integrated with Jetpack.
- Mockito + JUnit: Easy mocking and fast execution of unit tests.
