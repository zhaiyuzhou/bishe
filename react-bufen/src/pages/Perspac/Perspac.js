import React, {useEffect, useState} from 'react';
import {Layout, theme} from 'antd';
import "./perspac.css"
import Perspaccon from './Perspaccon';

const {Content, Footer} = Layout;

const Perspac = (props) => {

    const {
        token: {colorBgContainer, borderRadiusLG},
    } = theme.useToken();

    const [user, setUser] = useState({
        id: 0,
        avatar: ""
    });

    useEffect(() => {
        setUser(props.user);
    }, [user.id])

    console.log(!props.isLogin && props.other.id !== user.id);
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
                        <Perspaccon user={props.other} disabled={!props.isLogin && props.other.id !== user.id}
                                    isLogin={props.isLogin}/>
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