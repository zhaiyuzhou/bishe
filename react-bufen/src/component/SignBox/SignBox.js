import React from 'react';
import {
    Button,
    Checkbox,
    Form,
    Input,
    Select,
} from 'antd';
import './sign.css'
import qs from 'qs'

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
    const onFinish = (values) => {
        console.log('Received values of form: ', values);
        const data = JSON.stringify(values);
        console.log(data);
        fetch(
            '/sign',
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
            })
            .then(function(){
                // window.location.assign('');
            });
    };
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
                <div className="demo-logo" />
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
                    maxWidth: 300,
                    height: 500,
                    float: 'right',
                }}
                scrollToFirstError
            >
                <Form.Item
                    name="email"
                    label="邮箱"
                    rules={[
                        {
                            type: 'email',
                            message: 'The input is not valid E-mail!',
                        },
                        {
                            required: true,
                            message: 'Please input your E-mail!',
                        },
                    ]}
                >
                    <Input />
                </Form.Item>

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
                        <Option value="male">男</Option>
                        <Option value="female">女</Option>
                    </Select>
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
                    <Button type="primary" htmlType="submit">
                        注册
                    </Button>
                </Form.Item>
            </Form>
        </div>
    );
};
export default SignBox;