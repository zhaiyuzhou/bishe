import React, {useEffect, useState} from "react";
import {Avatar, Button, Flex, Image, Pagination, Tag} from 'antd';
import {
    CheckOutlined,
    ExportOutlined,
    LikeFilled,
    LikeOutlined,
    MessageOutlined,
    PlusOutlined,
    UserOutlined
} from '@ant-design/icons';
import Video from "../Video/Video";
import Comment from "../Comment/Comment";
import Music from "../Music/Music";
import axios from "axios";
import Pubcom from "../Pubcom/Pubcom";
import './Dynamic.css'

const Dynamic = (props) => {
    console.log(props);
    const tags = ['新闻', '电影', '电视剧', '动画', '番剧', '游戏', '音乐', '美术', '动物', '知识', '科技', '美食', '汽车', '运动', '生活', '其他'];
    const tagsEn = ['news', 'movie', 'show', 'animated', 'bangumi', 'game', 'music', 'art', 'animal', 'knowledge', 'technology', 'food', 'car', 'movement', 'live', 'other'];

    const [like, setLike] = useState({num: 0});
    const [commentHeignt, setCommentHeight] = useState(false);
    const [commentList, setCommentList] = useState([]);
    const [commentPage, setCommentPage] = useState([]);

    useEffect(() => {
        setCommentList(props.comments);
        setCommentPage(commentList.slice(0, 10));
    }, [props.comments])

    const addLikeNum = () => {
        setLike({num: 1});
        axios.post("/api/addLikeNum", {
            dynamicId: props.id,
        });
    }

    const delLikeNum = () => {
        setLike({num: 0});
        axios.post("/api/delLikeNum", {
            dynamicId: props.id,
        });
    }

    const LikeNum = () => {
        if (like.num === 0) {
            addLikeNum();
        } else {
            delLikeNum();
        }
    }

    const [gz, setGz] = useState(false);
    const guanzhu = () => {
        setGz(true);
        axios.post("/api/addLikeNum", {
            userId: props.author.id,
        });
    }

    const quguan = () => {
        setGz(false);
        axios.post("/api/delLikeNum", {
            userId: props.author.id,
        });
    }

    const gzfun = () => {
        if (!gz) {
            guanzhu();
        } else {
            quguan();
        }
    }

    // 处理新发的评论
    const pubComment = (newComment) => {
        if (typeof newComment != "undefined")
            setCommentList([...commentList, newComment]);
    }

    const [commentAuthor, setCommentAuthor] = useState("");
    const getCommentAuthor = (commentAuthor) => {
        console.log(commentAuthor);
        setCommentAuthor(commentAuthor);
    }


    return (
        <div className="dynamic-div">
            <div className="dynamic-head">
                <Avatar className="dynamic-avatar" icon={<UserOutlined/>} srcSet={props.author.avatar}/>
                <p className="dynamic-nickname">{props.author.nickName}</p>
                <p className="dynamic-describe">发布于{props.postedDate}</p>
                <Button className="dynamic-guanzhu" type='primary' icon={gz ? <CheckOutlined/> : <PlusOutlined/>}
                        onClick={gzfun} disabled={!props.isLogin}>{gz ? "取关" : "关注"}</Button>
            </div>
            <div className="dynamic-body">
                <Flex className="dynamic-tag" gap="4px 0" wrap="wrap">
                    <Tag style={{color: "#52c41a", border: "0px"}}>#{tags[tagsEn.indexOf(props.tag)]}</Tag>
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
                <Button className="dynamic-button" type="link" shape="circle" icon={<ExportOutlined/>}
                        disabled={!props.isLogin}/>
                <Button className="dynamic-button" type="link" shape="circle" icon={<MessageOutlined/>} onClick={() => {
                    setCommentHeight(!commentHeignt)
                }}/>
                <Button className="dynamic-button" type="link" shape="circle"
                        icon={like.num === 0 ? <LikeOutlined/> : <LikeFilled/>} onClick={LikeNum}
                        disabled={!props.isLogin}/>
            </div>
            <div className="dynamic-comment" style={{height: (commentHeignt ? "auto" : "0px")}}>
                <Pubcom dynamicId={props.id} pubComment={pubComment} commentAuthor={commentAuthor}
                        isLogin={props.isLogin}/>
                {
                    commentPage.map((comment, index) => {
                        return (
                            <Comment key={"comment" + index} {...comment} getCommentAuthor={getCommentAuthor}
                                     isLogin={props.isLogin}/>
                        )
                    })
                }
                <Pagination pageSize={10} total={commentList.length} onChange={(page, pageSize) => {
                    setCommentPage(commentList.slice((page - 1) * pageSize, page * pageSize));
                }}/>
            </div>
        </div>
    )
}

export default Dynamic;