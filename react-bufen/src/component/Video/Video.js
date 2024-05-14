import React, {useEffect, useState} from "react";
import DPlayer from 'dplayer';

const Video = (props) => {

    const [id, setId] = useState("video-player" + props.videoId + props.transmitId + props.idx)
    useEffect(() => {
        const dp = new DPlayer({
            container: document.getElementById(id),
            video: {
                url: props.url,
            },
        });
    });
    return (
        <div id={id} style={{
            maxWidth: "600px",
            display: (typeof (props.style.display) === "undefined" ? "inline-block" : props.style.display)
        }}></div>
    )
}

export default Video;