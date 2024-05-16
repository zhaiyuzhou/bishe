import React, {useEffect, useState} from 'react';
import {Button, Image, Input, Layout} from 'antd';
import {useNavigate} from 'react-router';
import {Link, useLocation} from 'react-router-dom';
import LoginBox from '../../component/LoginBox/LoginBox';
import Avapopover from '../../component/Avapopover/Avapopover';

const {Search} = Input;
const {Header} = Layout;

const MyHeader = (props) => {

    const navigate = useNavigate();
    const [times, setTimes] = useState(0);
    const location = useLocation();

    // 搜索内容
    const onSearch = (value) => {
        props.setSearchDate(value);
        setTimes(0);
        navigate("/主页/搜索", {replace: true});
    };

    useEffect(() => {

        // 登陆气泡框显示
        let model = document.querySelector(".model");

        const showModal = () => {
            model.style.display = "inline-block";
        };

        const closeModel = () => {
            model.style.display = "none";
        };

        if (decodeURI(location.pathname) === '/忘记密码') {
            model.style.display = "none";
        }

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
    }, [navigate, times, props.isLogin, location]);

    return (
        <>
            <div className='model'
                 style={{display: 'none', width: (window.innerWidth - 18), height: (window.innerHeight)}}>
                <div className='mask'></div>
                <LoginBox/>
            </div>
            <div className='index'>
                <Layout>
                    <Header className='header-style'>
                        <div className="demo-logo"/>
                        <Image src='http://localhost:3000/imgs/title.png' style={{
                            width: "200px",
                            position: "relative",
                            top: "-4px",
                            left: "10px",
                        }} preview={false}/>
                        <Link className='shouye-back' to={"/主页/首页"}>首 页</Link>
                        <Search
                            className='search'
                            placeholder="input search text"
                            onSearch={onSearch}
                            style={{
                                display: (props.noSearch ? "none" : "block"),
                                width: 500,
                            }}
                        />
                        <Button type='primary' className='login-but'
                                style={{display: (props.isLogin ? 'none' : 'inline-block')}}
                        >登陆</Button>
                        <Avapopover style={{display: (props.isLogin ? 'inline-block' : 'none')}} {...props.user} />
                    </Header>
                    {props.element}
                </Layout>
            </div>
        </>
    )

}

export default MyHeader;