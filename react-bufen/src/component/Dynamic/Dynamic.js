import React from "react";
import { Avatar, Button } from 'antd';
import { UserOutlined, ExportOutlined, MessageOutlined, LikeOutlined } from '@ant-design/icons';
import { Flex, Tag } from 'antd';
import './Dynamic.css'


const Dynamic = () => {

    return (
        <div className="dynamic-div">
            <div className="dynamic-head">
                <Avatar className="dynamic-avatar" icon={<UserOutlined />} />
                <p className="dynamic-nickname">nickname</p>
                <p className="dynamic-describe">发布于******* ********</p>
            </div>
            <div className="dynamic-body">
                <Flex className="dynamic-tag" gap="4px 0" wrap="wrap">
                    <Tag color="#f50">#f50</Tag>
                    <Tag color="#2db7f5">#2db7f5</Tag>
                    <Tag color="#87d068">#87d068</Tag>
                    <Tag color="#108ee9">#108ee9</Tag>
                </Flex>
                <p className="dynamic-content">*********************************************************************************************************************************</p>
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