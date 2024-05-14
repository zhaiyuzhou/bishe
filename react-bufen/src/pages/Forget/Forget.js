import {React, useState} from "react";
import {Button, Form, Input} from 'antd';
import axios from "axios";
import "./Forget.css"
import {Link} from "react-router-dom";

const Forget = () => {

    const [authCode, setAuthCode] = useState('');
    const [email, setemail] = useState();

    const pushCode = (values) => {
        console.log('Success:', values);
        setemail(values);
        axios.post("/api/authCode", values)
            .then(function (response) {
                setAuthCode(response.data.data);
            })
            .catch(function (error) {
                console.log(error);
            })
    }

    const findPassword = () => {
        axios.post("/api/findPassword", email);
    }

    return (
        <div className="forget-div">
            <Form
                name="oldem"
                labelCol={{
                    span: 8,
                }}
                wrapperCol={{
                    span: 16,
                }}
                style={{
                    Width: 249,
                }}
                initialValues={{
                    remember: true,
                }}
                layout='inline'
                onFinish={pushCode}
                autoComplete="off"
            >
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
                onFinish={findPassword}
                autoComplete="off"
            >
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
                        找回密码
                    </Button>
                </Form.Item>
            </Form>
            <Link className="forget-link" to="/主页">返回</Link>
        </div>
    )
}


export default Forget;