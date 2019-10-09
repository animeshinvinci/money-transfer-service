# Money transfer service

Service is built using Spark micro framework.

__Note__: IDE can show compilation errors if Lombok Plugin is not installed, 
but you can still build and run application with Gradle. For more details please
visit https://projectlombok.org/setup/overview

## Required tools

 * JDK 1.8 
 
 No need to install Gradle, as wrapper is already included in the project.
 
## How to

* Build project (unit + it + jar creation):

``` ./gradlew :build ```

* Run application:

```java -jar build/libs/money-transfer-1.0-SNAPSHOT.jar```

and app will be accessible on http://localhost:4567/

* Run unit tests:

``` ./gradlew :test ```

* Run integration tests:

``` ./gradlew :it ```

* Run unit + it:

``` ./gradlew :check ```

* Run e2e tests:

Build and run application first, then execute:

``` ./gradlew :e2e ```

## For Windows users

Replace __./gradlew__ with __gradlew.bat__ in all the commands.