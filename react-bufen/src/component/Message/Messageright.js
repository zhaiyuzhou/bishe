import Avatar from "antd/es/avatar/avatar";
import React from "react";
import "./Message.css";
import {theme} from "antd";

const Messageright = (props) => {

    const {
        token: {colorBgContainer, borderRadiusLG},
    } = theme.useToken();

    return (
        <div className="message-div">
            <Avatar size="large" className="message-ava-right" src={props.user.avatar}/>
            <p className="message-nickname-right">{props.user.nickName}</p>
            <div className="message-content-right"
                 style={{backgroundColor: colorBgContainer, borderRadius: borderRadiusLG}}>
                <div className="message-flex">
                    <p style={{color: "white"}}>{props.message}</p>
                </div>
            </div>
        </div>
    )

}

export default Messageright;