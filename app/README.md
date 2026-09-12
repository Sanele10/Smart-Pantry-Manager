# Smart Pantry Manager

## Overview
Smart Pantry Manager is a native Android application developed as a final-year IT project for Richfield. It allows users to track household ingredients, monitor stock levels, and generate recipe suggestions based strictly on the items currently available in their local pantry database.

## Technical Stack
* **Language:** Java
* **UI/UX:** XML (Material Design principles, Custom RecyclerView Adapters)
* **Local Storage:** SQLite (via SQLiteOpenHelper)
* **Architecture:** MVC (Model-View-Controller) based Activity structure

## Database implementation (SQLite)
The application utilizes a local SQLite database for offline persistence, bypassing the need for cloud dependency.
* **`DatabaseHelper.java`:** Manages table creation and versioning.
* **CRUD Operations:** Supports full lifecycle data management (Create, Read, Update, Delete) mapped directly to POJO models (`Ingredient.java`).
* **Efficiency:** Database cursors are closed immediately after execution to prevent memory leaks.

## Run Instructions
1. Clone or download the repository to your local machine.
2. Open the project folder in Android Studio.
3. Allow Gradle to complete its initial sync.
4. Connect a physical Android device (via USB or Wireless Debugging) or start an AVD Emulator.
5. Press `Shift + F10` or click the green 'Run' button in the toolbar to compile and deploy the APK.