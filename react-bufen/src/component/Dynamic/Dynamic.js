import React, {useEffect, useState} from "react";
import {Avatar, Button, Flex, Image, Modal, Pagination, Tag} from 'antd';
import {
    CloseOutlined,
    DeleteOutlined,
    ExportOutlined,
    LikeFilled,
    LikeOutlined,
    MessageOutlined,
    PlusOutlined,
    UserOutlined
} from '@ant-design/icons';
import Video from "../Video/Video";
import {useNavigate} from 'react-router';
import Comment from "../Comment/Comment";
import Music from "../Music/Music";
import axios from "axios";
import Pubcom from "../Pubcom/Pubcom";
import './Dynamic.css'

const Dynamic = (props) => {

    const tags = ['新闻', '电影', '电视剧', '动画', '番剧', '游戏', '音乐', '美术', '动物', '知识', '科技', '美食', '汽车', '运动', '生活', '其他'];
    const tagsEn = ['news', 'movie', 'show', 'animated', 'bangumi', 'game', 'music', 'art', 'animal', 'knowledge', 'technology', 'food', 'car', 'movement', 'live', 'other'];

    const [like, setLike] = useState({num: 0});
    const [commentList, setCommentList] = useState([]);
    const [commentPage, setCommentPage] = useState([]);
    const navigate = useNavigate()

    const [gz, setGz] = useState(false);

    useEffect(() => {
        setCommentList(props.comments);
        setCommentPage(commentList.slice(0, 10));
        setGz(props.ifa);

        const avatar = document.querySelector(".dynamic-avatar");

        const toPerspac = () => {
            navigate("/个人动态/" + props.author.id, {replace: false});
        };
        avatar.addEventListener("dblclick", toPerspac);
        return () => {
            avatar.removeEventListener("dblclick", toPerspac);
        };
    }, [props, commentList, navigate])


    // 点赞
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

    // 关注
    const guanzhu = () => {
        setGz(true);
        axios.post("/api/attention", {
            userId: props.author.id,
        });
    }

    const quguan = () => {
        setGz(false);
        axios.post("/api/calAttention", {
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

    // 删除动态
    const delDynamic = () => {
        axios.post("/api/delDynamic", {
            dynamicId: props.id,
        })
        props.delDynamicforList(props);
    }

    // 转发动态
    const transmit = () => {
        props.setTransmit(props);
    }

    const [isModalOpen, setIsModalOpen] = useState(false);
    const showModal = () => {
        setIsModalOpen(true);
    };
    const handleOk = () => {
        setIsModalOpen(false);
    };
    const handleCancel = () => {
        setIsModalOpen(false);
    };

    return (
        <div className="dynamic-div">
            <div className="dynamic-head">
                <Avatar className="dynamic-avatar" icon={<UserOutlined/>} src={props.author.avatar} onClick={() => {
                }}/>
                <p className="dynamic-nickname">{props.author.nickName}</p>
                <p className="dynamic-describe">发布于{props.postedDate}</p>
                <Button className="dynamic-guanzhu" style={{display: (props.del ? "none" : "block")}} type='primary'
                        icon={gz ? <CloseOutlined/> : <PlusOutlined/>}
                        onClick={gzfun} disabled={!props.isLogin}>{gz ? "取关" : "关注"}</Button>
                <Button className="dynamic-guanzhu" style={{display: (props.del ? "block" : "none")}} type='primary'
                        danger icon={<DeleteOutlined/>}
                        onClick={delDynamic} disabled={!props.isLogin}>删除</Button>
            </div>
            <div className="dynamic-body">
                <Flex className="dynamic-tag" gap="4px 0" wrap="wrap">
                    <Tag style={{color: "#52c41a", border: "0px"}}>#{tags[tagsEn.indexOf(props.tag)]}</Tag>
                </Flex>
                <p className="dynamic-content">{props.content}</p>
                <div className="dynamic-transmit" style={{display: (props.transmit === null ? "none" : "block")}}>
                    <Button onClick={showModal}>
                        {props.transmit === null ? "" : (props.transmit.content.slice(0, 5)) + "..."}
                    </Button>
                    <Modal open={isModalOpen} onOk={handleOk} onCancel={handleCancel}
                           okText="确认" cancelText="关闭" width={800} mask={false} destroyOnClose={true}>
                        <Dynamic {...props.transmit} isLogin={props.isLogin} del={props.del} fatherId={props.id}/>
                    </Modal>
                </div>
                <Flex wrap="wrap" gap="small">
                    {
                        props.imgPaths.map((img, index) => {
                            return (
                                <Image key={"img" + index}
                                       style={{
                                           display: (typeof img == "undefined" ? 'none' : 'block'),
                                           maxWidth: 600,
                                           borderRadius: "6px",
                                       }} src={img}/>
                            )
                        })
                    }
                </Flex>
                <br/>
                <Flex wrap="wrap" gap="small">
                    {
                        props.videoPaths.map((video, index) => {
                            return (
                                <Video key={"video" + index} idx={index} videoId={props.id} transmitId={props.fatherId}
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
                        onClick={transmit}
                        disabled={!props.isLogin}/>
                <Button className="dynamic-button" type="link" shape="circle" icon={<MessageOutlined/>} onClick={() => {
                    const commentdiv = document.getElementById("comment" + props.id + props.fatherId);
                    if (commentdiv.style.height === "auto") {
                        commentdiv.style.height = "0px";
                    } else {
                        commentdiv.style.height = "auto";
                    }
                }}/>
                <Button className="dynamic-button" type="link" shape="circle"
                        icon={like.num === 0 ? <LikeOutlined/> : <LikeFilled/>} onClick={LikeNum}
                        disabled={!props.isLogin}/>
                <p className="dynamic-likeNum">{props.likeNum}</p>
            </div>
            <div className="dynamic-comment" id={"comment" + props.id + props.fatherId}>
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