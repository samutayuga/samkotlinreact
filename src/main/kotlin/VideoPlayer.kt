import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.h3
import react.dom.img
import styled.css
import styled.styledButton
import styled.styledDiv

external interface VideoPlayerProps: RProps{
    var video: Video
    var onWatchedButtonPressed: (Video) -> Unit
    var unWatchedVideo: Boolean
}
@JsExport
class VideoPlayer: RComponent<VideoPlayerProps,RState>(){
    override fun RBuilder.render(){
        styledDiv {
            css {
                display = Display.flex
                marginBottom = 10.px
            }
            emailShareButton {
                attrs.url = props.video.videoUrl
                emailIcon {
                    attrs.size = 32
                    attrs.round = true
                }
            }
            telegramShareButton {
                attrs.url = props.video.videoUrl
                telegramIcon {
                    attrs.size = 32
                    attrs.round = true
                }
            }
        }
        styledDiv {
            css {
                position=Position.absolute
                top=10.px
                right=10.px

            }
            h3 {
                +"${props.video.speaker}: ${props.video.title}"
            }

            //img {
           //     attrs {
           //         src = "https://via.placeholder.com/640x360.png?text=Video+Player+Placeholder"
           //     }
            //}
            reactPlayer{
                attrs.url=props.video.videoUrl
            }
        }
        styledButton {
            css {
                display=Display.block
                backgroundColor=if(props.unWatchedVideo) Color.lightGreen else Color.red
            }
            attrs {
                onClickFunction={
                    props.onWatchedButtonPressed(props.video)
                }
                if(props.unWatchedVideo){
                    +"Mark as watched"
                }
                else{
                    +"Mark as unwatched"
                }
            }
        }
    }
}
fun RBuilder.videoPlayer(handler: VideoPlayerProps.() -> Unit): ReactElement{
    return child(VideoPlayer::class){
        this.attrs(handler)
    }
}