import React, {useEffect} from "react";
import DPlayer from 'dplayer';

const Video = (props) => {

    useEffect(() => {
        const dp = new DPlayer({
            container: document.getElementById('video-player'),
            video: {
                url: props.url,
            },
        });
    });
    return (
        <div id="video-player" style={{
            maxWidth: "600px",
            display: (typeof (props.style.display) === "undefined" ? "inline-block" : props.style.display)
        }}></div>
    )
}

export default Video;