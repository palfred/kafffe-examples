# Kafffe Examples
This project holds programming examples for the [Kafffe Kotlin Frontend Library](https://github.com/cs-aware-next/data-visualisation.git).

# Getting started
Kafffe is included as a library loaded from a GitMub repository and build using Gradle. his cause a need to define a user and token for GitHub as the Maven repo dows not allow anonymous access AFAIK. This could be set up in USER_HOME/.gradle/gradle.properties as 

```properties
githubUser=palfred@rheasoft.dk
githubToken=ghp_XXXXXXXXXXXXXXXXXXXXXXXXXXXX
```

# Build

`./gradlew build` or `./gradlew browserDevelopemnetWebpack`

# Run
Open the desired examples HTML file in a browser from the build output in folder 'build/xxxxExecutable' or 'distributions'

Or:
`./gradlew browserDevelopmentRun` or `./gradlew browserProductionRun`  

# Import in IDE
IDEs that support Kotlin and Gradle, should be able to import this project.