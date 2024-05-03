import React, {useEffect, useRef, useState} from 'react';
import {Button, Image, Input, Upload} from 'antd';
import {CloudUploadOutlined, CustomerServiceTwoTone, PictureOutlined, PlaySquareOutlined} from '@ant-design/icons';
import axios from 'axios';
import "./Pubcom.css"

const {TextArea} = Input;

const Pubcom = (props) => {

    // 上传文字
    const [value, setValue] = useState('');
    const [imgName, setImgName] = useState([]);
    const [videoName, setVideoName] = useState([]);
    const [musicName, setMusicName] = useState([]);

    const propsContent = () => {
        axios.post('/commentText', {
            content: value,
            dynamicId: props.dynamicId,
            imgName: imgName,
            videoName: videoName,
            musicName: musicName,
        })
            .then(function (response) {
                console.log(response);
            })
            .catch(function (error) {
                console.log(error);
            });
    }

    // 改变视图
    const [isimgload, setIsimgload] = useState(false);
    const [isvideoload, setIsvideoload] = useState(false);
    const [ismusicload, setIsmusicload] = useState(false);

    const showImgload = () => {
        setIsimgload(!isimgload);
    }

    const showvideoload = () => {
        setIsvideoload(!isvideoload);
    }

    const showmusicload = () => {
        setIsmusicload(!ismusicload);
    }

    // 上传的图片
    const [previewOpen, setPreviewOpen] = useState(false);
    const [previewImage, setPreviewImage] = useState('');

    const [props, setProps] = useState();

    //上传照片
    const propsImg = {
        action: '/updateImg',
        listType: 'picture',
        onChange({file}) {
            if (imgName.indexOf(file.name) === -1) {
                setImgName([...imgName, file.name]);
            }
        },
    };

    //上传视频
    const propsVideo = {
        action: '/updateVideo',
        listType: 'picture',
        onChange({file}) {
            console.log(file);
            if (videoName.indexOf(file.name) === -1) {
                setVideoName([...videoName, file.name]);
            }
        },
    };

    // 上传音乐
    const propsMusic = {
        action: '/updateMusic',
        listType: 'picture',
        onChange({file}) {
            if (musicName.indexOf(file.name) === -1) {
                setMusicName([...musicName, file.name]);
            }
        },
    };

    const chageToImg = () => {
        setProps(propsImg);
    }

    const chageToVideo = () => {
        setProps(propsVideo);
    }

    const chageToMusic = () => {
        setProps(propsMusic);
    }

    // 检查登录
    // const isLogin = (cookie.load('isLogin') === 'true');
    const isLogin = true;
    const pubcomBut = useRef(null);
    const pubcomText = useRef(null);

    useEffect(() => {

        const shouqi = (e) => {
            if (e.target !== pubcomText.current.resizableTextArea.textArea) {
                pubcomBut.current.style.transform = "translateY(-32px)";
            } else {
                pubcomBut.current.style.transform = "translateY(0px)";
            }
        }

        window.addEventListener("click", shouqi);

        return () => {
            window.removeEventListener("click", shouqi);
        }
    })

    return (
        <div className='Pubcom-div'>
            <TextArea
                ref={pubcomText}
                className='Pubcom-text'
                value={value}
                onChange={(e) => setValue(e.target.value)}
                placeholder="想要评价什么"
                autoSize={{
                    minRows: 2,
                    maxRows: 5,
                }}
                style={{
                    zIndex: "2",
                }}
            />
            <div ref={pubcomBut} className='Pubcom-but'>
                <Button type="text" icon={<PictureOutlined/>} onClick={showImgload} disabled={!isLogin}/>
                <Button type="text" icon={<PlaySquareOutlined/>} onClick={showvideoload} disabled={!isLogin}/>
                <Button type="text" icon={<CustomerServiceTwoTone twoToneColor="black"/>} onClick={showmusicload}
                        disabled={!isLogin}/>
                <Button className='Pubcom-input' type="primary" onClick={propsContent} disabled={!isLogin}>发布</Button>
            </div>
            <div className='Pubcom-upload'>
                <Upload
                    {...props}
                >
                    <div className='Pubcom-upload-img' style={{display: (isimgload ? 'inline-block' : 'none')}}>
                        <button
                            style={{
                                border: 0,
                                background: 'none',
                            }}
                            type="button"
                            onClick={chageToImg}
                        >
                            <CloudUploadOutlined/>
                            <div
                                style={{
                                    marginTop: 8,
                                }}
                            >
                                图片
                            </div>
                        </button>
                    </div>
                    <div className='Pubcom-upload-video' style={{display: (isvideoload ? 'inline-block' : 'none')}}>
                        <button
                            style={{
                                border: 0,
                                background: 'none',
                            }}
                            onClick={chageToVideo}
                            type="button"
                        >
                            <CloudUploadOutlined/>
                            <div
                                style={{
                                    marginTop: 8,
                                }}
                            >
                                视频
                            </div>
                        </button>
                    </div>
                    <div className='Pubcom-upload-music' style={{display: (ismusicload ? 'inline-block' : 'none')}}>
                        <button
                            style={{
                                border: 0,
                                background: 'none',
                            }}
                            onClick={chageToMusic}
                            type="button"
                        >
                            <CloudUploadOutlined/>
                            <div
                                style={{
                                    marginTop: 8,
                                }}
                            >
                                音乐
                            </div>
                        </button>
                    </div>
                </Upload>
                {previewImage && (
                    <Image
                        height={30}
                        width={30}
                        wrapperStyle={{
                            display: 'none',
                        }}
                        preview={{
                            visible: previewOpen,
                            onVisibleChange: (visible) => setPreviewOpen(visible),
                            afterOpenChange: (visible) => !visible && setPreviewImage(''),
                        }}
                        src={previewImage}
                    />
                )}
            </div>
        </div>
    )

}

export default Pubcom;