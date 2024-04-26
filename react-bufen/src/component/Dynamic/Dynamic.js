import React from "react";
import {Avatar, Button, Flex, Image, Tag} from 'antd';
import {ExportOutlined, LikeOutlined, MessageOutlined, UserOutlined} from '@ant-design/icons';
import './Dynamic.css'


const Dynamic = (props) => {

    return (
        <div className="dynamic-div">
            <div className="dynamic-head">
                <Avatar className="dynamic-avatar" icon={<UserOutlined/>} srcSet={props.avatar}/>
                <p className="dynamic-nickname">{props.nickName}</p>
                <p className="dynamic-describe">发布于{props.postedDate}</p>
            </div>
            <div className="dynamic-body">
                <Flex className="dynamic-tag" gap="4px 0" wrap="wrap">
                    <Tag color="#f50">{props.tag}</Tag>
                </Flex>
                <p className="dynamic-content">{props.content}</p>
                <Image src={props.imgs}/>
                
            </div>
            <div className="dynamic-bottom">
                <Button className="dynamic-button" type="link" shape="circle" icon={<ExportOutlined />} />
                <Button className="dynamic-button" type="link" shape="circle" icon={<MessageOutlined />} />
                <Button className="dynamic-button" type="link" shape="circle" icon={<LikeOutlined />} />
            </div>
        </div>
    )
}

export default Dynamic;