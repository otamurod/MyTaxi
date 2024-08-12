# MyTaxi Android App

## Overview
This project is a simple Android application that demonstrates the use of modern Android development practices. The app fetches the user's live location and displays it on a map with a taxi icon as a marker. The project is built using Kotlin and Jetpack Compose, following the Clean + MVI architecture, and supports both Dark and Light themes.

## Figma
You can find the project prototype over [here](https://www.figma.com/design/IwRIDZ2cWQJeh1ZK9LYVtD/Untitled?node-id=1-17521&t=6BQWqMLV5BBQMDCz-0)

## Features
- **Live Location Tracking:** The app continuously fetches the user's location using a foreground service.
- **Map Integration:** The user's location is displayed on a map using Mapbox or MapLibre, with a custom taxi icon marker.
- **Data Persistence:** Each location update is stored in a local database for future reference.
- **Responsive UI:** The UI is designed to adapt to different screen sizes and orientations, ensuring a consistent experience across devices.
- **Theming:** The app supports both Dark and Light themes, providing a seamless experience in different lighting conditions.

## Tech Stack
- **Kotlin**: Used for all programming logic.
- **Jetpack Compose**: For building the UI components.
- **Mapbox**: For map rendering and displaying the user's location.
- **Clean + MVI Architecture**: Ensuring a scalable and maintainable codebase.
- **Foreground Service**: To keep tracking the user's location even when the app is in the background.
- **Local Database**: For storing the user's location data.

## Requirements
- **Minimum Android Version:** Android 7.0 (API Level 24)
- **Device Orientation:** Portrait mode only

## Setup Instructions
1. Clone this repository:
   ```bash
   git clone https://github.com/otamurod/MyTaxi.git
   cd MyTaxi
2. Open the project in Android Studio.
 
3. Add keystore.properties gradle file at root project directory. Add your MAPBOX_DOWNLOADS_SECRET_TOKEN there

4. Build the project and run it on an Android device or emulator.

## Screenshots

<p align="center">
  <img src="https://github.com/user-attachments/assets/d5747332-159b-4f62-b22b-1fd8fc092d06" alt="Screenshot 1" width="30%">
  <img src="https://github.com/user-attachments/assets/3217093c-7192-4d96-ab7a-7d491396f254" alt="Screenshot 2" width="30%">
  <img src="https://github.com/user-attachments/assets/6b796d4f-19d7-4c31-882a-ddb9c1488a3a" alt="Screenshot 2" width="30%">
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/71b18d28-49ee-4935-9bf9-2d66139700f2" alt="Screenshot 4" width="30%">
  <img src="https://github.com/user-attachments/assets/593b19c9-67f5-454c-bae9-cb9070abe478" alt="Screenshot 5" width="30%">
  <img src="https://github.com/user-attachments/assets/f28edc4f-1abb-432a-a397-7d70195b2b9d" alt="Screenshot 6" width="30%">
</p>
