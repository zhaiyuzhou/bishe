import React, {useState} from 'react';
import {Button, Input, notification, Select, Upload} from 'antd';
import './Pubdyn.css';
import {CustomerServiceTwoTone, PictureOutlined, PlaySquareOutlined} from '@ant-design/icons';
import cookie from 'react-cookies'
import axios from 'axios';

const { TextArea } = Input;

const Pubdyn = (props) => {

    // 上传文字
    const [value, setValue] = useState('');
    const [imgName, setImgName] = useState([]);
    const [videoName, setVideoName] = useState([]);
    const [musicName, setMusicName] = useState([]);
    const [fileList, setFileList] = useState([]);
    const [tag, setTag] = useState('news');
    const [api, contextHolder] = notification.useNotification();

    const propsContent = () => {
        axios.post('/api/dynamicText', {
            content: value,
            tag: tag,
            imgName: imgName,
            videoName: videoName,
            musicName: musicName,
        })
            .then(function (response) {
                console.log(response);
                props.pubDynamic(response.data.data);
                setValue('');
                setFileList([]);
                api.open({
                    message: response.data.message,
                })
            })
            .catch(function (error) {
                console.log(error);
            });
    }
    // 选项
    const tagItem = [
        {
            value: 'news',
            label: '新闻',
        },
        {
            label: '电影',
            value: 'movie',
        },
        {
            label: '电视剧',
            value: 'show',
        },
        {
            label: '动画',
            value: 'animated',
        },
        {
            label: '番剧',
            value: 'bangumi',
        },
        {
            label: '游戏',
            value: 'game',
        },
        {
            label: '音乐',
            value: 'music',
        },
        {
            label: '美术',
            value: 'art',
        },
        {
            label: '动物',
            value: 'animal',
        },
        {
            label: '知识',
            value: 'knowledge',
        },
        {
            label: '科技',
            value: 'technology',
        },
        {
            label: '美食',
            value: 'food',
        },
        {
            label: '汽车',
            value: 'car',
        },
        {
            label: '运动',
            value: 'movement',
        },
        {
            label: '生活',
            value: 'live',
        },
        {
            label: '其他',
            value: 'other',
        },
    ]

    const chageTag = (value) => {
        setTag(value);
    }

    // 改变视图
    const [isimgload, setIsimgload] = useState(false);
    const [isvideoload, setIsvideoload] = useState(false);
    const [ismusicload, setIsmusicload] = useState(false);

    const showImgload = () => {
        const imgButton = document.getElementById("Pubdyn-upload-img-button");
        imgButton.click();
        setIsimgload(!isimgload);
    }

    const showvideoload = () => {
        const videoButton = document.getElementById("Pubdyn-upload-video-button");
        videoButton.click();
        setIsvideoload(!isvideoload);
    }

    const showmusicload = () => {
        const musicButton = document.getElementById("Pubdyn-upload-music-button");
        musicButton.click();
        setIsmusicload(!ismusicload);
    }

    const [uploadProps, setUploadProps] = useState();

    //上传照片
    const propsImg = {
        action: '/api/updateImg',
        listType: 'picture',
        onChange({file, fileList: newFileList}) {
            if (imgName.indexOf(file.name) === -1) {
                setImgName([...imgName, file.name]);
            } else {
                api.open({
                    message: "不要上传相同的文件",
                });
            }
            setFileList(newFileList);
            if (file.status === "done") {
                api.open({
                    message: file.response.message,
                });
            }
            if (file.status === "removed") {
                axios.post("/api/remove", {
                    fileName: file.name,
                })
            }
        },
    };

    //上传视频
    const propsVideo = {
        action: '/api/updateVideo',
        listType: 'picture',
        onChange({file, fileList: newFileList}) {
            console.log(file);
            if (videoName.indexOf(file.name) === -1) {
                setVideoName([...videoName, file.name]);
            }
            setFileList(newFileList);
            if (file.status === "done") {
                api.open({
                    message: file.response.message,
                });
            }
            if (file.status === "removed") {
                axios.post("/api/remove", {
                    fileName: file.name,
                })
            }
        },
    };

    // 上传音乐
    const propsMusic = {
        action: '/api/updateMusic',
        listType: 'picture',
        onChange({file, fileList: newFileList}) {
            if (musicName.indexOf(file.name) === -1) {
                setMusicName([...musicName, file.name]);
            }
            setFileList(newFileList);
            if (file.status === "done") {
                api.open({
                    message: file.response.message,
                });
            }
            if (file.status === "removed") {
                axios.post("/api/remove", {
                    fileName: file.name,
                })
            }
        },
    };

    const chageToImg = () => {
        setUploadProps(propsImg);
    }

    const chageToVideo = () => {
        setUploadProps(propsVideo);
    }

    const chageToMusic = () => {
        setUploadProps(propsMusic);
    }

    // 检查登录
    const isLogin = (cookie.load('isLogin') === 'true');

    return (
        <div className='Pubdyn-div'>
            {contextHolder}
            <TextArea
                value={value}
                onChange={(e) => setValue(e.target.value)}
                placeholder="想有什么于大家分享"
                autoSize={{
                    minRows: 4,
                    maxRows: 5,
                }}
            />
            <Select
                defaultValue="新闻"
                style={{
                    width: 120,
                }}
                onChange={chageTag}
                options={tagItem}
            />
            <Button type="text" icon={<PictureOutlined/>} onClick={showImgload} disabled={!isLogin}/>
            <Button type="text" icon={<PlaySquareOutlined/>} onClick={showvideoload} disabled={!isLogin}/>
            <Button type="text" icon={<CustomerServiceTwoTone twoToneColor="black"/>} onClick={showmusicload}
                    disabled={!isLogin}/>
            <Button className='Pubdyn-input' type="primary" onClick={propsContent} disabled={!isLogin}>发布</Button>
            <div className='Pubdyn-upload'>
                <Upload
                    fileList={fileList}
                    {...uploadProps}
                >
                    <button
                        className='Pubdyn-upload-img'
                        id='Pubdyn-upload-img-button'
                        type="button"
                        onClick={chageToImg}
                    ></button>
                    <button
                        className='Pubdyn-upload-video'
                        id='Pubdyn-upload-video-button'
                        onClick={chageToVideo}
                        type="button"
                    ></button>
                    <button
                        className='Pubdyn-upload-music'
                        id='Pubdyn-upload-music-button'
                        onClick={chageToMusic}
                        type="button"
                    ></button>

                </Upload>
            </div>
        </div>
    )
};

export default Pubdyn;