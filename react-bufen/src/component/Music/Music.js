import React, {useEffect, useRef, useState} from "react";
import {Button} from 'antd';
import {CaretRightOutlined, PauseOutlined} from '@ant-design/icons';
import "./Music.css"

const formatDuration = (duration) => {
    const minutes = Math.floor(duration / 60);
    const seconds = Math.floor(duration % 60);
    return `${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;
};

const Music = (props) => {

    const [showBut, setShowBut] = useState(false);
    const [barwidth, setBarWidth] = useState(0);
    const [play, setPlay] = useState(false);
    const [time, setTime] = useState("");
    const canChangeRef = useRef(false);
    const music = useRef(new Audio());

    useEffect(() => {

        music.current.src = props.url;

        const onLoadedMetadata = () => {
            const duration = music.current.duration;
            setTime(formatDuration(duration))

            const moveChangeWidth = (e) => {
                if (canChangeRef.current) { // 使用 ref 的值
                    let rect = document.querySelector(".progress-back").getBoundingClientRect();
                    let x = (e.clientX - rect.left) / rect.width;
                    setBarWidth(x);
                    if (x >= 0 || x <= 1) {
                        console.log(duration);
                        music.current.currentTime = x * duration;
                        console.log(music.current.currentTime);
                    }
                }
            };

            const mouseUpHandler = () => {
                canChangeRef.current = false; // 更新 ref 的值
            };

            const updateProgress = () => {
                const currentTime = music.current.currentTime;
                setTime(formatDuration(currentTime));
                setBarWidth((currentTime / duration));
            };

            music.current.addEventListener('timeupdate', updateProgress);
            window.addEventListener("mousemove", moveChangeWidth);
            window.addEventListener("mouseup", mouseUpHandler);

            return () => {
                // 在组件卸载或者重新渲染时移除监听器
                window.removeEventListener("mousemove", moveChangeWidth);
                window.removeEventListener("mouseup", mouseUpHandler);
                music.current.removeEventListener('timeupdate', updateProgress);
            };
        }

        music.current.addEventListener('loadedmetadata', onLoadedMetadata);

        return () => {
            music.current.removeEventListener('loadedmetadata', onLoadedMetadata);
        };

    }, []); // 空依赖列表，确保只在组件挂载和卸载时运行

    const downChangeWidth = (e) => {
        let rect = document.querySelector(".progress-back").getBoundingClientRect();
        let x = (e.clientX - rect.left) / rect.width;
        setBarWidth(x);
        if (x >= 0 || x <= 1) {
            const duration = music.current.duration;
            music.current.currentTime = x * duration;
        }
        canChangeRef.current = true; // 更新 ref 的值
    };

    const showProBut = () => {
        setShowBut(true);
    }

    const delProBut = () => {
        setShowBut(false);
    }

    const playMusic = () => {
        music.current.play();
        setPlay(true);
    }

    const stopMusic = () => {
        music.current.pause();
        setPlay(false);
    }

    return (
        <div id="music-player"
             style={{display: (typeof props.style.display === "undefined" ? "inline-block" : props.style.display)}}>
            <div id="progress" onMouseEnter={setShowBut} onMouseLeave={delProBut} onMouseDown={e => downChangeWidth(e)}>
                <div className="progress-bar" style={{width: (barwidth * 400)}}></div>
                <div className="progress-back"></div>
                <div className="progress-but"
                     style={{display: (showBut ? "inline-block" : "none"), left: (barwidth * 400)}}></div>
            </div>
            <p className="time">{time}</p>
            <Button className="player-but" icon={<CaretRightOutlined/>} height={10} width={10} type="text"
                    style={{display: (play ? "none" : "inline-block")}} onClick={playMusic}/>
            <Button className="player-but" icon={<PauseOutlined/>} height={10} width={10} type="text"
                    style={{display: (play ? "inline-block" : "none")}} onClick={stopMusic}/>
        </div>
    )
}

export default Music;