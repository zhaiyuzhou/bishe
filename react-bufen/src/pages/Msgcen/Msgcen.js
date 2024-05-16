import React, {useEffect, useState} from 'react';
import {
    MenuFoldOutlined,
    MenuUnfoldOutlined,
} from '@ant-design/icons';
import {Avatar, Button, Layout, Menu, theme} from 'antd';
import Msgcon from './Msgcon';
import axios from 'axios';
import {Route, Routes} from 'react-router-dom';
import {useNavigate} from 'react-router';

const {Header, Sider, Content} = Layout;

const Msgcen = (props) => {

    const [collapsed, setCollapsed] = useState(false);
    const [friendList, setFriendList] = useState([]);
    const navigate = useNavigate();
    const [item, setItem] = useState([]);
    const {
        token: {colorBgContainer, borderRadiusLG},
    } = theme.useToken();

    useEffect(() => {

        axios.get("/api/getFriend")
            .then((res) => {
                setFriendList(res.data.data);
                setItem(res.data.data.map((friend, index) => {
                    return {
                        key: '/消息中心/' + friend.id,
                        icon: <Avatar src={friend.avatar}/>,
                        label: friend.nickName,
                    }
                }))
            });
    }, [])

    return (
        <div>
            <Layout>
                <Sider collapsed={collapsed}
                       style={{
                           background: colorBgContainer,
                       }}>
                    <Menu
                        mode="inline"
                        items={item}
                        onClick={(e) => {
                            navigate(e.key, {replace: true})
                        }}
                    />
                </Sider>
                <Layout>
                    <Header
                        style={{
                            padding: 0,
                            background: colorBgContainer,
                        }}
                    >
                        <Button
                            type="text"
                            icon={collapsed ? <MenuUnfoldOutlined/> : <MenuFoldOutlined/>}
                            onClick={() => setCollapsed(!collapsed)}
                            style={{
                                fontSize: '16px',
                                width: 64,
                                height: 64,
                            }}
                        />
                    </Header>
                    <Content
                        style={{
                            margin: '24px 16px',
                            padding: 24,
                            minHeight: 600,
                            background: colorBgContainer,
                            borderRadius: borderRadiusLG,
                        }}
                    >
                        <Routes>
                            {
                                friendList.filter(friend => friend !== null).map((friend, index) => {
                                    return (
                                        <Route path={'/' + friend.id}
                                               element={<Msgcon friend={friend} user={props.user}/>}/>
                                    )
                                })
                            }
                        </Routes>
                    </Content>
                </Layout>
            </Layout>
        </div>
    )
}

export default Msgcen;