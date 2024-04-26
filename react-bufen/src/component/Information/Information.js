import React from "react";
import {Descriptions} from 'antd';

const Information = (props) => {
    const items = [
        {
            key: '1',
            label: '用户名',
            children: props.userName,
        },
        {
            key: '2',
            label: '昵称',
            children: props.nickName,
        },
        {
            key: '3',
            label: '电话',
            children: props.phone,
        },
        {
            key: '4',
            label: '性别',
            children: props.gender,
        },
        {
            key: '5',
            label: '电子邮件',
            children: props.email,
            span: 2,
        },
        {
            key: '6',
            label: '生日',
            children: props.gmtCreated,
            span: 2,
        },
    ];


    return (
        <Descriptions title="个人信息" bordered items={items}/>
    )
}

export default Information;