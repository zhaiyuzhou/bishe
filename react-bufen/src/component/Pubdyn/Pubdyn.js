import React, {useState} from 'react';
import {Button, Image, Input, Select, Upload} from 'antd';
import './Pubdyn.css';
import {CloudUploadOutlined, CustomerServiceTwoTone, PictureOutlined, PlaySquareOutlined} from '@ant-design/icons';
import cookie from 'react-cookies'
import axios from 'axios';

const { TextArea } = Input;

const Pubdyn = (props) => {

    // 上传文字
    const [value, setValue] = useState('');
    const [imgName, setImgName] = useState([]);
    const [videoName, setVideoName] = useState([]);
    const [musicName, setMusicName] = useState([]);
    const [tag, setTag] = useState('news');

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

    const [uploadProps, setUploadProps] = useState();

    //上传照片
    const propsImg = {
        action: '/api/updateImg',
        listType: 'picture',
        onChange({file}) {
            if (imgName.indexOf(file.name) === -1) {
                setImgName([...imgName, file.name]);
            }
        },
    };

    //上传视频
    const propsVideo = {
        action: '/api/updateVideo',
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
        action: '/api/updateMusic',
        listType: 'picture',
        onChange({file}) {
            if (musicName.indexOf(file.name) === -1) {
                setMusicName([...musicName, file.name]);
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
                    {...uploadProps}
                >
                    <div className='Pubdyn-upload-img' style={{display: (isimgload ? 'inline-block' : 'none')}}>
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
                    <div className='Pubdyn-upload-video' style={{display: (isvideoload ? 'inline-block' : 'none')}}>
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
                    <div className='Pubdyn-upload-music' style={{display: (ismusicload ? 'inline-block' : 'none')}}>
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
};

export default Pubdyn;