import React, {useState} from "react";
import {Avatar, Button, Flex, Image, Tag} from 'antd';
import Video from "../Video/Video";
import {ExportOutlined, LikeFilled, LikeOutlined, MessageOutlined, UserOutlined} from '@ant-design/icons';
import Comment from "../Comment/Comment";
import './Dynamic.css'
import Music from "../Music/Music";
import axios from "axios";
import Pubcom from "../Pubcom/Pubcom";

const Dynamic = (props) => {
    const tags = ['新闻', '电影', '电视剧', '动画', '番剧', '游戏', '音乐', '美术', '动物', '知识', '科技', '美食', '汽车', '运动', '生活', '其他'];
    const tagsEn = ['news', 'movie', 'show', 'animated', 'bangumi', 'game', 'music', 'art', 'animal', 'knowledge', 'technology', 'food', 'car', 'movement', 'live', 'other'];

    const [like, setLike] = useState({num: 0, icon: <LikeOutlined/>});

    const addLikeNum = () => {
        setLike({num: 1, icon: <LikeFilled/>});
        axios.post("/addLikeNum", {
            dynamic: props,
        });
    }

    const delLikeNum = () => {
        setLike({num: 0, icon: <LikeOutlined/>});
        axios.post("/delLikeNum", {
            dynamic: props,
        });
    }

    const LikeNum = () => {
        if (like.num === 0) {
            addLikeNum();
        } else {
            delLikeNum();
        }
    }

    return (
        <div className="dynamic-div">
            <div className="dynamic-head">
                <Avatar className="dynamic-avatar" icon={<UserOutlined/>} srcSet={props.avatar}/>
                <p className="dynamic-nickname">{props.nickName}</p>
                <p className="dynamic-describe">发布于{props.postedDate}</p>
            </div>
            <div className="dynamic-body">
                <Flex className="dynamic-tag" gap="4px 0" wrap="wrap">
                    <Tag color="#52c41a">{tags[tagsEn.indexOf(props.tag)]}</Tag>
                </Flex>
                <p className="dynamic-content">{props.content}</p>
                <Flex wrap="wrap" gap="small">
                    {
                        props.imgPaths.map((img, index) => {
                            return (
                                <Image key={"img" + index}
                                       style={{display: (typeof img == "undefined" ? 'none' : 'block')}} src={img}/>
                            )
                        })
                    }
                </Flex>
                <br/>
                <Flex wrap="wrap" gap="small">
                    {
                        props.videoPaths.map((video, index) => {
                            return (
                                <Video key={"video" + index}
                                       style={{display: (typeof video == "undefined" ? 'none' : 'block')}} url={video}/>
                            )
                        })
                    }
                </Flex>
                <br/>
                <Flex wrap="wrap" gap="small">
                    {
                        props.musicPaths.map((music, index) => {
                            return (
                                <Music key={"music" + index}
                                       style={{display: (typeof music == "undefined" ? 'none' : 'block')}} url={music}/>
                            )
                        })
                    }
                </Flex>
            </div>
            <div className="dynamic-bottom">
                <Button className="dynamic-button" type="link" shape="circle" icon={<ExportOutlined />} />
                <Button className="dynamic-button" type="link" shape="circle" icon={<MessageOutlined />} />
                <Button className="dynamic-button" type="link" shape="circle" icon={like.icon} onClick={LikeNum}/>
            </div>
            <div className="dynamic-comment">
                <Pubcom/>
                <Comment/>
            </div>
        </div>
    )
}

export default Dynamic;