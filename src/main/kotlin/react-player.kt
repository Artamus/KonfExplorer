@file:JsModule("react-player")
@file:JsNonModule

import react.RClass
import react.RProps

@JsName("default")
external val ReactPlayer: RClass<ReactPlayerProps>

external interface ReactPlayerProps : RProps {
    var url: String
}