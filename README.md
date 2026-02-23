# Hello Google TV Web Wrapper 📺✨

A lightweight, high-performance Android TV application designed to seamlessly wrap and display Web UI technologies on the big screen. This templates empowers web developers to build premium Google TV experiences using standard HTML, CSS, and Javascript.

## 🚀 Overview

This project bypasses traditional native Android TV UI components (like Leanback fragments) and instead provides a fullscreen, borderless `WebView` container. It actively bridges native TV hardware hardware events (like the D-Pad and physical Back button) directly into the JavaScript DOM space.

## 🏗️ Architecture Matrix

- **App Container:** A standard `FragmentActivity` utilizing the `android.software.leanback` manifest flag to ensure the app appears correctly on TV Home Launchers.
- **WebView Core:** The UI is served entirely via an embedded Android `WebView`. It's configured to ignore user swipe gestures (`mediaPlaybackRequiresUserGesture = false`) and forcefully scale content to the wide viewport.
- **Remote Bridge:** 
  - `requestFocus()` guarantees the WebView is the primary receiver of all D-Pad hardware signals.
  - A custom `evaluateJavascript` bridge intercepts the Android OS `onBackPressed` trigger. This allows Single Page Applications (SPAs) to handle "Back" logic internally (e.g. closing modals or sidebars) *before* the Android OS terminates the process.

## 🎮 TV Remote Walkthrough

The project ships with a demonstration UI (`assets/index.html`) implementing custom "Spatial Navigation". 

### 1. Spatial Navigation (D-Pad)
Because TV users don't have a mouse, they navigate using the directional arrows on their remote. 
- The WebView captures `ArrowUp`, `ArrowDown`, `ArrowLeft`, and `ArrowRight` Keyboard Events.
- Our JavaScript dynamically calculates which element is spatially "next" and triggers `.focus()`.
- **CSS Magic:** Elements utilize `:focus` pseudo-classes to display premium, glowing scaling animations to tell the user what they are currently highlighting.

### 2. Selection (OK / Center Button)
When the user highlights a "TV Card" and presses the center OK button on their remote:
- An `Enter` Keyboard Event is fired.
- The web app traps the event (preventing double clicks) and briefly adds an `.active` CSS class to mimic the tactile "push" of a physical button.
- It then opens a fullscreen dark-glass Modal overlay.

### 3. Native Back Button
When the Modal is open, pressing the physical **Back** button on the Google TV remote triggers our native-to-web bridge:
1. Android Kotlin says: *"Hey WebView, I'm about to close the app unless your Javascript stops me."* (Via `handleAppBack()`)
2. Javascript responds: *"Wait! The user has a Modal open. I'll close the Modal, but don't close the app."* (`return true`)
3. If the user presses Back again from the main screen, JS says *"I have nothing open. Go ahead and close the app natively."* (`return false`)

## 🛠️ Getting Started

1. Clone this repository directly into **Android Studio**.
2. Android Studio will automatically sync the Gradle configuration.
3. Once indexed, hit the **Run (Play)** button at the top toolbar.
4. If you do not have a physical TV connected via ADB, use the integrated **Android TV Emulator**.
5. Once launched, use your arrow keys and the Enter key to experience the D-Pad Web Navigation!
