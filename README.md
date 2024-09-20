# Weather Space

Welcome to **Weather Space**, a Kotlin-based Android application that fetches live weather data based on your current location. Built using modern Android development libraries and best practices, this project demonstrates a robust architecture and clean code principles.

## Phone Screenshots
<p float="center">
    <img width="200" src="https://github.com/santhosh-anbazhagan/WeatherSpace/blob/master/app/src/main/java/com/alienspace/weatherspace/samples/Screenshot_1.png">
    <img width="200" src="https://github.com/santhosh-anbazhagan/WeatherSpace/blob/master/app/src/main/java/com/alienspace/weatherspace/samples/Screenshot_2.png">
    <img width="200" src="https://github.com/santhosh-anbazhagan/WeatherSpace/blob/master/app/src/main/java/com/alienspace/weatherspace/samples/Screenshot_3.png">
</p>

## Features

- **Live Location Tracking**: Automatically fetches current location coordinates.
- **Weather Data Fetching**: Retrieves weather information from [Open Meteo API](https://api.open-meteo.com).
- **Modern UI**: Utilizes Jetpack Compose for a responsive and intuitive user interface.
- **Dependency Injection**: Employs Hilt for seamless dependency management.
- **Network Operations**: Uses Retrofit for efficient API calls.
- **Logging**: Integrated Timber for structured logging.
- **Navigation**: Implements Jetpack Navigation for a smooth user experience.
- **Architecture**: Follows the Model-View-Intent (MVI) architecture for clear separation of concerns.

## Technologies Used

- **Kotlin**: Primary programming language for the app.
- **Jetpack Compose**: For building the UI.
- **Hilt**: For dependency injection.
- **Retrofit**: For making network requests.
- **Timber**: For logging.
- **Jetpack Navigation**: For navigation between screens.
- **Open Meteo API**: For weather data.

## Getting Started

### Prerequisites

- Android Studio with Kotlin support
- Basic knowledge of Kotlin and Android development

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/weather-space.git
   ```
2. Navigate to the project directory:
   ```bash
   cd weather-space
   ```
3. Open the project in Android Studio.
4. Build the project to download dependencies.

### Configuration

1. Add necessary permissions in the `AndroidManifest.xml`:
   ```xml
   <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
   <uses-permission android:name="android.permission.INTERNET" />
   <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
   <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
   ```

2. Set up your API key and other configuration in `build.gradle` if needed.

### Running the App

1. Connect your Android device or start an emulator.
2. Run the application from Android Studio.

## Usage

- Open the app, and it will request permission to access your location.
- Once granted, it fetches the current coordinates and displays the current weather information.

## Architecture Overview

The app is built following the MVI architecture pattern:

- **Model**: Represents the data and business logic.
- **View**: Composed UI elements that display data to the user.
- **Intent**: User interactions and actions that trigger state changes.

## Contributing

We welcome contributions! Please fork the repository and submit a pull request. 

### Guidelines

1. Ensure your code follows the project's style guide.
2. Write tests for new features and fixes.
3. Document any major changes.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more information.

## Contact

For questions or inquiries, please contact [santhosh-anbazhagan](santhoshanbazhagan1910@gmail.com).

---

Happy coding! 🌦️
