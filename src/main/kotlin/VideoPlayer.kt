import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.RProps
import react.child
import react.dom.h3
import react.functionalComponent
import styled.css
import styled.styledButton
import styled.styledDiv

external interface VideoPlayerProps : RProps {
    var video: Video
    var onWatchedButtonPressed: (Video) -> Unit
    var unwatchedVideo: Boolean
}

val videoPlayer = functionalComponent<VideoPlayerProps> { props ->
    styledDiv {
        css {
            position = Position.absolute
            top = 10.px
            right = 10.px
            fontFamily = "sans-serif"
        }

        h3 {
            +"${props.video.speaker}: ${props.video.title}"
        }

        styledButton {
            css {
                display = Display.block
                backgroundColor = if (props.unwatchedVideo) Color.lightGreen else Color.red
            }

            attrs.onClickFunction = {
                props.onWatchedButtonPressed(props.video)
            }

            if (props.unwatchedVideo) {
                +"Mark as watched"
            } else {
                +"Mark as unwatched"
            }
        }

        styledDiv {
            css {
                display = Display.flex
                marginBottom = 10.px
            }

            TelegramShareButton {
                attrs.url = props.video.videoUrl
                TelegramIcon {
                    attrs.size = 32
                    attrs.round = true
                }
            }

            RedditShareButton {
                attrs.url = props.video.videoUrl
                RedditIcon {
                    attrs.size = 40
                    attrs.round = false
                }
            }
        }

        ReactPlayer {
            attrs.url = props.video.videoUrl
        }
    }
}

fun RBuilder.videoPlayer(handler: VideoPlayerProps.() -> Unit) =
    child(videoPlayer) {
        this.attrs(handler)
    }
