import React, {useState} from "react";
import {Avatar, Button} from 'antd';
import {LikeFilled, LikeOutlined, UserOutlined} from '@ant-design/icons';
import "./Comment.css";
import axios from "axios";

const Comment = (props) => {

    const [like, setLike] = useState({num: 0, icon: <LikeOutlined/>});
    const isLogin = props.isLogin;

    const addLikeNum = () => {
        setLike({num: 1, icon: <LikeFilled/>});
        axios.post("/addLikeNum", {
            commentId: props.id,
        });
    }

    const delLikeNum = () => {
        setLike({num: 0, icon: <LikeOutlined/>});
        axios.post("/delLikeNum", {
            commentId: props.id,
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
        <div className="comment-div">
            <div className="comment-head">
                <Avatar className="comment-avatar" icon={<UserOutlined/>} srcSet={props.author.avatar}/>
                <p className="comment-nickname">{props.author.nickName}</p>
                <p className="coment-huifu">{typeof props.father === "undefined" ? "" : "回复自" + props.father}</p>
                <p className="comment-describe">{props.postedDate}</p>
            </div>
            <p className="comment-content">{props.content}</p>
            <div className="comment-bottom">
                <Button className="comment-button" type="link" shape="circle" icon={like.icon} onClick={LikeNum}
                        disabled={!isLogin}/>
                <Button className="comment-button" type="link" shape="circle" disabled={!isLogin} onClick={() => {
                    props.getCommentAuthor(props.author.nickName);
                    setTimeout(() => {
                        props.getCommentAuthor("");
                    }, 50);
                }}>回复</Button>
            </div>
        </div>
    )

}

export default Comment;