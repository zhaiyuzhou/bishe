import React, {useEffect, useState} from 'react';
import {
    AntDesignOutlined,
    CrownOutlined,
    EditOutlined,
    KeyOutlined,
    LoadingOutlined,
    MailOutlined,
    PlusOutlined,
    SlackCircleFilled,
    UserOutlined
} from '@ant-design/icons';
import {Avatar, Button, Flex, Form, Input, Layout, Menu, message, theme, Upload} from 'antd';
import Avapopover from '../../component/Avapopover/Avapopover';
import {useNavigate} from 'react-router';
import {Link, Route, Routes} from 'react-router-dom';
import './Pencen.css'
import Information from '../../component/Information/Information';
import axios from 'axios';
import Dynamicbody from '../Dynamicbody/Dynamicbody';


const {Header, Content, Footer, Sider} = Layout;

const getBase64 = (img, callback) => {
    const reader = new FileReader();
    reader.addEventListener('load', () => callback(reader.result));
    reader.readAsDataURL(img);
};

const beforeUpload = (file) => {
    const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png';
    if (!isJpgOrPng) {
        message.error('你只能上传 JPG/PNG !');
    }
    const isLt2M = file.size / 1024 / 1024 < 2;
    if (!isLt2M) {
        message.error('图片大小不超过 2MB!');
    }
    return isJpgOrPng && isLt2M;
};

const Pencen = (props) => {
    const navigate = useNavigate();

    console.log(props.isLogin);
    if (!props.isLogin) {
        navigate("/主页/首页", {replace: true});
    }

    const [user, setUser] = useState({
        avatar: ""
    });

    useEffect(() => {
        setUser(props.user);
    }, [props.user])

    // 导航
    const item1 = [
        {
            key: '/个人中心/information',
            icon: React.createElement(UserOutlined),
            label: "个人信息",
        },
        {
            key: '/个人中心/pDynamic',
            icon: React.createElement(SlackCircleFilled),
            label: "个人动态",
        },
        {
            key: '/个人中心/avatar',
            icon: React.createElement(CrownOutlined),
            label: "上传头像",
        },
        {
            key: '/个人中心/changPassword',
            icon: React.createElement(KeyOutlined),
            label: "修改密码",
        },
        {
            key: '/个人中心/changEmail',
            icon: React.createElement(MailOutlined),
            label: "修改邮箱",
        },
        {
            key: '/个人中心/changeNickname',
            icon: React.createElement(EditOutlined),
            label: "修改昵称",
        },
    ]

    // 修改密码
    const onPwFinish = (values) => {
        console.log('Success:', values);
        axios.post('/api/passwordChange', values)
            .then(function (response) {
                console.log(response);
            })
            .catch(function (error) {
                console.log(error);
            })
    };

    const pwChage = (
        <Form
            name="pwChage"
            labelCol={{
                span: 8,
            }}
            wrapperCol={{
                span: 16,
            }}
            style={{
                maxWidth: 600,
            }}
            initialValues={{
                remember: true,
            }}
            onFinish={onPwFinish}
            autoComplete="off"
        >
            <Form.Item
                label="原密码"
                name="oldPassword"
                rules={[
                    {
                        required: true,
                        message: '请输入你的原密码!',
                    },
                ]}
            >
                <Input.Password/>
            </Form.Item>

            <Form.Item
                label="新密码"
                name="newPassword"
                rules={[
                    {
                        required: true,
                        message: '请输入你的新密码',
                    },
                ]}
            >
                <Input.Password/>
            </Form.Item>

            <Form.Item
                name="confirmPassword"
                label="确认新密码"
                dependencies={['newPassword']}
                hasFeedback
                rules={[
                    {
                        required: true,
                        message: '请确认你的新密码!',
                    },
                    ({getFieldValue}) => ({
                        validator(_, value) {
                            if (!value || getFieldValue('newPassword') === value) {
                                return Promise.resolve();
                            }
                            return Promise.reject(new Error('密码不一样!'));
                        },
                    }),
                ]}
            >
                <Input.Password/>
            </Form.Item>

            <Form.Item
                wrapperCol={{
                    offset: 8,
                    span: 16,
                }}
            >
                <Button type="primary" htmlType="submit">
                    提交
                </Button>
            </Form.Item>
        </Form>
    )

    // 上传头像           
    const [loading, setLoading] = useState(false);
    const [imageUrl, setImageUrl] = useState();
    const [avatar, setAvatar] = useState('');
    const handleChange = (info) => {
        if (info.file.status === 'uploading') {
            setLoading(true);
            return;
        }
        if (info.file.status === 'done') {
            console.log(info.file);
            // Get this url from response in real world.
            getBase64(info.file.originFileObj, (url) => {
                setLoading(false);
                setImageUrl(url);
            });

            if (info.file.name !== avatar) {
                setAvatar(info.file.name);
            }
        }
    };

    const uploadButton = (
        <button
            style={{
                border: 0,
                background: 'none',
            }}
            type="button"
        >
            {loading ? <LoadingOutlined/> : <PlusOutlined/>}
            <div
                style={{
                    marginTop: 8,
                }}
            >
                上传
            </div>
        </button>
    );

    const avChange = (

        <Flex gap="middle" wrap="wrap">
            <Avatar
                size={100}
                icon={<AntDesignOutlined/>}
                src={user.avatar}
            />
            <Upload
                listType="picture-circle"
                className="avatar-uploader"
                showUploadList={false}
                action="/api/updateImg"
                beforeUpload={beforeUpload}
                onChange={handleChange}
            >
                {imageUrl ? (
                    <img
                        src={imageUrl}
                        alt="avatar"
                        style={{
                            width: '100%',
                        }}
                    />
                ) : (
                    uploadButton
                )}
            </Upload>
            <Button type="primary" onClick={() => {
                axios.post('/api/avatarChange', {
                    avatar: avatar,
                })
                    .then(function (response) {
                        console.log(response);
                        navigate("/个人中心", {replace: true});
                    })
                    .catch(function (error) {
                        console.log(error);
                    })
            }}>修改头像</Button>
        </Flex>
    )


    // 修改邮箱
    const onEmFinish = (values) => {
        console.log('Success:', values);
        axios.post("/api/emailChange", values)
            .then(function (response) {
                console.log(response);
            })
            .catch(function (error) {
                console.log(error);
            })
    };

    const [authCode, setAuthCode] = useState('');

    const pushCode = (values) => {
        console.log('Success:', values);
        axios.post("/api/authCode", values)
            .then(function (response) {
                setAuthCode(response.data.data);
            })
            .catch(function (error) {
                console.log(error);
            })
    }

    const emChange = (
        <div>
            <Form
                name="oldem"
                labelCol={{
                    span: 8,
                }}
                wrapperCol={{
                    span: 16,
                }}
                style={{
                    maxWidth: 600,
                }}
                initialValues={{
                    remember: true,
                }}
                layout='inline'
                onFinish={pushCode}
                autoComplete="off"
            >
                <Form.Item
                    label="原邮箱"
                    name="Email"
                    rules={[
                        {
                            required: true,
                            message: '请输入你的原邮箱!',
                        },
                        {
                            type: 'email',
                            message: '请输入正确的邮箱',
                        },
                    ]}
                >
                    <Input/>
                </Form.Item>
                <Form.Item
                    wrapperCol={{
                        offset: 8,
                        span: 16,
                    }}
                >
                    <Button type="primary" htmlType="submit">
                        发送验证码
                    </Button>
                </Form.Item>
            </Form>
            <br/>
            <Form
                name="emChage"
                labelCol={{
                    span: 8,
                }}
                wrapperCol={{
                    span: 16,
                }}
                style={{
                    maxWidth: 249,
                }}
                initialValues={{
                    remember: true,
                }}
                onFinish={onEmFinish}
                autoComplete="off"
            >
                <Form.Item
                    label="新邮箱"
                    name="newEmail"
                    rules={[
                        {
                            required: true,
                            message: '请输入你的新邮箱',
                        },
                        {
                            type: 'email',
                            message: '请输入正确的邮箱!',
                        },
                    ]}
                >
                    <Input/>
                </Form.Item>
                <Form.Item
                    name="confirmCode"
                    label="验证码"
                    dependencies={['验证码']}
                    hasFeedback
                    rules={[
                        {
                            required: true,
                            message: '请确认你的验证码!',
                        },
                        () => ({
                            validator(_, value) {
                                if (!value || authCode === value) {
                                    return Promise.resolve();
                                }
                                return Promise.reject(new Error('验证码不正确'));
                            },
                        }),
                    ]}
                >
                    <Input/>
                </Form.Item>

                <Form.Item
                    wrapperCol={{
                        offset: 8,
                        span: 16,
                    }}
                >
                    <Button type="primary" htmlType="submit">
                        修改邮箱
                    </Button>
                </Form.Item>
            </Form>
        </div>
    )

    const onNnFinish = (value) => {
        axios.post("/api/nickNameChange", {
            nickName: value,
        })
    }
    const nicknameChange = (
        <Form
            name="nnChage"
            labelCol={{
                span: 8,
            }}
            wrapperCol={{
                span: 16,
            }}
            style={{
                maxWidth: 600,
            }}
            initialValues={{
                remember: true,
            }}
            onFinish={onNnFinish}
            autoComplete="off"
        >

            <Form.Item
                label="新昵称"
                name="newnickname"
                rules={[
                    {
                        required: true,
                        message: '请输入你的新昵称',
                    },
                ]}
            >
                <Input/>
            </Form.Item>

            <Form.Item
                wrapperCol={{
                    offset: 8,
                    span: 16,
                }}
            >
                <Button type="primary" htmlType="submit">
                    提交
                </Button>
            </Form.Item>
        </Form>
    )

    const {
        token: {colorBgContainer, borderRadiusLG},
    } = theme.useToken();

    const clickItem = (e) => {
        navigate(e.key, {replace: false})
    }

    return (
        <div className='pencen-page'>
            <Layout>
                <Header className='header-style'>
                    <div className="demo-logo"/>
                    <Link className='shouye-back' to={"/主页/首页"}>首页</Link>
                    <Avapopover style={{display: 'inline-block'}} {...user} />
                </Header>
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
                        <Sider
                            style={{
                                background: colorBgContainer,
                            }}
                            width={200}
                        >
                            <Menu
                                mode="inline"
                                defaultSelectedKeys={['1']}
                                defaultOpenKeys={['sub1']}
                                style={{
                                    height: '100%',
                                }}
                                items={item1}
                                onClick={clickItem}
                            />
                        </Sider>
                        <Content
                            style={{
                                padding: '0 24px',
                                minHeight: 750,
                            }}
                        >
                            <Routes>
                                <Route path='/information' element={<Information {...user} />}/>
                                <Route path='/avatar' element={avChange}/>
                                <Route path='/changPassword' element={pwChage}/>
                                <Route path='/changEmail' element={emChange}/>
                                <Route path='/pDynamic' element={<div style={{
                                    padding: 10,
                                    margin: 0,
                                    minHeight: 900,
                                    minWidth: 800,
                                    background: "rgb(231, 231, 231)",
                                    borderRadius: borderRadiusLG,
                                    position: "relative",
                                }}><Dynamicbody authorId={user.id} del={true} isLogin={props.isLogin}/></div>}/>
                                <Route path='/changeNickname' element={nicknameChange}/>
                            </Routes>
                        </Content>
                    </Layout>
                </Content>
                <Footer style={{textAlign: 'center'}}>
                    Ant Design ©{new Date().getFullYear()} Created by Ant UED
                </Footer>
            </Layout>
        </div>
    )
}

export default Pencen;