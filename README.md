# M-Commerce App

![Build Status](https://img.shields.io/badge/build-passing-brightgreen.svg)
![License](https://img.shields.io/badge/license-MIT-blue.svg)

A modern, scalable Android mobile commerce app built with Kotlin, Jetpack Compose, and Clean Architecture. Designed for seamless product browsing, cart management, and currency selection.

This project also includes a dedicated Admin App for managing products and other store functionalities. The entire platform integrates with Shopify's Storefront API, utilizing GraphQL to efficiently fetch and manipulate store data.
---

## Table of Contents

* [Features](#features)
* [Tech Stack](#tech-stack)
* [Libraries Used](#libraries-used)
* [Folder Structure](#folder-structure)
* [Installation & Getting Started](#installation--getting-started)
* [API Integration](#api-integration)
* [Screenshots](#screenshots)
* [Contributing](#contributing)
* [Team Members](#team-members)
* [License](#license)
* [Contact](#contact)

---

## Features

* Product listing with category filtering
* Product search 
* Shopping cart and dynamic checkout flow
* Currency selection and live exchange rates
* User profile management with saved preferences
* MVVM architecture for state management
* Clean Architecture: Data, Domain, Presentation
* Retrofit for API networking
---

## Tech Stack

* **Language:** Kotlin
* **UI:** Jetpack Compose
* **Architecture:** MVVM + Clean Architecture
* **Networking:** Retrofit, storeFront SDK
* **State Management:** StateFlow, ViewModel
* **Persistence:** SharedPreferences (and Room if added)
* **Backend/Auth:** Firebase (optional, if used)

---

## Libraries Used

### Core & Compose

* androidx.core: core-ktx
* androidx.activity: activity-compose
* androidx.compose: UI, Material3, Foundation, Icons
* Google Play Services Location

### Navigation & Architecture

* androidx.navigation: navigation-compose
* androidx.hilt: hilt-navigation-compose
* androidx.lifecycle: lifecycle-runtime-ktx, viewmodel-compose
* Hilt for DI


### Networking & Persistence

* Retrofit 
* SharedPreferences
* Firebase Auth & Firestore

### UI & Media

* Coil for image loading
* Lottie Compose for animations
* Google Maps Compose

### Payments

* Stripe Android SDK

### Testing

* JUnit
* Mockk
* Hamcrest

---

## Folder Structure

```
M-Commerce-App/
├── features/            # Feature-specific modules (cart, product, profile, etc.)
├── core/                # Common utilities, design system, and base classes
├── data/                # Data layer: repositories, API, local data sources
├── domain/              # Business logic, use cases, and models
├── presentation/        # UI layer, Composables, and ViewModels
```

---

## Installation & Getting Started

### Prerequisites

* Android Studio Giraffe or newer
* Kotlin 1.9+
* Gradle 8.0+
* JDK 17

### Steps

```bash
# Clone the repo
git clone https://github.com/Ayat-Gamal/M-Commerce-App.git
cd M-Commerce-App

# Open in Android Studio and build the project
```

### API Keys

* If using currency APIs (e.g. [FreeCurrencyAPI](https://www.exchangerate-api.com/)), add your API key to `local.properties`:

  ```properties
  CURRENCY_API_KEY=your_api_key_here
  ```
* For Firebase: ensure `google-services.json` is in the appropriate module.

---

## API Integration
* shopify storeFront Api
* Uses **FreeCurrencyAPI** to fetch live exchange rates
* Retrofit is used to handle network calls
* JSON parsing via Kotlin Serialization or Gson

[FreeCurrencyAPI Documentation](https://freecurrencyapi.com/docs/latest)

---

You can add screenshots of:

* Product list
* Cart screen
* Checkout screen
* Currency selector
* Profile screen


---

## Team Members

### Ahmed Mohamed Saad

**Mobile Software Engineer**
[GitHub](https://github.com/ahmedsaad207) | [LinkedIn](https://www.linkedin.com/in/dev-ahmed-saad/)

---

### Ayat Gamal Mustafa

**Mobile Software Engineer**
[GitHub](https://github.com/ahmedsaad207) | [LinkedIn](https://www.linkedin.com/in/ayat-gamal-700946229/)

---

### Mohamed Tag El-Deen Ahmed

**Mobile Software Engineer**
[LinkedIn](https://www.linkedin.com/in/mohamed-tag-eldeen)

---

### Youssif Nasser Mostafa

**Mobile Software Engineer**
[GitHub](https://github.com/JoeTP) | [LinkedIn](https://www.linkedin.com/in/youssif-nasser/)

---

## License

This project is licensed under the [MIT License](LICENSE).

---

## Contact

**Maintainer:** Ayat Gamal
[GitHub Profile](https://github.com/Ayat-Gamal)
Feel free to open issues or discussions for questions and ideas.
