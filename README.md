# Product Search App

This project is a simple Android application that allows users to search for products using the DummyJSON Products Search API.

## Technologies Used
- Kotlin
- Coroutines
- Flow
- Hilt for Dependency Injection
- Paging 3
- **MVI Architecture in Presentation Layer**
- Clean Architecture
- **No XML code** (Programmatically built UI, no Jetpack Compose used)

## Features
- A search input field to enter queries.
- API requests are made as the user types in the input field, with a debounce of 100 ms to minimize unnecessary requests.
- Preserves the order of search responses, ensuring newer results (e.g., for "abcd") are displayed over older ones (e.g., for "abc").
- Displays product titles and images based on search results using a RecyclerView.
- **No XML code**; all UI components are created programmatically.

## Architecture Overview
- **MVI (Model-View-Intent) in Presentation Layer**: Ensures unidirectional data flow and separation of concerns between UI logic and intent processing.
- **Clean Architecture**: Divided into three layers:
    - **Domain Layer**: Contains business logic and use cases.
    - **Data Layer**: Contains repositories and data sources (API interaction).
    - **Presentation Layer**: Uses MVI for managing UI state, intents, and handling user actions.

## Getting Started
1. Clone the repository or download the zip file.
2. Open the project in Android Studio.
3. Build and run the application on an Android device or emulator.

## How to Use
- Start typing in the search bar to query products from the DummyJSON API.
- Results are displayed below the search bar as product titles and images.

## Note
This project is built using **no XML code**. All UI components are programmatically created using Android View-based classes.
