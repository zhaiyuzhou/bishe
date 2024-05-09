import './Home.css'
import React, {useEffect, useState} from 'react';
import {Breadcrumb, Button, Image, Input, Layout, Menu, notification, theme} from 'antd';
import Sider from 'antd/es/layout/Sider';
import cookie from 'react-cookies'
import {HomeOutlined, RedoOutlined, UnorderedListOutlined} from '@ant-design/icons';
import {useNavigate} from 'react-router';
import {Link, Route, Routes, useLocation} from 'react-router-dom';
import LoginBox from '../../component/LoginBox/LoginBox';
import Avapopover from '../../component/Avapopover/Avapopover';
import Pubdyn from '../../component/Pubdyn/Pubdyn';
import Dynamicbody from '../Dynamicbody/Dynamicbody';
import axios from 'axios';

const {Search} = Input;

const {Header, Content} = Layout;
const Home = () => {

    const [times, setTimes] = useState(0);
    const navigate = useNavigate();
    const location = useLocation();
    const {
        token: {colorBgContainer, borderRadiusLG},
    } = theme.useToken();

    useEffect(() => {

        if (location.pathname === "/主页")
            navigate("/主页/首页", {replace: false})

        if (window.performance.navigation.type === 1) {
            console.log(times);
            setTimes(0);
        }

        // 登陆气泡框显示
        let model = document.querySelector(".model");

        const showModal = () => {
            model.style.display = "inline-block";
        };

        const closeModel = () => {
            model.style.display = "none";
        };

        let mask = document.querySelector(".mask");
        let loginBut = document.querySelector(".login-but");

        mask.addEventListener("click", closeModel);
        loginBut.addEventListener("click", showModal);

        function updatePosition() {
            const element = document.querySelector(".model"); // 获取你想要居中的元素

            element.style.position = 'absolute'; // 设置元素的定位为绝对定位
            element.style.top = window.scrollY + 'px'; // 设置元素的上边距，使其垂直居中
        }

        updatePosition();
        window.addEventListener('scroll', updatePosition);

        // 在组件卸载时移除事件监听器
        return () => {
            mask.removeEventListener("click", closeModel);
            loginBut.removeEventListener("click", showModal);
            window.removeEventListener('scroll', updatePosition);
        };
    }, [navigate, location, times]);

    // 搜索内容
    const [searchDate, setSearchDate] = useState();
    const onSearch = (value) => {
        setSearchDate(value);
        setTimes(0);
        navigate("/主页/搜索", {replace: true});
    };

    // 标签
    const tags = ['新闻', '电影', '电视剧', '动画', '番剧', '游戏', '音乐', '美术', '动物', '知识', '科技', '美食', '汽车', '运动', '生活', '其他'];
    const tagsEn = ['news', 'movie', 'show', 'animated', 'bangumi', 'game', 'music', 'art', 'animal', 'knowledge', 'technology', 'food', 'car', 'movement', 'live', 'other'];

    const items2 = [
        {
            key: '/主页/首页',
            icon: React.createElement(HomeOutlined),
            label: '主页',
        },
        {
            key: 'sider2',
            icon: React.createElement(UnorderedListOutlined),
            label: '分类',
            children: tags.map((tag, index) => {
                return {
                    key: '/主页/' + tag,
                    label: tag
                }
            })
        }
    ]

    // 检查登录
    const isLogin = (cookie.load('isLogin') === 'true');

    // 处理新发的动态
    const [newDynamic, setNewDynamic] = useState();

    const pubDynamic = (newDynamic) => {
        setNewDynamic(newDynamic);
    }

    const [loadings, setLoadings] = useState([]);
    const enterLoading = (index) => {
        setLoadings((prevLoadings) => {
            const newLoadings = [...prevLoadings];
            newLoadings[index] = true;
            return newLoadings;
        });
        setTimes(times + 1);
        axios.post("/api/refreshPyn", {
            times: times
        }).then((res) => {
            if (res.status === 200) {
                setLoadings((prevLoadings) => {
                    const newLoadings = [...prevLoadings];
                    newLoadings[index] = false;
                    return newLoadings;
                });
            } else {
                api.open({
                    message: '向服务器请求失败',
                });
            }
        })
    };

    const [api, contextHolder] = notification.useNotification();

    return (
        <div>
            <div className='model'
                 style={{display: 'none', width: (window.innerWidth - 18), height: (window.innerHeight)}}>
                <div className='mask'></div>
                <LoginBox/>
            </div>
            <div className='index'>
                <Layout>
                    <Header className='header-style'>
                        <div className="demo-logo"/>
                        <Image src='../../../title.png' style={{
                            width: "200px",
                            position: "relative",
                            top: "-5px",
                            left: "10px",
                        }} preview={false}/>
                        <Link className='shouye-back' to={"/主页/首页"}>首页</Link>
                        <Search
                            className='search'
                            placeholder="input search text"
                            onSearch={onSearch}
                            style={{
                                width: 500,
                            }}
                        />
                        <Button type='primary' className='login-but'
                                style={{display: (isLogin ? 'none' : 'inline-block')}}
                        >登陆</Button>
                        <Avapopover style={{display: (isLogin ? 'inline-block' : 'none')}}/>
                    </Header>
                    <Layout>
                        <Sider
                            width={200}
                            style={{
                                background: colorBgContainer,
                            }}
                        >
                            <Menu
                                mode="inline"
                                defaultOpenKeys={['sider2']}
                                style={{
                                    height: '100%',
                                    width: 200,
                                    borderRight: 0,
                                    position: 'fixed',
                                }}
                                items={items2}
                                onClick={(e) => {
                                    navigate(e.key, {replace: true})
                                    setTimes(0);
                                }}
                            />
                        </Sider>
                        <Layout
                            style={{
                                padding: '0 24px 24px',
                            }}
                        >
                            <Breadcrumb
                                style={{
                                    margin: '16px 0',
                                }}
                            >
                            </Breadcrumb>
                            <Content
                                style={{
                                    padding: 10,
                                    margin: 0,
                                    minHeight: 900,
                                    minWidth: 800,
                                    background: "rgb(231, 231, 231)",
                                    borderRadius: borderRadiusLG,
                                    position: "relative",
                                }}
                            >
                                {contextHolder}
                                <Pubdyn pubDynamic={pubDynamic}/>
                                <Button className='huanyipi' icon={<RedoOutlined/>} loading={loadings[0]}
                                        onClick={() => enterLoading(0)}>换一批</Button>
                                <Routes>
                                    <Route path='/首页' element={<Dynamicbody newDynamic={newDynamic} isLogin={isLogin}
                                                                              times={times}/>}/>
                                    {tags.map((tag, index) => {
                                        return (
                                            <Route key={'/' + tag} path={'/' + tag}
                                                   element={<Dynamicbody tag={tagsEn[index]} newDynamic={newDynamic}
                                                                         isLogin={isLogin} times={times}/>}/>
                                        )
                                    })}
                                    <Route path='/搜索'
                                           element={<Dynamicbody newDynamic={newDynamic} isLogin={isLogin} times={times}
                                                                 searchDate={searchDate}/>}/>
                                </Routes>
                            </Content>
                        </Layout>
                    </Layout>
                </Layout>
            </div>
        </div>

    );
};
export default Home;