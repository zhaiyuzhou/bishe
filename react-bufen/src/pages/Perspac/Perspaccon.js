import "./perspac.css";
import React, {useEffect, useState} from 'react';
import {CheckOutlined, HeartOutlined, PlusOutlined, SlackCircleFilled} from '@ant-design/icons';
import {Avatar, Button, ConfigProvider, Input, Menu, theme} from 'antd';
import {useNavigate} from 'react-router';
import {Route, Routes, useLocation} from 'react-router-dom';
import Dynamicbody from '../Dynamicbody/Dynamicbody';
import axios from 'axios';


const Perspaccon = (props) => {

    const {
        token: {borderRadiusLG},
    } = theme.useToken();

    const navigate = useNavigate();
    const location = useLocation();


    // 个性签名
    const onSignature = (e) => {
        navigate(e.key, {replace: true});
    };
    useEffect(() => {
        if (decodeURI(location.pathname) === '/个人动态/' + props.user.id) {
            navigate('/个人动态/' + props.user.id + '/dynamic', {replace: true});
        }

        const signature = document.querySelector(".Perspac-header-signature");
        window.addEventListener("click", (e) => {
            if (signature.contains(e.target)) {
                signature.style.background = "white";
            } else {
                signature.style.background = "transparent";
            }
        })

    }, [location, navigate, props.user])

    // 顶部导航
    const onMenu = (e) => {
        navigate(e.key, {replace: true});
    };


    const items = [
        {
            label: '个人动态',
            key: '/个人动态/' + props.user.id + '/dynamic',
            icon: <SlackCircleFilled/>,
        },
        {
            label: '收藏的动态',
            key: '/个人动态/' + props.user.id + '/likeDynamic',
            icon: <HeartOutlined/>,
        },
    ];


    // 关注
    const [gz, setGz] = useState(false);
    const guanzhu = () => {
        setGz(true);
        axios.post("/api/attention", {
            userId: props.user.id,
        });
    }

    const quguan = () => {
        setGz(false);
        axios.post("/api/calAttention", {
            userId: props.user.id,
        });
    }

    const gzfun = () => {
        if (!gz) {
            guanzhu();
        } else {
            quguan();
        }
    }

    return (
        <>
            <div className='Perspac-header'>
                <Avatar className='Perspac-header-avatar' src={props.user.avatar}/>
                <p className='Perspac-header-nickName'>{props.user.nickName}</p>
                <ConfigProvider
                    theme={{
                        token: {
                            colorPrimary: "#ffffff",
                        },
                        components: {
                            Input: {
                                colorPrimary: '#8c8c8c',
                                activeBorderColor: '#f0f0f0',
                            }
                        }
                    }}
                >
                    <Input className='Perspac-header-signature' placeholder={props.user.signature} allowClear
                           onChange={onSignature} style={{display: (props.disabled ? "none" : "block")}}/>
                    <p className='Perspac-header-signature-txt'
                       style={{display: (props.disabled ? "block" : "none")}}>{props.user.signature === "请编辑个人签名" ? "这个人没有个人签名" : props.user.signature}</p>
                </ConfigProvider>
                <Button className="Perspac-header-guanzhu" style={{display: (props.self ? "none" : "block")}}
                        type='primary'
                        icon={gz ? <CheckOutlined/> : <PlusOutlined/>}
                        onClick={gzfun} disabled={!props.isLogin}>{gz ? "取关" : "关注"}</Button>
                <Button className='Perspac-header-send' type='primary' disabled={!props.isLogin}>发消息</Button>
                <Menu className='Perspac-header-Menu' onClick={onMenu} mode="horizontal"
                      items={items}/>
                <p className='Perspac-header-guanzhushu'>关注数{props.user.likeNum}</p>
            </div>
            <div style={{
                padding: 10,
                marginTop: 10,
                minHeight: 900,
                minWidth: 800,
                background: "rgb(231, 231, 231)",
                borderRadius: borderRadiusLG,
                position: "relative",
            }}>
                <Routes>
                    <Route path={'/' + props.user.id + '/dynamic'}
                           element={<Dynamicbody authorId={props.user.id} isLogin={props.isLogin}/>}/>
                    <Route path={'/' + props.user.id + '/likeDynamic'}
                           element={<Dynamicbody authorId={props.user.id} isLogin={props.isLogin} like={true}/>}/>
                </Routes>
            </div>
        </>
    )
}

export default Perspaccon;