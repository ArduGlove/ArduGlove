# Mouse and keyboard interface for ArduGlove

## Building and running

```
// Build and run app
gradle installApp
build/install/MKInterface/bin/MKInterface

// Quickly test (can not take arguments)
gradle run

// Put a distributable zip in build/distributions
gradle distZip
```

If you don't have `gradle` installed replace it with `./gradlew` (Unix) or `gradlew` (Windows) in the above commands to use the included gradle wrapper