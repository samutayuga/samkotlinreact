import kotlinx.css.*
import react.*
import react.dom.h1
import react.dom.h3
import react.dom.img
import react.dom.select
import styled.css
import styled.styledDiv

external interface AppState : RState {
    var currentVideo: Video?
}

@JsExport
class App : RComponent<RProps, AppState>() {
    override fun RBuilder.render() {
        h1 {
            +"KotlinConf Explorer"
        }
        h3 {
            +"videos to watch"
            //child(VideoList::class){
            //     attrs.videos=unwatchedVideos
            // }
            videoList {
                videos = unwatchedVideos
                selectedVideo = state.currentVideo
                onSelectVideo = { video ->
                    setState { currentVideo = video }
                }
            }
        }
        h3 {
            +"videos watched"
            //child(VideoList::class){
            //     attrs.videos=watchedVideos
            // }
            videoList {
                videos = watchedVideos
                selectedVideo = state.currentVideo
                onSelectVideo = { video -> setState { currentVideo = video } }

            }
        }

        styledDiv {
            css {
                position = Position.absolute
                top = 10.px
                right = 10.px

            }
            h3 {
                +"John Doe: Building and breaking things"
            }

            img {
                attrs {
                    src = "https://via.placeholder.com/640x360.png?text=Video+Player+Placeholder"
                }
            }
        }
    }
}