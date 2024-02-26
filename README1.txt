## Selenium java framework

### Installation

- install [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- install [Gradle 8.5](https://gradle.org/releases/)
- install Allure:
-
    * `brew install allure` for Mac [Allure](https://brew.sh/)
-
    * `scoop install allure` for Windows

* install dependencies:
* `./gradlew build`

### Run tests

### IMPORTAN ACTIONS

-for run test using testng need to do actions:

1. Open Settings -> Build, Execution, Deployment -> Build Tools -> Gradle
2. Change Build and run using to `IntelliJ IDEA`
3. Change Run tests using to `IntelliJ IDEA`
4. Apply and Ok


- Run all tests `./gradlew clean test`

- Run tests using task `./gradlew clean nameOfTask` (task name can be found in `gradlew build` file)

### Config file

- Config file is located in `src/main/resources/config.properties`
- Need to add project url
- selenoid.state - false = `local run`, `true = remote run`
- selenoid.url - `url to selenoid`

### Reports

- After running tests, you can find reports in `build/reports/allure-report` folder
- To open report , run `allure serve build/allure-results`

### Screenshots

- Screenshots are saved in `reference` folder
- Screenshots are taken when test fails
- To do screenshot is reference need to change screenshot name, remove `tmp_` from screenshot name
- Screenshots are attached to allure report 