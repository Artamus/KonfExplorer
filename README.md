# KonfExplorer
The example Kotlin/JS React project for exploring KotlinConf talks.

Follows the tutorial from the 
[Kotlin/JS react hands-on tutorial](https://kotlinlang.org/docs/tutorials/javascript/react-and-kotlin-js.html),
but all code/components have been converted to use functional style and hooks.

## Running the project

From the terminal: `./gradlew run`, to have hot reload: `./gradlew run --continuous`.

If using IDEA use either the `run` or the `browserDevelopmentRun` task from the explorer.

## Build for production

Run `./gradlew build` or the `build` task from IDEA.

The appropriate static files to be deployed will be created in `/build/distributions`.

## Encountered problems
1. In the reducer I had to use `!==` at one point
 because when using `!=` the operation failed with error 
 `element.equals is not a function`.
2. In the suspending function to fetch videos I had to use the type 
 `Array<Video>` because otherwise the operation failed with the error
 `props.videos.iterator is not a function`.
