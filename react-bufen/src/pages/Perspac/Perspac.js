import React, {useEffect, useState} from 'react';
import {Layout, theme} from 'antd';
import "./perspac.css"
import {useLocation} from 'react-router-dom';
import Perspaccon from './Perspaccon';
import axios from 'axios';

const {Content, Footer} = Layout;

const Perspac = (props) => {

    const location = useLocation();
    const {
        token: {colorBgContainer, borderRadiusLG},
    } = theme.useToken();

    const [other, setOther] = useState();

    useEffect(() => {
        var str = decodeURI(location.pathname);
        if (str.slice(0, 5) === "/个人动态") {
            console.log(str.slice(6, 8));
            axios.post("/api/getUser", {
                userId: str.slice(6, 8),
            }).then((res) => {
                setOther(<Perspaccon user={res.data.data}
                                     disabled={!props.isLogin && res.data.data.id !== props.user.id}
                                     isLogin={props.isLogin}/>);
            })
        }

    }, [props, location])


    return (
        <div className="Perspac">
            <Content
                style={{
                    padding: '0 48px',
                }}
            >
                <Layout
                    style={{
                        padding: '24px 0',
                        background: colorBgContainer,
                        borderRadius: borderRadiusLG,
                    }}
                >
                    <Content
                        style={{
                            padding: '0 24px',
                            minHeight: 750,
                        }}
                    >
                        {other}
                    </Content>
                </Layout>
            </Content>
            <Footer style={{textAlign: 'center'}}>
                {new Date().getFullYear()}
            </Footer>
        </div>
    )
}

export default Perspac;