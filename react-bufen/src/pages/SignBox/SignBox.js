import {React, useState} from 'react';
import {Button, Checkbox, Form, Input, notification, Select,} from 'antd';
import './sign.css'
import {Link} from 'react-router-dom';
import axios from 'axios';

const { Option } = Select;
const formItemLayout = {
    labelCol: {
        xs: {
            span: 24,
        },
        sm: {
            span: 8,
        },
    },
    wrapperCol: {
        xs: {
            span: 24,
        },
        sm: {
            span: 16,
        },
    },
};
const tailFormItemLayout = {
    wrapperCol: {
        xs: {
            span: 24,
            offset: 0,
        },
        sm: {
            span: 16,
            offset: 8,
        },
    },
};

const SignBox = () => {
    const [form] = Form.useForm();

    // 错误提示
    const [api, contextHolder] = notification.useNotification();

    const onFinish = (values) => {
        console.log('Received values of form: ', values);
        const data = JSON.stringify(values);
        console.log(data);
        fetch(
            '/api/sign',
            {
                method: 'POST',
                body: data,
                headers: {
                    'content-type': 'application/json'
                }
            }
        )
            .then(function (response) {
                return response.json();
            })
            .then(function (myJson) {
                console.log(myJson);
                return myJson.message;
            })
            .then(function (msg) {
                if (typeof msg === "undefined" || msg === null) {
                    msg = "注册失败";
                }
                api.open({
                    message: msg,
                });
                if ("success" === msg) {
                    window.location.href = "/";
                }
            });
    };


    const [authCode, setAuthCode] = useState('');
    const [email, setEmail] = useState('');

    const pushCode = () => {
        console.log('Success:', email);
        axios.post("/api/authCode", {
            Email: email,
        })
            .then(function (response) {
                setAuthCode(response.data.data);
            })
            .catch(function (error) {
                console.log(error);
            })
    }

    const prefixSelector = (
        <Form.Item name="prefix" noStyle>
            <Select
                style={{
                    width: 70,
                }}
            >
                <Option value="86">+86</Option>
                <Option value="87">+87</Option>
            </Select>
        </Form.Item>
    );
    return (
        <div className='sign-box'>
            <div className='pure-color'>
                <div className="sign-demo-logo"/>
            </div>
            <Form
                {...formItemLayout}
                form={form}
                name="register"
                onFinish={onFinish}
                initialValues={{
                    prefix: '86',
                }}
                style={{
                    Width: 300,
                    height: 640,
                    float: 'right',
                    backgroundColor: 'white',
                    padding: '10px',
                    borderRadius: '6px',
                }}
                scrollToFirstError
            >

                <Form.Item
                    name="username"
                    label="用户名"
                    rules={[
                        {
                            required: true,
                            message: '请输入你的用户名!',
                        },
                    ]}
                >
                    <Input />
                </Form.Item>

                <Form.Item
                    name="password"
                    label="密码"
                    rules={[
                        {
                            required: true,
                            message: '请输入你的密码!',
                        },
                    ]}
                    hasFeedback
                >
                    <Input.Password />
                </Form.Item>

                <Form.Item
                    name="confirm"
                    label="确认密码"
                    dependencies={['password']}
                    hasFeedback
                    rules={[
                        {
                            required: true,
                            message: '请检查你的确认密码!',
                        },
                        ({ getFieldValue }) => ({
                            validator(_, value) {
                                if (!value || getFieldValue('password') === value) {
                                    return Promise.resolve();
                                }
                                return Promise.reject(new Error('密码输入错误!'));
                            },
                        }),
                    ]}
                >
                    <Input.Password />
                </Form.Item>

                <Form.Item
                    name="nickname"
                    label="昵称"
                    tooltip="What do you want others to call you?"
                    rules={[
                        {
                            required: true,
                            message: '请输入昵称!',
                            whitespace: true,
                        },
                    ]}
                >
                    <Input />
                </Form.Item>


                <Form.Item
                    name="phone"
                    label="电话"
                    rules={[
                        {
                            required: true,
                            message: '请输入你的电话!',
                        },
                    ]}
                >
                    <Input
                        addonBefore={prefixSelector}
                        style={{
                            width: '100%',
                        }}
                    />
                </Form.Item>

                <Form.Item
                    name="gender"
                    label="性别"
                    rules={[
                        {
                            required: true,
                            message: '请输入你的性别!',
                        },
                    ]}
                >
                    <Select placeholder="select your gender">
                        <Option value="男">男</Option>
                        <Option value="女">女</Option>
                    </Select>
                </Form.Item>
                <Form.Item
                    label="邮箱"
                    name="Email"
                    rules={[
                        {
                            required: true,
                            message: '请输入你的邮箱!',
                        },
                        {
                            type: 'email',
                            message: '请输入正确的邮箱',
                        },
                    ]}
                >
                    <Input onChange={(e) => {
                        setEmail(e.target.value);
                    }}/>
                </Form.Item>
                <Form.Item
                    wrapperCol={{
                        offset: 8,
                        span: 16,
                    }}
                >
                    <Button type="primary" htmlType="submit" onClick={pushCode}>
                        发送验证码
                    </Button>
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
                    name="agreement"
                    valuePropName="checked"
                    rules={[
                        {
                            validator: (_, value) =>
                                value ? Promise.resolve() : Promise.reject(new Error('Should accept agreement')),
                        },
                    ]}
                    {...tailFormItemLayout}
                >
                    <Checkbox>
                        我已读<a href="">合同</a>
                    </Checkbox>
                </Form.Item>
                <Form.Item {...tailFormItemLayout}>
                    {contextHolder}
                    <Button type="primary" htmlType="submit">
                        注册
                    </Button>
                    <Link className='toHome' to={"/主页"}>返回</Link>
                </Form.Item>
            </Form>
        </div>
    );
};
export default SignBox;