## Introduction
This is an Android application built using MVVM (Model-View-ViewModel) architecture with Jetpack Compose for a modern, declarative UI. The app demonstrates fetching product data from a remote endpoint, displaying it in a list, showing detailed product views, and includes real-time search functionality.

## Core Features
* Product Listing: Displays a list of products fetched from a designated API endpoint in a LazyColumn for efficient rendering.
* Product Details: Navigates to a detailed screen for each product upon selection, presenting comprehensive information.
* Real-time Search: Includes a search bar that allows users to filter products instantly based on their titles as they type.
* Robust State Management: Implements a refined state management approach to ensure a smooth and responsive user experience, particularly addressing UI update inconsistencies.

## Architecture and Technologies
The application adheres to the MVVM architectural pattern, separating concerns into distinct layers:
* Model: Represents the data layer, including data models and repositories responsible for data fetching (e.g., from network APIs).
* View (Jetpack Compose): The UI layer built entirely with Jetpack Compose, responsible for rendering the UI and reacting to state changes.
* ViewModel: Acts as a bridge between the View and Model, holding UI-related data, handling user interactions, and exposing data streams to the View.
* Dependency Injection: Uses Hilt (based on the ViewModel code) for robust dependency management.
* Retrofit: Leverages Retrofit, a type-safe HTTP client for Android and Java, to handle all network API interactions. Retrofit simplifies the process of making HTTP requests, parsing JSON responses into Kotlin objects, and integrating with Kotlin 
* Kotlin Coroutines & Flows: Utilizes Kotlin Coroutines for asynchronous operations and Kotlin Flows for reactive data streams, enabling efficient and non-blocking data handling.

## Solving the "Flickering" UI State Issue in Product Details
A key improvement in this application addresses a common UI problem: a brief "flicker" where the previous product's details would momentarily appear before the newly selected product's details loaded on the detail screen. This issue has been resolved through a precise state management strategy within the DealDetailViewModel and DealDetailScreen Composables.

#### The Problem
When a user navigated from one product detail to another, the DealDetailScreen would briefly display the data of the previously viewed product before updating to the new product's information. This occurred because the UI wasn't clearing its content fast enough during the transition and data loading phase.

#### The Solution
To eliminate this flicker, the following enhancements were implemented:
Introducing an Initial UI State: A new state, DealDetailUiState.Initial, was added to the DealDetailUiState sealed class. This state explicitly represents a condition where no product detail data is currently loaded or displayed.
The DealDetailViewModel is now initialized with this Initial state. When the DealDetailScreen observes the Initial state, it ensures that any previously displayed content is cleared, presenting a blank or minimal UI.
Immediate Loading State Transition: Upon initiating a new deal fetch via `viewModel.getDealById(id)`, the _uiState in the DealDetailViewModel is immediately set to DealDetailUiState.Loading. ViewModel State Reset with DisposableEffect: A `resetUiState()` function was added to DealDetailViewModel to explicitly set its _uiState back to DealDetailUiState.Initial.

In the DealDetailScreen composable, a `DisposableEffect` is used:

```kotlin
DisposableEffect(viewModel) {
    onDispose {
        viewModel.resetUiState()
    }
}
```
This ensures that whenever the DealDetailScreen composable leaves the composition (e.g., when the user presses the back button and navigates away), the `viewModel.resetUiState()` method is called. By resetting the ViewModel's state, the next time the DealDetailScreen is opened (even for a different product), it starts from a clean Initial state, preventing the old data from momentarily appearing. This combination of an Initial state and explicit state resetting guarantees a cleaner, more responsive, and flicker-free user experience when navigating between product details.
