import React, {useState} from "react";
import {Avatar, Button} from 'antd';
import {LikeFilled, LikeOutlined, UserOutlined} from '@ant-design/icons';
import "./Comment.css";
import axios from "axios";

const Comment = (props) => {

    const [like, setLike] = useState({num: 0, icon: <LikeOutlined/>});

    const addLikeNum = () => {
        setLike({num: 1, icon: <LikeFilled/>});
        axios.post("/addLikeNum", {
            comment: props,
        });
    }

    const delLikeNum = () => {
        setLike({num: 0, icon: <LikeOutlined/>});
        axios.post("/delLikeNum", {
            comment: props,
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
                <Avatar className="comment-avatar" icon={<UserOutlined/>} srcSet={props.avatar}/>
                <p className="comment-nickname">{props.nickName}nic</p>
                <p className="coment-huifu">{props.father}fat</p>
                <p className="comment-describe">{props.postedDate}des</p>
            </div>
            <p className="comment-content">{props.content}con</p>
            <div className="comment-bottom">
                <Button className="comment-button" type="link" shape="circle" icon={like.icon} onClick={LikeNum}/>
                <Button className="comment-button" type="link" shape="circle">回复</Button>
            </div>
        </div>
    )

}

export default Comment;