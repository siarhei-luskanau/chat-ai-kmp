# Compose Multiplatform Application

### Android
To run the application on android device/emulator:
- open project in Android Studio and run imported android run configuration

To build the application bundle:
- run `./gradlew :app:androidApp:assembleDebug`
- find `.apk` file in `app/androidApp/build/outputs/apk/debug/androidApp-debug.apk`
  Run android UI tests on the connected device: `./gradlew connectedDebugAndroidTest`

### Desktop
Run the desktop application: `./gradlew :app:desktopApp:run`
Run the desktop **hot reload** application: `./gradlew :app:desktopApp:hotRun --auto`
Run desktop UI tests: `./gradlew jvmTest`

### iOS
To run the application on iPhone device/simulator:
- Open `iosApp/iosApp.xcproject` in Xcode and run standard configuration
- Or use [Kotlin Multiplatform Mobile plugin](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform-mobile) for Android Studio
  Run iOS simulator UI tests: `./gradlew iosSimulatorArm64Test`

### Web Distribution
Build web distribution: `./gradlew :app:webApp:composeCompatibilityBrowserDistribution`  
Deploy a dir `app/webApp/build/dist/composeWebCompatibility/productionExecutable` to a web server

### JS Browser
Run the browser application: `./gradlew :app:webApp:jsBrowserDevelopmentRun`

### Wasm Browser
Run the browser application: `./gradlew :app:webApp:wasmJsBrowserDevelopmentRun`  
