import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.RProps
import react.child
import react.dom.p
import react.functionalComponent

external interface VideoListProps : RProps {
    var videos: List<Video>
    var selectedVideo: Video?
    var onSelectVideo: (Video) -> Unit
}

val videoList = functionalComponent<VideoListProps> { props ->
    props.videos.forEach { video ->
        p {
            key = video.id.toString()
            attrs.onClickFunction = { props.onSelectVideo(video) }

            if (video == props.selectedVideo) {
                +"> "
            }
            +"${video.speaker}: ${video.title}"
        }
    }
}

fun RBuilder.videoList(handler: VideoListProps.() -> Unit) =
    child(videoList) {
        this.attrs(handler)
    }
