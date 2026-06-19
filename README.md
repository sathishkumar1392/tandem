Tandem Community Challenge

An Android application that fetches and displays a language-learning community, built with Kotlin, Jetpack Compose, and Clean Architecture.

Tech Stack


Language: Kotlin
UI: Jetpack Compose, Material 3
Architecture: MVVM + Clean Architecture
Async: Kotlin Coroutines, Flow
Pagination: Paging 3
DI: Koin
Networking: Retrofit, Moshi, OkHttp
Local Persistence: Room
Image Loading: Coil
Testing: JUnit, MockK, Turbine, kotlinx-coroutines-test


Architecture

The app follows Clean Architecture with three layers:

data/        → API service, Room database, PagingSource, Repository implementation, DTO-to-domain mappers
domain/      → Pure Kotlin models, repository interfaces, use cases (zero Android dependencies)
feature/     → ViewModel, Compose UI screens and components

Key design decisions


Pagination via Paging 3: the API is page-based (community_{page}.json) with 20 members per page and a final page that returns fewer than 20 items. PagingSource.nextKey returns null when a page has fewer than 20 results, which stops further requests.
referenceCnt == 0 → "new member": this business rule is computed once in the DTO-to-domain mapper, not in the UI layer, so the UI only ever renders a pre-computed isNew flag.
Like persistence is decoupled from pagination. Liked member IDs are stored in Room and exposed as an independent Flow<Set<String>>. The UI merges like-state into each paged item at render time. This was a deliberate fix: combining like-state directly into the Flow<PagingData<T>> stream (e.g., via combine) causes Paging 3's internal collector to throw IllegalStateException: Attempt to collect twice from pageEventFlow, because every like-toggle would force a new PagingData emission. Keeping the two streams separate avoids this entirely.
Use cases follow single-responsibility: GetCommunityUseCase only fetches data, ToggleLikeUseCase only toggles a like. Each repository method maps to exactly one use case.


Known Limitations


Test coverage covers PagingSource, RepositoryImpl, and ViewModel logic. UI/Compose tests were not included due to time constraints.
No offline caching of the member list itself — only like-state is persisted, per the challenge's explicit requirement.


Running the Project


Clone the repo
Open in Android Studio
Sync Gradle
Run on a device/emulator (minSdk 24)


A release APK is available in the [Releases section](https://github.com/sathishkumar1392/tandem/releases).