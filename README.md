# FocusLock App

A productivity Android app designed for students who want to improve focus by limiting distractions during study time. Built as a capstone project for ICS 499 - Software Engineering, Spring 2025.

## Features

- **Lock Mode**: Activates Android’s Lock Task Mode to prevent access to other apps during a study session.
- **Custom Study Timer**: Users can select hours, minutes, and seconds to create their own focused session.
- **Single User Type**: Simplified experience — no multiple roles. Anyone can register and use the app.
- **Firebase Integration**: Stores user information securely in Firestore and supports login/logout.
- **Welcome Message**: Personalized welcome message based on your registered first name.

## Screenshots

Coming soon...

## Tech Stack

- Java
- Android Studio
- Firebase Authentication & Firestore
- XML Layouts
- Device Admin APIs (Lock Task Mode)

## How It Works

1. **Register an account** (name, email, password).
2. **Login** with your credentials.
3. **Choose your study duration** using a scrollable time picker.
4. **Tap "Start"** to activate Lock Mode and begin the countdown.
5. **App restricts access** to other apps until the timer ends or is stopped.
6. **Logout** anytime from the dashboard.

## Permissions Used

- Device Admin permission for Lock Task Mode.

> Note: You must enable device admin permission for the app to enforce lock mode.

## Developers
- Ahmed Abdullahi - [GitHub](https://github.com/ahmedforeal9) 
- Neeju Singh — [GitHub](https://github.com/NEEJUSINGH)
- Yod N Xiong - [GitHub](https://github.com/J3lloZ)

## 📂 Folder Structure
```
app/
├── java/com/example/ics449app/
│ ├── DashboardActivity.java # Main dashboard with timer + lock
│ ├── RegisterActivity.java # User registration with Firebase
│ ├── SignInActivity.java # Firebase login
│ ├── User.java # User model
│ └── MyDeviceAdminReceiver.java # Enables device lock mode
├── res/layout/
│ └── dashboard_activity.xml # UI for main dashboard
│ └── register_activity.xml # UI for registration screen
└── google-services.json # Firebase config

Installation & Setup (User Manual)

System Requirements

Android Studio (Electric Eel or higher)

Android Emulator or physical device (API 31+)

Java 11 SDK

Firebase account

Step-by-Step Installation

Clone the Repo:

git clone https://github.com/J3lloZ/ICS-499-Spring-2025.git
cd ICS-499-Spring-2025

Firebase Setup:

Create a Firebase project at https://console.firebase.google.com/

Add Android app with your app's package name com.example.ics449app

Download google-services.json and place it inside:

app/google-services.json

Enable Firebase Features:

Firestore Database (in test mode)

Email/Password authentication

Build & Run the App:

./gradlew clean build installDebug

Or simply run from Android Studio.

```
