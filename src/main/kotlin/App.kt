import kotlinx.coroutines.MainScope
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import react.RProps
import react.dom.div
import react.dom.h1
import react.dom.h3
import react.functionalComponent
import react.useEffect
import react.useReducer
import kotlin.browser.window

data class State(val unwatchedVideos: List<Video>, val watchedVideos: List<Video>, val currentVideo: Video?)

sealed class Action {
    data class SelectVideo(val video: Video) : Action()
    data class ToggleWatched(val video: Video) : Action()
    data class ResetVideos(val videos: List<Video>) : Action()
}

val initialState = State(unwatchedVideos = emptyList(), watchedVideos = emptyList(), currentVideo = null)

val reducer = { state: State, action: Action ->
    when (action) {
        is Action.SelectVideo -> state.copy(currentVideo = action.video)
        is Action.ToggleWatched -> {
            val watched = action.video in state.watchedVideos
            if (watched) {
                state.copy(
                    unwatchedVideos = state.unwatchedVideos + listOf(action.video),
                    watchedVideos = state.watchedVideos.filter { it !== action.video }) // If I use != then it complains that .equals does not exist
            } else {
                state.copy(
                    unwatchedVideos = state.unwatchedVideos.filter { it !== action.video },
                    watchedVideos = state.watchedVideos + listOf(action.video)
                )
            }
        }

        is Action.ResetVideos -> state.copy(unwatchedVideos = action.videos, watchedVideos = emptyList())
    }
}

val app = functionalComponent<RProps> {
    val (state, dispatch) = useReducer(reducer, initialState)

    useEffect(emptyList()) {
        MainScope().launch {
            val fetchedVideos = fetchVideos()
            dispatch(Action.ResetVideos(fetchedVideos))
        }
    }

    h1 {
        +"KotlinConf Explorer"
    }

    div {
        h3 {
            +"Videos to watch"
        }

        videoList {
            videos = state.unwatchedVideos
            selectedVideo = state.currentVideo
            onSelectVideo = { video -> dispatch(Action.SelectVideo(video)) }
        }

        h3 {
            +"Videos watched"
        }

        videoList {
            videos = state.watchedVideos
            selectedVideo = state.currentVideo
            onSelectVideo = { video -> dispatch(Action.SelectVideo(video)) }
        }
    }

    state.currentVideo?.let {
        videoPlayer {
            video = it
            unwatchedVideo = it in state.unwatchedVideos
            onWatchedButtonPressed = {
                dispatch(Action.ToggleWatched(it))
            }
        }
    }
}

suspend fun fetchVideos(): List<Video> =
    window.fetch("https://my-json-server.typicode.com/kotlin-hands-on/kotlinconf-json/videos").await()
        .json().await()
        .unsafeCast<Array<Video>>() // Cannot cast directly to list, because JS cannot find .iterator function
        .asList()