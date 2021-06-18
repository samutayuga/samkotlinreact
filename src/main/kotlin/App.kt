import kotlinx.browser.window
import kotlinx.coroutines.*
import react.*
import react.dom.h1
import react.dom.h3

external interface AppState : RState {
    var currentVideo: Video?
    var unwatchedVideos: List<Video>
    var watchedVideos: List<Video>
}

suspend fun fetchVideo(id: Int): Video {
    val response =
        window.fetch("https://my-json-server.typicode.com/kotlin-hands-on/kotlinconf-json/videos/$id")
            .await()
            .json()
            .await()
    return response as Video
}

suspend fun fectchVideos(): List<Video> = coroutineScope {
    (1..25).map { id ->
        async { fetchVideo(id) }
    }.awaitAll()
}

@JsExport
class App : RComponent<RProps, AppState>() {
    override fun AppState.init() {
//        unwatchedVideos = listOf(
//            KotlinVideo(
//                1,
//                "Building and breaking things",
//                "John Doe",
//                "https://youtu.be/PsaFVLr8t4E"
//            ),
//            KotlinVideo(2, "The development process", "Jane Smith", "https://youtu.be/PsaFVLr8t4E"),
//            KotlinVideo(3, "The Web 7.0", "Matt Miller", "https://youtu.be/PsaFVLr8t4E")
//        )
//        watchedVideos = listOf(
//            KotlinVideo(4, "Mouseless development", "Tom Jerry", "https://youtu.be/PsaFVLr8t4E")
//        )

        unwatchedVideos = listOf()
        watchedVideos = listOf()
        val mainScope = MainScope()
        mainScope.launch {
            val videos = fectchVideos()
            setState {
                unwatchedVideos = videos
            }
        }
    }

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
                videos = state.unwatchedVideos
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
                videos = state.watchedVideos
                selectedVideo = state.currentVideo
                onSelectVideo = { video -> setState { currentVideo = video } }

            }
        }

        // styledDiv {
        //    css {
        //         position = Position.absolute
        //          top = 10.px
        //          right = 10.px

        //      }
        //       h3 {
        //          +"John Doe: Building and breaking things"
        //      }

        //       img {
        //           attrs {
        //               src = "https://via.placeholder.com/640x360.png?text=Video+Player+Placeholder"
        //            }
        //        }
        //    }
        state.currentVideo?.let { currentVideo ->
            videoPlayer {
                video = currentVideo
                unWatchedVideo = currentVideo in state.unwatchedVideos
                onWatchedButtonPressed = {
                    if (video in state.unwatchedVideos) {
                        setState {
                            unwatchedVideos -= video
                            watchedVideos += video
                        }

                    } else {
                        setState {
                            watchedVideos -= video
                            unwatchedVideos += video
                        }
                    }
                }
            }
        }
    }
}